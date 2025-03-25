package com.cesar.ahorcado;

import java.sql.*;

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

    public void load(){
        if (this.connURI != null){
            try {
                this.conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/cesar/ahorcado/db/" + connURI + ".db");
                this.stmt = this.conn.createStatement();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void execute(String sql){
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String sql){
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rs;
    }
}
