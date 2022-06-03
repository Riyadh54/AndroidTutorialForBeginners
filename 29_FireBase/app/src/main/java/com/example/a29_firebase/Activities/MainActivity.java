package com.example.a29_firebase.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.a29_firebase.Adapter.RecyclerAdapter;
import com.example.a29_firebase.Interfaces.onMoreClickListener;
import com.example.a29_firebase.Model.EmployeeModel;
import com.example.a29_firebase.R;
import com.example.a29_firebase.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    // for delete any project into firebase
    //https://console.cloud.google.com/cloud-resource-manager?previousPage=%2Fcloud-resources-manager
    public static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 13;
    public static final String stringIntent = "-1";
    public static final String keyIntent = "keyIntent";
    private String pushKeyValue;
    ActivityMainBinding binding;
    ArrayList<EmployeeModel> arrayList ;
    RecyclerAdapter adapter;
    DatabaseReference reference;
    boolean connected = false;
    String _pushKey ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQ_CODE_WRITE_EXTERNAL_STORAGE);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            //we are connected to a network
            connected = true;
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        }else{
            connected = false;
            Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show();
        }


        showAllData();


        binding.mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddEmployeeActivity.class);
                intent.putExtra(keyIntent,stringIntent);
                arl.launch(intent);
            }
        });

    }


    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    showAllData();
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_box).getActionView();
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                showDataByEmployeeName(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showAllData();
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                Intent intent = new Intent(MainActivity.this,AddEmployeeActivity.class);
                intent.putExtra(keyIntent,pushKeyValue);
                arl.launch(intent);
                return true;
            case R.id.delete:
                getPushKey(pushKeyValue);
                return true;
        }


        return false;
    }

    private void showAllData(){
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       reference = database.getReference("riyadh");
       binding.mainRv.setHasFixedSize(true);
       binding.mainRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

       arrayList = new ArrayList<>();
       adapter = new RecyclerAdapter(arrayList, new onMoreClickListener() {
           @Override
           public void onClickListener(View view,String pushKey) {
               registerForContextMenu(view);
               pushKeyValue = pushKey;
           }
       },MainActivity.this);

       binding.mainRv.setAdapter(adapter);

       reference.child("employees").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               arrayList.clear();
               for (DataSnapshot dataSnapshot : snapshot.getChildren())
               {
                   EmployeeModel employeeModel = dataSnapshot.getValue(EmployeeModel.class);
                   arrayList.add(employeeModel);
                   //adapter.setData(arrayList);

               }
               adapter.notifyDataSetChanged();
               Collections.reverse(arrayList);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
           }
       });
    }

   private void showDataByEmployeeName(String employeeName){

       binding.mainRv.setHasFixedSize(true);
       binding.mainRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       arrayList = new ArrayList<>();
       adapter = new RecyclerAdapter(arrayList, new onMoreClickListener() {
           @Override
           public void onClickListener(View view, String pushKey){
               registerForContextMenu(view);
               pushKeyValue = pushKey;
           }
       },MainActivity.this);
       binding.mainRv.setAdapter(adapter);

       FirebaseDatabase database = FirebaseDatabase.getInstance();
       reference = database.getReference("riyadh").child("employees");
       Query query = reference.orderByChild("name").equalTo(employeeName);
       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists())
               {
                   arrayList.clear();
                   for (DataSnapshot dataSnapshot : snapshot.getChildren())
                   {
                       EmployeeModel employeeModel = dataSnapshot.getValue(EmployeeModel.class);
                       arrayList.add(employeeModel);
                   }
                   adapter.notifyDataSetChanged();
               }
               else {
                   Toast.makeText(MainActivity.this, "Data Base in not Exist", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
   }

   private void getPushKey(String pushId){

       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference reference = database.getReference("riyadh").child("employees");
       Query query = reference.orderByChild("pushId").equalTo(pushId);
       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                   for (DataSnapshot dataSnapshot : snapshot.getChildren())
                   {
                       EmployeeModel employeeModel = dataSnapshot.getValue(EmployeeModel.class);
                       if (employeeModel != null && employeeModel.getImageUri().isEmpty()){
                           dataSnapshot.getRef().removeValue();
                           Toast.makeText(MainActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
                       }else if (employeeModel != null && !employeeModel.getImageUri().isEmpty()){
                           dataSnapshot.getRef().removeValue();
                           StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                           StorageReference userStorageRef = storageRef.child("employees").child(pushId);
                           userStorageRef.delete();
                           Toast.makeText(MainActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
                       }

                   }
                   showAllData();

               }else {
                   Toast.makeText(MainActivity.this, "DataBase is not exist", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

   }


}