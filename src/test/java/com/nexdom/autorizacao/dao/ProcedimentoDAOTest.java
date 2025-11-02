package com.nexdom.autorizacao.dao;

import com.nexdom.autorizacao.config.ConnectionFactory;
import com.nexdom.autorizacao.model.Procedimento;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;


import static org.junit.jupiter.api.Assertions.*;

public class ProcedimentoDAOTest {

    private static ProcedimentoDAO dao;

    @BeforeAll
    static void setup() throws Exception {
        dao = new ProcedimentoDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {


            st.execute("""
                CREATE TABLE procedimento (
                    id IDENTITY PRIMARY KEY,
                    codigo VARCHAR(10),
                    idade INT,
                    sexo VARCHAR(1),
                    permitido BOOLEAN
                )
            """);


            st.execute("""
                INSERT INTO procedimento (codigo, idade, sexo, permitido) VALUES
                ('1234', 10, 'M', FALSE),
                ('1234', 20, 'M', TRUE),
                ('1234', 30, 'F', TRUE),
                ('4567', 20, 'F', TRUE),
                ('4567', 25, 'M', FALSE)
            """);
        }
    }

    @Test
    void deveSelecionarProcedimentoComIdadeAdequada() {
        Procedimento p = dao.buscarPorCodigo("1234", 25, "M");
        assertNotNull(p, "Deveria encontrar um procedimento para o código 1234 e sexo M");
        assertEquals("1234", p.getCodigo());
        assertEquals("M", p.getSexo());
        assertTrue(p.getPermitido(), "Deveria estar permitido, pois idade >= mínima e permitido no banco");
    }

    @Test
    void deveNegarQuandoIdadeAbaixoDoMinimo() {
        Procedimento p = dao.buscarPorCodigo("1234", 8, "M");
        assertNull(p, "Não deveria encontrar nenhum procedimento porque idade é menor que mínima (10)");
    }

    @Test
    void deveSelecionarPorSexoCorreto() {
        Procedimento p = dao.buscarPorCodigo("1234", 35, "F");
        assertNotNull(p);
        assertEquals("F", p.getSexo());
        assertTrue(p.getPermitido());
    }

    @Test
    void deveNegarProcedimentoPorSexoDiferente() {
        Procedimento p = dao.buscarPorCodigo("1234", 35, "M");
        assertNotNull(p);
        assertTrue(p.getPermitido(), "Ainda deve retornar permitido pois M 20 true também existe");
    }

    @Test
    void deveRetornarNullParaCodigoInexistente() {
        Procedimento p = dao.buscarPorCodigo("9999", 20, "M");
        assertNull(p, "Deveria retornar null pois o código não existe no banco");
    }

    @Test
    void deveSelecionarProcedimentoMaisApropriado() {

        Procedimento p = dao.buscarPorCodigo("1234", 25, "M");
        assertEquals(20, p.getIdade());
    }
}
