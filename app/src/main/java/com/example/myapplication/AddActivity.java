package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail;
    private TextView textViewDOB;
    private RadioGroup radioGroup;
    private ImageView imageView1, imageView2, imageView3;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;
    private String selectedImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add); // Replace with your layout name

        editTextName = findViewById(R.id.editTextName);
        textViewDOB = findViewById(R.id.textViewDOB);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroup = findViewById(R.id.radioGroup);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        buttonSave = findViewById(R.id.buttonSave);
        databaseHelper = new DatabaseHelper(this);

        setupDatePicker();
        setupRadioGroup();
        setupSaveButton();
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

            if (!name.isEmpty() && !dob.equals("Click here to select date") && !email.isEmpty() && !selectedImagePath.isEmpty()) {
                boolean isInserted = databaseHelper.addUser(name, dob, email, selectedImagePath);
                if (isInserted) {
                    Toast.makeText(AddActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    finish(); // Quay trở lại activity trước
                } else {
                    Toast.makeText(AddActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
