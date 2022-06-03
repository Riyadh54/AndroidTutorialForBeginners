package com.example.a29_firebase.Activities;

import static com.example.a29_firebase.Activities.MainActivity.stringIntent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.a29_firebase.Adapter.RecyclerAdapter;
import com.example.a29_firebase.Interfaces.onMoreClickListener;
import com.example.a29_firebase.Model.EmployeeModel;
import com.example.a29_firebase.databinding.ActivityAddEmployeeBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity {
    ActivityAddEmployeeBinding binding;
    final Calendar myCalendar= Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    String keyChild;
    String pushId;
    Uri imageUri ;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String value =intent.getStringExtra("keyIntent");
        progressDialog = new ProgressDialog(AddEmployeeActivity.this);

        if (value.equals(stringIntent)){
            // Add new Employee
            date =new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,day);
                    updateLabel();

                }
            };
            updateLabel();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("riyadh");
            binding.addEmployeeBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = binding.addEmployeeEtName.getText().toString();
                    String email = binding.addEmployeeEtEmail.getText().toString();
                    String _salary = binding.addEmployeeEtSalary.getText().toString();
                    if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(_salary))
                    {
                        Toast.makeText(AddEmployeeActivity.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double salary = Double.parseDouble(_salary);
                    long dateBirth = toLong(myCalendar.getTime());
                    String _pushId = reference.push().getKey();

                    try {
                        if (imageUri != null){
                            progressDialog.setMessage("pleas wait");
                            progressDialog.show();
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference userStorageRef = storageRef.child("employees").child(_pushId);
                            userStorageRef.putStream(inputStream)
                                    .continueWithTask(taskSnapshot -> {
                                        return userStorageRef.getDownloadUrl();
                                    }).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            String imageUrl = task.getResult().toString();
                                            Toast.makeText(AddEmployeeActivity.this, "on completed", Toast.LENGTH_SHORT).show();
                                            EmployeeModel employeeModel = new EmployeeModel(_pushId,name,email,imageUrl,salary,dateBirth);
                                            reference.child("employees").push().setValue(employeeModel);
                                            progressDialog.dismiss();
                                            finish();

                                        }
                                    });


                            // need to do more work here
                        }else {
                            EmployeeModel employeeModel = new EmployeeModel(_pushId,name,email,"",salary,dateBirth);
                            reference.child("employees").push().setValue(employeeModel);
                            finish();
                        }

                    } catch (FileNotFoundException ex) {
                        Toast.makeText(AddEmployeeActivity.this, ""+ex, Toast.LENGTH_SHORT).show();
                    }


                }
            });
            binding.addEmployeeEmployeeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImage.launch("image/*");
                }
            });
        }else {
            // Edit Employee
            showDataByPushId(value);
            binding.addEmployeeBtnSave.setOnClickListener(new View.OnClickListener() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("riyadh").child("employees");
                @Override
                public void onClick(View view) {
                    String name = binding.addEmployeeEtName.getText().toString();
                    String email = binding.addEmployeeEtEmail.getText().toString();
                    String _salary = binding.addEmployeeEtSalary.getText().toString();
                    if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(_salary))
                    {
                        Toast.makeText(AddEmployeeActivity.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double salary = Double.parseDouble(_salary);
                    long dateBirth = toLong(myCalendar.getTime());
                    try {
                        if (imageUri != null){
                            progressDialog.setMessage("pleas wait");
                            progressDialog.show();
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference userStorageRef = storageRef.child("employees").child(pushId);
                            userStorageRef.putStream(inputStream)
                                    .continueWithTask(taskSnapshot -> {
                                        return userStorageRef.getDownloadUrl();
                                    }).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            String imageUrl = task.getResult().toString();
                                            EmployeeModel employeeModel = new EmployeeModel(pushId,name,email,imageUrl,salary,dateBirth);
                                            reference.child(keyChild).setValue(employeeModel);
                                            progressDialog.dismiss();
                                            finish();
                                        }
                                    });
                        }else {
                            EmployeeModel employeeModel = new EmployeeModel(pushId,name,email,"",salary,dateBirth);
                            reference.child(keyChild).setValue(employeeModel);
                            finish();
                        }

                        // need to do more work here
                    } catch (FileNotFoundException ex) {
                        Toast.makeText(AddEmployeeActivity.this, ""+ex, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            binding.addEmployeeEmployeeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImage.launch("image/*");
                }
            });

        }







        binding.addEmployeeEtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEmployeeActivity.this,date,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });





    }

    private void showDataByPushId(String pushKey){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("riyadh").child("employees");
        Query query = reference.orderByChild("pushId").equalTo(pushKey);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        EmployeeModel employeeModel = dataSnapshot.getValue(EmployeeModel.class);
                        if (employeeModel != null){
                            binding.addEmployeeEtName.setText(employeeModel.getName());
                            binding.addEmployeeEtEmail.setText(employeeModel.getEmail());
                            binding.addEmployeeEtSalary.setText(String.valueOf(employeeModel.getSalary()));
                            String myFormat="dd/MM/yy";
                            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
                            binding.addEmployeeEtBirthday.setText(dateFormat.format(toDate(employeeModel.getDateBirth())));

                            if (!employeeModel.getImageUri().equals("")){
                                Uri uri = Uri.parse(employeeModel.getImageUri());
                                Picasso.with(AddEmployeeActivity.this).load(uri).into(binding.addEmployeeEmployeeImage);
                            }

                            pushId = employeeModel.getPushId();
                        }
                         keyChild = dataSnapshot.getKey();

                    }
                }else {
                    Toast.makeText(AddEmployeeActivity.this, "data base is not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
        binding.addEmployeeEtBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }

    public static Long toLong(Date date){
        return date == null?null: date.getTime();
    }
    public static Date toDate(Long millisecond){
        return millisecond==null?null:new Date(millisecond);
    }

    ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    imageUri = result;
                    binding.addEmployeeEmployeeImage.setImageURI(imageUri);

                }
            }
    );


}