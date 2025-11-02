package com.nexdom.autorizacao.config;

import jakarta.servlet.annotation.WebListener;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseInit {

    public static void init(){
        try{
            String url = "jdbc:h2:mem:nexdom;DB_CLOSE_DELAY=-1";
            Connection conn = DriverManager.getConnection(url, "sa", "sa");

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));

            Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml", new ClassLoaderResourceAccessor(), database);


            liquibase.update("");

            System.out.println("Ok");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
