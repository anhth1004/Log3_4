package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "UserDatabase.db";

    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    // Tạo bảng SQL
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DOB + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_IMAGE_PATH + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Thêm dữ liệu người dùng mới
    public boolean addUser(String name, String dob, String email, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_IMAGE_PATH, imagePath);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // trả về true nếu thêm thành công
    }

    // Cập nhật thông tin người dùng
    public boolean updateUser(int id, String name, String dob, String email, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_IMAGE_PATH, imagePath);

        int result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] {String.valueOf(id)});
        db.close();

        return result > 0; // trả về true nếu cập nhật thành công
    }

    // Xóa thông tin người dùng
    public boolean deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] {String.valueOf(id)});
        db.close();

        return result > 0; // trả về true nếu xóa thành công
    }

    // Lấy danh sách tất cả người dùng
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Lấy thông tin chi tiết của một người dùng
    public Cursor getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[] {String.valueOf(id)});
    }

    public static String getColumnName() {
        return COLUMN_NAME;
    }

    public static String getColumnDob() {
        return COLUMN_DOB;
    }

    public static String getColumnEmail() {
        return COLUMN_EMAIL;
    }

    public static String getColumnImagePath() {
        return COLUMN_IMAGE_PATH;
    }
    public static String getColumnId() {
        return COLUMN_ID;
    }
}
