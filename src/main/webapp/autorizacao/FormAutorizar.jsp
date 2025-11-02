<%--
  Created by IntelliJ IDEA.
  User: Joao Gabriel
  Date: 01/11/2025
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Autorização de Procedimento</title>


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 500px;
            margin-top: 70px;
        }
        .card {
            border-radius: 12px;
            box-shadow: 0 3px 8px rgba(0,0,0,0.15);
        }
        #resultado h4 {
            margin-top: 20px;
            font-weight: bold;
        }
    </style>

    <script>

        async function autorizar() {
            const form = document.getElementById("formAutorizacao");
            const data = new URLSearchParams();
            for (const [key, value] of new FormData(form)) {
                data.append(key, value);
            }

            try {
                const response = await fetch(form.action, {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: data
                });

                const result = await response.json();
                console.log("📦 Result recebido:", result);

                const div = document.getElementById("resultado");
                console.log("Div encontrada:", div);

                if (div) {
                    div.innerHTML = `
                <h4 class="\${result.autorizado ? 'text-success' : 'text-danger'}">
                    \${result.mensagem}
                </h4>
                <table class="table table-bordered mt-3">
                    <tr><th>Código</th><td>\${result.codigo}</td></tr>
                    <tr><th>Status</th><td>\${result.autorizado ? 'Autorizado' : 'Negado'}</td></tr>
                </table>
            `;
                }
            } catch (error) {
                document.getElementById("resultado").innerHTML =
                    `<h4 class='text-danger'>Erro: ${error.message}</h4>`;
            }
        }

    </script>
</head>

<body>
<div class="container">
    <div class="card p-4">
        <h3 class="text-center mb-3">Autorização de Procedimento</h3>


        <form id="formAutorizacao"
              action="${pageContext.request.contextPath}/autorizacao"
              method="post">

            <div class="mb-3">
                <label for="codigo" class="form-label">Código do Procedimento:</label>
                <input type="text" class="form-control" id="codigo" name="codigo" required>
            </div>

            <div class="mb-3">
                <label for="idade" class="form-label">Idade do Paciente:</label>
                <input type="number" class="form-control" id="idade" name="idade" min="0" required>
            </div>

            <div class="mb-3">
                <label for="sexo" class="form-label">Sexo do Paciente:</label>
                <select id="sexo" name="sexo" class="form-select" required>
                    <option value="">Selecione</option>
                    <option value="M">Masculino</option>
                    <option value="F">Feminino</option>
                </select>
            </div>

            <button type="button" class="btn btn-primary w-100" onclick="autorizar()">Verificar Autorização</button>
        </form>


        <div id="resultado" class="mt-4 text-center"></div>

    </div>
</div>
</body>
</html>
