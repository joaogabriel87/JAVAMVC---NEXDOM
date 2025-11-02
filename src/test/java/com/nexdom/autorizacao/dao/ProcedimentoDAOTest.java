package com.nexdom.autorizacao.dao;

import com.nexdom.autorizacao.config.ConnectionFactory;
import com.nexdom.autorizacao.model.Procedimento;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProcedimentoDAOTest {

    private final ProcedimentoDAO dao = new ProcedimentoDAO();

    @BeforeEach
    void initDatabase() throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        Statement st = conn.createStatement();

        st.execute("DROP TABLE IF EXISTS procedimento");
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
            ('4567', 20, 'M', TRUE),
            ('6789', 10, 'F', FALSE),
            ('6789', 10, 'M', TRUE),
            ('1234', 20, 'M', TRUE),
            ('4567', 30, 'F', TRUE)
        """);

        st.close();
        conn.close();
    }

    @Test
    @Order(1)
    void deveListarTodosProcedimentos() {
        List<Procedimento> lista = dao.listar();
        Assertions.assertEquals(6, lista.size(), "Deve ter 6 procedimentos iniciais");
    }

    @Test
    @Order(2)
    void deveEncontrarProcedimentoPermitido() {
        Procedimento p = dao.buscarPorCodigo("4567", 20, "M");
        Assertions.assertNotNull(p);
        Assertions.assertTrue(p.getPermitido());
    }

    @Test
    @Order(3)
    void deveNegarProcedimentoInexistente() {
        Procedimento p = dao.buscarPorCodigo("9999", 50, "M");
        Assertions.assertNull(p);
    }
}
