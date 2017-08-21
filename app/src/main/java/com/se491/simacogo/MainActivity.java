package com.se491.simacogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText numPlies = (EditText) findViewById(R.id.numPlies);
        Button start = (Button) findViewById(R.id.startBtn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = numPlies.getText().toString();
                if( temp.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter a number", Toast.LENGTH_SHORT).show();
                }else{
                    int num = Integer.parseInt(temp);
                    if(num <1 || num > 10){
                        Toast.makeText(MainActivity.this, "Enter a number between 1 and 10", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent newIntent = new Intent(MainActivity.this, GameActivity.class);
                        newIntent.putExtra("numPlies", num);
                        startActivity(newIntent);
                    }
                }

            }
        });
    }

}
