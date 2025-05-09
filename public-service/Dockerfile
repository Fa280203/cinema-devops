# Étape 1 : build Maven multi-module
FROM maven:3.9.7-eclipse-temurin-17 AS build
WORKDIR /build

# On copie TOUT le monorepo : parent + modules
COPY . .

# On compile seulement le module voulu (+ ses dépendances)
RUN mvn -q -pl public-service -am package

# Étape 2 : Tomcat runtime
FROM tomcat:9.0-jdk11
COPY --from=build /build/public-service/target/public-service.war \
                  /usr/local/tomcat/webapps/
EXPOSE 8094
CMD ["catalina.sh", "run"]
