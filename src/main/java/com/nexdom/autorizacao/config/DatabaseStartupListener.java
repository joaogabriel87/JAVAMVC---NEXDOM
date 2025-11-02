package com.nexdom.autorizacao.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){
        System.out.println("Conectado com sucesso");
        DatabaseInit.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        System.out.println("Desconectado com sucesso");
    }
}
