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

    public EditText editTextName, editTextEmail;
    public TextView textViewDOB;
    public RadioGroup radioGroup;
    public ImageView imageView1, imageView2, imageView3;
    public Button buttonSave;
    public DatabaseHelper databaseHelper;
    public int selectedImagePath = 0;
    String path ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add); // Replace with your layout name

        editTextName = findViewById(R.id.editTextName);
        textViewDOB = findViewById(R.id.textViewDOB);
        editTextEmail = findViewById(R.id.editTextEmail);



        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        buttonSave = findViewById(R.id.buttonSave);
        databaseHelper = new DatabaseHelper(this);

        setupDatePicker();
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




    private void setupSaveButton() {
        buttonSave.setOnClickListener(v -> {
            // Lấy thông tin từ các trường nhập liệu
            String name = editTextName.getText().toString().trim();
            String dob = textViewDOB.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            radioGroup = findViewById(R.id.radioGroup);
            RadioButton setCheckRdb = findViewById(radioGroup.getCheckedRadioButtonId());

            if (setCheckRdb == findViewById(R.id.radioButton1))
            {
                path = String.valueOf(R.drawable.vector_1);
            }
            else if (setCheckRdb == findViewById(R.id.radioButton2))
            {
                path = String.valueOf(R.drawable.vector_2);
            }
            else {
                path = String.valueOf(R.drawable.vector_3);
            }


            // Kiểm tra xem tất cả các trường đã được điền
            if (!name.isEmpty() && !dob.equals("Click here to select date") && !email.isEmpty()) {
                // Lưu thông tin người dùng vào cơ sở dữ liệu
                boolean isInserted = databaseHelper.addUser(name, dob, email, path);
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