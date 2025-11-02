package com.nexdom.autorizacao.dao;

import com.nexdom.autorizacao.config.ConnectionFactory;
import com.nexdom.autorizacao.model.Procedimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedimentoDAO {

    public List<Procedimento> listar() {
        List<Procedimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM procedimento";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Procedimento p = new Procedimento(
                        rs.getLong("id"),
                        rs.getString("codigo"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getBoolean("permitido")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public Procedimento buscarPorCodigo(String codigo, int idade, String sexo) {
        System.out.println("SQL codigo=" + codigo + ", idade=" + idade + ", sexo=" + sexo);

        String sql = "SELECT * FROM procedimento WHERE codigo = ? AND idade = ? AND sexo = ?";
        Procedimento procedimento = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.setInt(2, idade);
            ps.setString(3, sexo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                procedimento = new Procedimento(
                        rs.getLong("id"),
                        rs.getString("codigo"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getBoolean("permitido")
                );
                System.out.println("Encontrado: codigo =" + procedimento.getCodigo() +
                        ", idade=" + procedimento.getIdade() +
                        ", sexo=" + procedimento.getSexo() +
                        ", permitido=" + procedimento.getPermitido());
            } else {
                System.out.println("Nenhum resultado encontrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return procedimento;
    }


}
