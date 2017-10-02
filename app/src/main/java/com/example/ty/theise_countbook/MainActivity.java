package com.example.ty.theise_countbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    //private String alert_title = "";

    private EditText name;
    private EditText inValue;
    private EditText comment;

    private int updIdx;

    public ListView oldCounterList;

    private ArrayList<Counter> counterList;
    private ArrayAdapter<Counter> adapter;

    private Counter updCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        inValue = (EditText) findViewById(R.id.inValue);
        comment = (EditText) findViewById(R.id.comment);

        Button saveButton = (Button) findViewById(R.id.save);
        oldCounterList = (ListView) findViewById(R.id.counterListView);

        oldCounterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                updIdx = i;
                Object objS = oldCounterList.getItemAtPosition(i);
                Counter sCounter = (Counter)objS;

                Gson gS = new Gson();
                String target = gS.toJson(sCounter);

                Intent intent = new Intent(MainActivity.this, CounterActivity.class);
                intent.putExtra("CounterAsString", target);
                startActivityForResult(intent, 1);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String strName = name.getText().toString();
                String strInValue = inValue.getText().toString();

                if (strName.length() != 0 & strName.length() < 50) {
                    if (strInValue.length() != 0) {

                        String strCom = comment.getText().toString();
                        int intInValue = Integer.parseInt(inValue.getText().toString());
                        Counter newCounter = new Counter(strName, intInValue, strCom);

                        counterList.add(newCounter);

                        adapter.notifyDataSetChanged();

                        saveInFile();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String target = data.getStringExtra("CounterAsString");
            Gson gson = new Gson();
            updCounter = gson.fromJson(target, Counter.class);

            if (updCounter != null) {
                counterList.set(updIdx, updCounter);
            }

            else {
                counterList.remove(updIdx);
            }

            adapter.notifyDataSetChanged();
            saveInFile();
        }
    };

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Counter>(this, R.layout.list_item, counterList);
        oldCounterList.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counterList = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
    private void deleteCounters() {
        counterList.clear();

        File file = getFilesDir();
        //

        // String path = Context.getFilesDir().getAbsolutePath();

        File file_del = new File (file, FILENAME);
        file_del.delete();
        //adapter.notifyDataSetChanged();
    }

}