FROM openjdk:11.0.13-jre-slim
ARG HOME=/home/spring/recipes
WORKDIR ${HOME}
ARG JAR_FILE=/build/libs/recipes.jar
COPY ${JAR_FILE} ${HOME}/recipes.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","./recipes.jar"]
