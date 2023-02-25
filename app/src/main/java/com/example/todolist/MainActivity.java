package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private List<ToDoModel> taskList;
    private ToDoAdapter tasksAdapter;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Removes the action bar from the activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, MainActivity.this);
        taskRecyclerView.setAdapter(tasksAdapter);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList =  db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}