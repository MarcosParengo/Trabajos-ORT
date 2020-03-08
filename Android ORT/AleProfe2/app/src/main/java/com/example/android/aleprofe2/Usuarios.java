package com.example.android.aleprofe2;

public class Usuarios {

    private String password;
    private String username;

    public Usuarios(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public Usuarios (){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

