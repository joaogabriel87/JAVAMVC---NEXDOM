package com.nexdom.autorizacao.servlet;

import com.nexdom.autorizacao.dao.ProcedimentoDAO;
import com.nexdom.autorizacao.model.Procedimento;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AutorizacaoServletTest {

    private AutorizacaoServlet servlet;
    private ProcedimentoDAO daoMock;

    @BeforeEach
    void setup() throws Exception {
        servlet = new AutorizacaoServlet();
        daoMock = mock(ProcedimentoDAO.class);


        var field = AutorizacaoServlet.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(servlet, daoMock);
    }

    @Test
    void deveAutorizarProcedimentoPermitido() throws Exception {
        Procedimento proc = new Procedimento(1L, "1234", 20, "M", true);
        when(daoMock.buscarPorCodigo("1234", 25, "M")).thenReturn(proc);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("codigo")).thenReturn("1234");
        when(req.getParameter("idade")).thenReturn("25");
        when(req.getParameter("sexo")).thenReturn("M");

        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doPost(req, resp);

        String result = sw.toString();
        System.out.println("JSON retornado (permitido): " + result);

        assertTrue(result.contains("\"autorizado\":true"));
        assertTrue(result.contains("autorizado"));
    }

    @Test
    void deveNegarProcedimentoComIdadeMenorQueMinima() throws Exception {

        Procedimento proc = new Procedimento(2L, "1234", 20, "M", false);
        when(daoMock.buscarPorCodigo("1234", 15, "M")).thenReturn(proc);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("codigo")).thenReturn("1234");
        when(req.getParameter("idade")).thenReturn("15");
        when(req.getParameter("sexo")).thenReturn("M");

        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doPost(req, resp);

        String result = sw.toString();
        System.out.println("JSON retornado (idade menor): " + result);

        assertTrue(result.contains("\"autorizado\":false"));
        assertTrue(result.toLowerCase().contains("não permitido") || result.toLowerCase().contains("nao permitido"));
    }

    @Test
    void deveNegarProcedimentoPorSexoIncorreto() throws Exception {

        Procedimento proc = new Procedimento(3L, "4567", 20, "M", false);
        when(daoMock.buscarPorCodigo("4567", 25, "F")).thenReturn(proc);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("codigo")).thenReturn("4567");
        when(req.getParameter("idade")).thenReturn("25");
        when(req.getParameter("sexo")).thenReturn("F");

        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doPost(req, resp);

        String result = sw.toString();
        System.out.println("JSON retornado (sexo incorreto): " + result);

        assertTrue(result.contains("\"autorizado\":false"));
        assertTrue(result.contains("não permitido") || result.contains("nao permitido"));
    }

    @Test
    void deveNegarProcedimentoInexistente() throws Exception {
        when(daoMock.buscarPorCodigo("9999", 40, "M")).thenReturn(null);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("codigo")).thenReturn("9999");
        when(req.getParameter("idade")).thenReturn("40");
        when(req.getParameter("sexo")).thenReturn("M");

        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doPost(req, resp);

        String result = sw.toString();
        System.out.println("JSON retornado (inexistente): " + result);

        assertTrue(result.contains("\"autorizado\":false"));
        assertTrue(result.toLowerCase().contains("negado") || result.toLowerCase().contains("nao encontrado"));
    }
}
