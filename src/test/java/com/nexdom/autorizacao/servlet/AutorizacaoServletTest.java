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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Procedimento proc = new Procedimento(1L, "4567", 20, "M", true);
        when(daoMock.buscarPorCodigo("4567", 20, "M")).thenReturn(proc);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("codigo")).thenReturn("4567");
        when(req.getParameter("idade")).thenReturn("20");
        when(req.getParameter("sexo")).thenReturn("M");

        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doPost(req, resp);

        String result = sw.toString();
        assertTrue(result.contains("\"autorizado\":true"));
        assertTrue(result.contains("4567"));
    }

    @Test
    void deveNegarProcedimentoNaoPermitido() throws Exception {
        Procedimento proc = new Procedimento(2L, "1234", 10, "M", false);
        when(daoMock.buscarPorCodigo("1234", 10, "M")).thenReturn(proc);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("codigo")).thenReturn("1234");
        when(req.getParameter("idade")).thenReturn("10");
        when(req.getParameter("sexo")).thenReturn("M");

        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doPost(req, resp);

        String result = sw.toString();
        assertTrue(result.contains("\"autorizado\":false"));
        assertTrue(result.contains("não permitido"));
    }

    @Test
    void deveNegarProcedimentoInexistente() throws Exception {
        when(daoMock.buscarPorCodigo("9999", 99, "F")).thenReturn(null);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("codigo")).thenReturn("9999");
        when(req.getParameter("idade")).thenReturn("99");
        when(req.getParameter("sexo")).thenReturn("F");

        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doPost(req, resp);

        String result = sw.toString();
        assertTrue(result.contains("\"autorizado\":false"));
        assertTrue(result.contains("negado"));
    }
}
