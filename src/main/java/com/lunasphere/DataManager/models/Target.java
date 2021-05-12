package com.lunasphere.DataManager.models;

import java.util.ArrayList;
import java.util.List;

public class Target {
    String URL, Username, password;
    List<Database> databases;

    public Target(String URL, String username, String password) {
        this.URL = URL;
        Username = username;
        this.password = password;

        this.databases = new ArrayList<>();
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
    }
}
