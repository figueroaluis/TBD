package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class AddListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingActionButton createList;
    private TextInputEditText editText;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_list_activity);

        mContext = this;

        mToolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent backButton = new Intent(getApplicationContext(), MainActivity.class);
                mContext.startActivity(backButton);
            }
        });

        if(getSupportActionBar()!=null){
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        editText = findViewById(R.id.enter_new_list_name_edittext);
        createList = findViewById(R.id.add_new_list_fab);

    }

    public void addNewList(View view){
        Intent addList = new Intent(getApplicationContext(), MainActivity.class);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        ListDAO listDAO = database.getListDAO();
        TaskList list = new TaskList(editText.getText().toString());
        listDAO.insert(list);

        this.startActivity(addList);

        Toast.makeText(AddListActivity.this, "Successfull Added List", Toast.LENGTH_SHORT).show();
    }


}
