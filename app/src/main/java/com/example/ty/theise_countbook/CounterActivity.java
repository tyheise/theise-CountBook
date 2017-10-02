package com.example.ty.theise_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

/* The Activity that displays and allows editing of the Counter's details  */
public class CounterActivity extends AppCompatActivity {

    public Counter sCounter;

    private EditText name;
    private EditText currValue;
    private EditText inValue;
    private EditText comment;

    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        Button nameButton = (Button) findViewById(R.id.nameB);
        Button currButton = (Button) findViewById(R.id.currB);
        Button inButton = (Button) findViewById(R.id.inB);
        Button commButton = (Button) findViewById(R.id.commB);


        Button incButton = (Button) findViewById(R.id.increment);
        Button decButton = (Button) findViewById(R.id.decrement);
        Button resetButton = (Button) findViewById(R.id.reset);
        Button doneButton = (Button) findViewById(R.id.done);
        Button deleteButton =(Button) findViewById(R.id.delete);

        name = (EditText) findViewById(R.id.editName);
        currValue = (EditText) findViewById(R.id.editCurr);
        inValue =(EditText) findViewById(R.id.editIn);
        comment = (EditText) findViewById(R.id.editComm);

        date = (TextView) findViewById(R.id.dateView);

        /* Gets the Counter object in json form from the main activity */
        String target = getIntent().getStringExtra("CounterAsString");


        if (target != null) {

            /* Convert json back to normal object form */
            Gson gson = new Gson();
            sCounter = gson.fromJson(target, Counter.class);

            /* display Counter details on screen */
            name.setText(sCounter.getName());
            currValue.setText(Integer.toString(sCounter.getCurrValue()));
            inValue.setText(Integer.toString(sCounter.getInValue()));
            comment.setText(sCounter.getComment());
            date.setText((sCounter.getDate()).toString());
        }

        /* checks if name has been saved */
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                String newName = (name.getText()).toString();

                /* makes sure name is new and not null */
                if (!(sCounter.getName().equals(newName))) {
                    if (newName.length() != 0 & newName.length() < 50) {
                        sCounter.setName(newName);
                        name.setText(sCounter.getName());
                        date.setText((sCounter.getDate()).toString());
                }
                }
            }
                                      }
        );

        currButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                int newCurr = Integer.parseInt((currValue.getText()).toString());
                if (sCounter.getCurrValue()!= newCurr) {
                    sCounter.setCurrValue(newCurr);
                    currValue.setText(Integer.toString(sCounter.getCurrValue()));
                    date.setText((sCounter.getDate()).toString());
                }}
                                      }
        );

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                int newIn = Integer.parseInt((inValue.getText()).toString());
                if (sCounter.getInValue()!= newIn) {
                    sCounter.setInValue(newIn);
                    inValue.setText(Integer.toString(sCounter.getInValue()));
                    date.setText((sCounter.getDate()).toString());
                }
            }
                                      }
        );

        commButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                String newComm = comment.getText().toString();
                if (!(sCounter.getComment().equals(newComm))) {
                    sCounter.setComment(newComm);
                    comment.setText(sCounter.getComment());
                    date.setText((sCounter.getDate()).toString());
                }
            }
                                      }
        );

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                sCounter.increment();
                currValue.setText(Integer.toString(sCounter.getCurrValue()));
                date.setText((sCounter.getDate()).toString());
                                         }
                                     }
        );

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                sCounter.decrement();
                currValue.setText(Integer.toString(sCounter.getCurrValue()));
                date.setText((sCounter.getDate()).toString());
                                         }
                                     }
        );

        resetButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             setResult(RESULT_OK);
                                             if (sCounter.getCurrValue()!= sCounter.getInValue()) {
                                                 sCounter.reset();
                                                 currValue.setText(Integer.toString(sCounter.getCurrValue()));
                                                 date.setText((sCounter.getDate()).toString());
                                             }
                                         }
                                     }
        );

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                /* if delete is called set the counter object to null and exit the activity
                    returning the null counter */

                sCounter = null;

                Gson gS = new Gson();
                String strCounter = gS.toJson(sCounter);

                Intent returnCounter = new Intent();
                returnCounter.putExtra("CounterAsString", strCounter);
                setResult(RESULT_OK, returnCounter);
                finish();
            }
        } );

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                /* if done is called return the new counter so we can update the list */

                Gson gS = new Gson();
                String strCounter = gS.toJson(sCounter);

                Intent returnCounter = new Intent();
                returnCounter.putExtra("CounterAsString", strCounter);
                setResult(RESULT_OK, returnCounter);
                finish();
            }
        }
        );




    }
}
