package com.nexdom.autorizacao.servlet;

import com.nexdom.autorizacao.dao.ProcedimentoDAO;
import com.nexdom.autorizacao.model.Procedimento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/autorizacao")
public class AutorizacaoServlet extends HttpServlet {

    private final ProcedimentoDAO dao = new ProcedimentoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("AutorizacaoServlet foi chamado!");

        String codigo = req.getParameter("codigo");
        String idadeStr = req.getParameter("idade");
        String sexo = req.getParameter("sexo");

        System.out.println("codigo=" + codigo + ", idade=" + idadeStr + ", sexo=" + sexo);
        if (codigo == null || idadeStr == null || sexo == null ||
                codigo.isBlank() || idadeStr.isBlank() || sexo.isBlank()) {

            resp.getWriter().write("{\"autorizado\":false, \"mensagem\":\"Campos obrigatórios não informados.\"}");
            System.out.println("PAROU AQUI PARTE1");
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException e) {
            System.out.println("PAROU AQUI PARTE2");
            resp.getWriter().write("{\"autorizado\":false, \"mensagem\":\"Idade inválida.\"}");
            return;
        }
        System.out.println("PAROU AQUI PARTE3");
        Procedimento p = dao.buscarPorCodigo(codigo, idade, sexo);
        resp.setContentType("application/json;charset=UTF-8");

        boolean autorizado = false;
        String mensagem;

        if (p == null) {
            mensagem = "Procedimento negado.";
        } else if (!p.getPermitido()) {
            mensagem = "Procedimento encontrado, mas não permitido.";
        } else {
            mensagem = "Procedimento autorizado!";
            autorizado = true;
        }


        String json = String.format(
                "{\"codigo\":\"%s\", \"autorizado\":%s, \"mensagem\":\"%s\"}",
                codigo, autorizado, mensagem
        );
        resp.getWriter().write(json);
    }

}

