package com.cesar.ahorcado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainDB {
    private String connURI;
    private Connection conn;
    private Statement stmt;

    public MainDB() {
    }

    public MainDB(String connURI) {
        this.connURI = connURI;
        load();
    }

    public String getConnURI() {
        return connURI;
    }

    public void setConnURI(String connURI) {
        this.connURI = connURI;
    }

    public Connection getConn() {
        return conn;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void load(){
        if (this.connURI != null){
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/cesar/ahorcado/db/" + connURI + ".db");
                stmt = conn.createStatement();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
