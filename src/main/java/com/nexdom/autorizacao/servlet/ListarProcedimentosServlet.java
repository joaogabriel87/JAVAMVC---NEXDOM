package com.nexdom.autorizacao.servlet;

import com.nexdom.autorizacao.dao.ProcedimentoDAO;
import com.nexdom.autorizacao.model.Procedimento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/listarprocedimentos")
public class ListarProcedimentosServlet extends HttpServlet {

   private final ProcedimentoDAO dao = new ProcedimentoDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Procedimento> lista = dao.listar();

        req.setAttribute("procedimentos", lista);

        req.getRequestDispatcher("/procedimento/listar.jsp").forward(req,resp);
    }
}
