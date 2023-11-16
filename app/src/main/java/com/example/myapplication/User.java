package com.example.myapplication;

public class User {
    private int id;
    private String name;
    private String dob;
    private String email;
    private String imagePath;

    public User(int id, String name, String dob, String email, String imagePath) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.imagePath = imagePath;
    }

    // Getter và Setter cho mỗi trường
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
