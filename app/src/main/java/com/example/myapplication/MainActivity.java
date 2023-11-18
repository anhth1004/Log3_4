package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button buttonAddUser;
    private UserAdapter userAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        buttonAddUser = findViewById(R.id.buttonAddUser);
        databaseHelper = new DatabaseHelper(this);

        setUpRecyclerView();
        buttonAddUser.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddActivity.class)));

        // Tải lại dữ liệu khi quay trở lại từ một Activity khác
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(this::loadUserData);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);
    }

    private void loadUserData() {
        // Lấy dữ liệu người dùng từ cơ sở dữ liệu
        userList.clear();
        Cursor cursor = databaseHelper.getAllUsers();
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DOB)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_PATH))
                );
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        userAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData(); // Tải lại dữ liệu khi Activity được kích hoạt lại
    }
}
