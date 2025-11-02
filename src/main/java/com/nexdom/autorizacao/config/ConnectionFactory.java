package com.nexdom.autorizacao.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static final String URL = "jdbc:h2:mem:nexdom;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    public static Connection getConnection(){
        try{
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar no banco de dados" + e.getMessage());
        }
    }
}
