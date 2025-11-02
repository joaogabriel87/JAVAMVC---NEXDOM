# NexDom

---

##  Tecnologias Utilizadas

| Tecnologia
|-------------|
|  **Java 21+** |
|  **Jakarta EE (Servlet + JSP)** | 
|  **Liquibase** |
|  **H2 Database** |
|  **JUnit 5 + Mockito** |
|  **Maven 3.9+** |
|  **Docker + WildFly 38** |

---

##  Compilar e Executar o Projeto

### Pré-requisitos

- **Java 21+ (JDK)**
- **Apache Maven 3.9+**
- **WildFly 38+**
- **Docker** e **Docker Compose**

---

### 1. Compilar o projeto

Para gerar o arquivo `.war` da aplicação:

```bash
mvn clean package -DskipTests
```

Após a execução, o artefato será gerado em:

```
target/NexDom.war
```

---

##  Execução no WildFly Local


1. Copie o artefato `.war` para a pasta de deploys do WildFly:
   ```bash
   C:\wildfly-38.0.0.Final\standalone\deployments\
   ```

2. Inicie o servidor:
   ```bash
   standalone.bat
   ```

3. Acesse a aplicação no navegador:
   ```
   http://localhost:8080/nexdom/
   ```

---

##  Execução de Testes Automatizados



### ▶ Executar todos os testes
```bash
mvn test
```

###  Executar um teste específico
```bash
mvn -Dtest=ProcedimentoDAOTest test
```

###  Compilar ignorando os testes
```bash
mvn clean package -DskipTests
```

---

##  Execução com Docker

###  Gerar o arquivo `.war`

Antes de construir a imagem:
```bash
mvn clean package -DskipTests
```

---

###  Construir e iniciar o container

Com o Docker instalado, execute:
```bash
docker compose up --build
```

---

###  Acessar a aplicação

Após o build e inicialização:
```
http://localhost:8080/nexdom/
```

---

### 📄 Estrutura do `docker-compose.yml`



---

##  Exemplo de Requisição e Resposta

### Requisição (AJAX)
```json
{
  "codigo": "1234",
  "idade": 20,
  "sexo": "M"
}
```

### Resposta
```json
{
  "codigo": "1234",
  "autorizado": true,
  "mensagem": "Procedimento autorizado!"
}
```

---
