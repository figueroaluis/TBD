package com.figueroaluis.finalproject271;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout addTaskLayout;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton add_to_do_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set mContext
        mContext = getApplicationContext();
        // get the main activity
        mActivity = MainActivity.this;

        // create the adapter
        mDrawerLayout = findViewById(R.id.main_layout);
        add_to_do_button =  findViewById(R.id.add_to_do_button);


        // first set on click listener to open the second activity
        add_to_do_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        // add new functions to make the app work better
    }
}
