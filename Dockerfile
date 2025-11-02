# Imagem base: WildFly com Java 22
FROM quay.io/wildfly/wildfly:latest

# Cria um diretório temporário para build
WORKDIR /opt/jboss

# Copia o WAR gerado pelo Maven para o diretório de deploy do WildFly
COPY target/nexdom.war /opt/jboss/wildfly/standalone/deployments/

# Expõe a porta HTTP padrão do WildFly
EXPOSE 8080

# Comando padrão para iniciar o servidor
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
