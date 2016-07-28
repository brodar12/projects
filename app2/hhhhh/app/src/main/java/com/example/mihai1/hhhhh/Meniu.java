package com.example.mihai1.hhhhh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Meniu extends AppCompatActivity {

    Button but,but1,but2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meniu);

        but=(Button) findViewById(R.id.button4);
        but1=(Button) findViewById(R.id.button5);
        but2=(Button) findViewById(R.id.button7);

        but.setOnClickListener(click);
        but1.setOnClickListener(click);
        but2.setOnClickListener(click);
    }

    View.OnClickListener click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.button4){
                Log.e("go in ", "MainActivity!!!");
                Intent intent = new Intent(Meniu.this, MainActivity.class);
                startActivity(intent);
            }
            else if(v.getId()==R.id.button5)
            {
                Log.e("go in ", "search!!!");
                Intent intent = new Intent(Meniu.this, search.class);
                startActivity(intent);
            }
            else if(v.getId()==R.id.button7)
            {
                Log.e("go in ", "Assign!!!");
                Intent intent = new Intent(Meniu.this, Assign.class);
                startActivity(intent);
            }
        }
    };

}
