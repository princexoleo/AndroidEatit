package com.app.leo.androideatit.Model;

/**
 * Created by Guest User on 3/23/2018.
 */

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String Table;
    private String IsStaff;

    public User() {
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User(String name, String password) {

        Name = name;
        Password = password;
        IsStaff="false";
    }
}
