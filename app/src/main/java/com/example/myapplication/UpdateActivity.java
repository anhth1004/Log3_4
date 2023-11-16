package com.example.myapplication;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail;
    private TextView textViewDOB;
    private RadioGroup radioGroup;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;
    private String selectedImagePath = "";
    private int userId; // ID của người dùng cần cập nhật

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initializeViews();
        databaseHelper = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);
        loadData();

        setupDatePicker();
        setupRadioGroup();
        setupSaveButton();
    }

    private void initializeViews() {
        editTextName = findViewById(R.id.editTextName);
        textViewDOB = findViewById(R.id.textViewDOB);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroup = findViewById(R.id.radioGroup);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void loadData() {
        Cursor cursor = databaseHelper.getUser(userId);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnName()));
            String dob = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnDob()));
            String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnEmail()));
            selectedImagePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnImagePath()));


            editTextName.setText(name);
            textViewDOB.setText(dob);
            editTextEmail.setText(email);

            updateRadioGroup(selectedImagePath);
            cursor.close();
        }
    }

    private void setupDatePicker() {
        textViewDOB.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) ->
                            textViewDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            datePickerDialog.show();
        });
    }

    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton1) {
                selectedImagePath = "vector_1";
            } else if (checkedId == R.id.radioButton2) {
                selectedImagePath = "vector_2";
            } else if (checkedId == R.id.radioButton3) {
                selectedImagePath = "vector_3";
            }
        });
    }

    private void setupSaveButton() {
        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String dob = textViewDOB.getText().toString();
            String email = editTextEmail.getText().toString();

            if (validateInputs(name, dob, email)) {
                updateUserData(name, dob, email);
            }
        });
    }

    private boolean validateInputs(String name, String dob, String email) {
        if (name.isEmpty() || dob.equals("Click here to select date") || email.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateUserData(String name, String dob, String email) {
        boolean isUpdated = databaseHelper.updateUser(userId, name, dob, email, selectedImagePath);
        if (isUpdated) {
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRadioGroup(String imagePath) {
        switch (imagePath) {
            case "vector_1":
                ((RadioButton) findViewById(R.id.radioButton1)).setChecked(true);
                break;
            case "vector_2":
                ((RadioButton) findViewById(R.id.radioButton2)).setChecked(true);
                break;
            case "vector_3":
                ((RadioButton) findViewById(R.id.radioButton3)).setChecked(true);
                break;
        }
    }
}
