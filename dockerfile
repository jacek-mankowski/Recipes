FROM openjdk:11.0.13-jre-slim

ARG USER=docker
ARG UID=1000
ARG GID=1000
# default password for user
ARG PW=docker

RUN useradd -m ${USER} --uid=${UID} && echo "${USER}:${PW}" | chpasswd

USER ${UID}:${GID}

ARG HOME=/home/${USER}
WORKDIR ${HOME}
ARG JAR_FILE=/build/libs/recipes.jar
COPY ${JAR_FILE} ${HOME}/recipes.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","./recipes.jar"]
