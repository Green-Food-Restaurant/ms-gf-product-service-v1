FROM maven:latest

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db-product:5432/fiap-lanches-product
WORKDIR /app
RUN rm -rf /app/*
COPY . /app
RUN mvn clean install -DskipTests
RUN mkdir jar
RUN mv /app/target/fiap-lanches-product-0.0.1-SNAPSHOT.jar /app/jar/fiap-lanches-product-app.jar
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/jar/fiap-lanches-product-app.jar"]
