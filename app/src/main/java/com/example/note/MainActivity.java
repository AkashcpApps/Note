package com.example.note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks ,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;
    TextView help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(MainActivity.this);

        editName = (EditText)findViewById(R.id.editText_name);
       help = (TextView) findViewById(R.id.textViewac);
        editTextId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        btnviewUpdate= (Button)findViewById(R.id.button_update);
        btnDelete= (Button)findViewById(R.id.button_delete);
        //editTextId.setVisibility(View.INVISIBLE);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder aler=new AlertDialog.Builder(MainActivity.this);
                aler.setTitle("HELP").setMessage("1)ADD DATA:To Add the NOTE\n2)VIEW:View the NOTE\n3)ALTER:to make changes in existing NOTE\n4)DELETE:Delete the NOTE\n***********\nUse the Note number to\n*Alter\n*Delete");
                aler.show();
                aler.setNegativeButton("OK",null);
            }
        });
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada).duration(500).repeat(1)
                                .playOn(findViewById(R.id.button_delete));
                       // editTextId.setVisibility(View.VISIBLE);
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0) {
                            Toast.makeText(MainActivity.this, "Data Deleted of number"+editTextId.getText().toString(), Toast.LENGTH_LONG).show();
                           // editTextId.setVisibility(View.INVISIBLE);
                        }
                        else
                            Toast.makeText(MainActivity.this,"Nothing not Deleted",Toast.LENGTH_LONG).show();
                       // editTextId.setVisibility(View.INVISIBLE);
                    }
                }
        );
    }
    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada).duration(500).repeat(1)
                                .playOn(findViewById(R.id.button_update));
                        if (editTextId.getText().toString().isEmpty()) {

                            Toast.makeText(MainActivity.this, "Enter Serial Number to\n\t\t\tAlter the Note", Toast.LENGTH_SHORT).show();

                        } else {
                            boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                    editName.getText().toString());
                            if (isUpdate == true)
                                Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Enter the Number to update", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada).duration(500).repeat(1)
                                .playOn(findViewById(R.id.button_add));
                        if (editName.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Please enter some data to Add", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            boolean isInserted = myDb.insertData(editName.getText().toString());
                            if (isInserted == true){
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            editName.getText().clear();
                            }
                            else
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada).duration(500).repeat(1)
                                .playOn(findViewById(R.id.button_viewAll));
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("No Memo","Empty");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Number:\t"+ res.getString(0)+"\n\n");
                            buffer.append("Note:\t"+ res.getString(1)+"\n");
                        }
                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



}