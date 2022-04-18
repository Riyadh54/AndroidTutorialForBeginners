package com.example.a13_actionmodemenu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActionMode myActMode;

    ArrayList<String> arrayList = new ArrayList<>();
    MyAdapter adapter;



    ListView lv;

    int position;
    View v;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.main_lv_country_items);
        String[] data = getResources().getStringArray(R.array.countries);
         for (int i =0;i<data.length;i++){
             arrayList.add(data[i]);
         }
         adapter = new MyAdapter(arrayList,R.layout.items_layout,MainActivity.this);
         lv.setAdapter(adapter);

         lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //layout = view.findViewById(R.id.main_ll);
                 //layout.setBackgroundColor(getResources().getColor(R.color.teal_200));

                    /* for (int g =0;g<adapterView.getChildCount();g++){
                         if (g == i)
                         {
                             adapterView.getChildAt(g).setBackgroundColor(getResources().getColor(R.color.teal_200));

                         }else {
                             adapterView.getChildAt(g).setBackgroundColor(getResources().getColor(R.color.white));

                         }
                     }
                     */
                    v = view;
                    v.setSelected(true);




                 position = i;
                 if(myActMode !=null){
                     return false;
                 }
                 myActMode = startSupportActionMode(myActModeCallback);
                 return true;
             }
         });

         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                // view.setSelected(true);
                 if (!view.isSelected()){
                     if (myActMode != null)
                     {
                         myActMode.finish();
                     }
                 }


             }
         });






    }

    private ActionMode.Callback myActModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_mode_menu, menu);
            mode.setTitle("1 Selected");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.rename:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Rename To");
                    EditText editText = new EditText(MainActivity.this);
                    editText.setText(arrayList.get(position));
                    alertDialog.setView(editText);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (editText.getText().toString().equals(arrayList.get(position))){
                                Toast.makeText(MainActivity.this, "the same name", Toast.LENGTH_SHORT).show();
                            }else {
                                adapter.renameItem(editText.getText().toString(),position);
                                Toast.makeText(MainActivity.this, "rename successfully", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                    alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    });
                    alertDialog.create().show();
                    v.setSelected(false);
                    mode.finish();
                    return true;
                case R.id.delete:
                    adapter.deleteItem(position);
                    v.setSelected(false);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            myActMode = null;
        }
    };
}
