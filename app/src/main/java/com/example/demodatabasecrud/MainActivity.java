package com.example.demodatabasecrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd,  btnRetrieve;

    EditText etContent;
    ArrayList<String> al;
    ArrayAdapter aa;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd=findViewById(R.id.btnAdd);

        btnRetrieve=findViewById(R.id.buttonRetrieve);

        etContent=findViewById(R.id.etContent);


        lv = findViewById(R.id.lv1);

        al=new ArrayList<>();
        al.add("note123");
        al.add("note456");

        aa = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, al);

        lv.setAdapter(aa); //null.setAdapter()





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                String data = al.get(position);
                String id = data.split(",")[0].split(":")[1];
                String content = data.split(",")[1].trim();

                Note target = new Note(Integer.parseInt(id), content);
                i.putExtra("data", target);
                startActivityForResult(i, 9);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=etContent.getText().toString();
                DBHelper dbh=new DBHelper(MainActivity.this);
                long row_affected=dbh.insertNote(data);
                dbh.close();

                if (row_affected != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllNotes());
                dbh.close();

//                String txt = "";
//                for (int i = 0; i< al.size(); i++){
//                    String tmp = al.get(i);
//                    txt += tmp + "\n";
//                }
//                tvDBContent.setText(txt);
                aa.notifyDataSetChanged();
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            btnRetrieve.performClick();
        }
    }

}
