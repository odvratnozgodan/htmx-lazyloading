FROM gradle:8.2.0-jdk17 AS build

EXPOSE 8000

RUN mkdir /appbuild
COPY . /appbuild

WORKDIR /appbuild

# get install curl
RUN apt-get update
RUN apt-get install curl -y
# set which version of nodejs to use
RUN curl -sL https://deb.nodesource.com/setup_22.x | bash
RUN apt-get install nodejs
# confirm that it was successful
RUN node -v
# npm installs automatically
RUN npm -v
# npm installs automatically
RUN npx -v

ENV APPLICATION_USER=_1033
RUN useradd -ms /bin/bash $APPLICATION_USER

# Change the working directory to the one where tailwind.config.js is located
WORKDIR /appbuild/server

RUN npm link tailwindcss

# Change the working directory back to the project root
WORKDIR /appbuild

RUN gradle build

RUN ls -la
RUN ls -la build/libs/


FROM openjdk:17

ENV APPLICATION_USER=_1033
RUN useradd -ms /bin/bash $APPLICATION_USER

RUN ls -la

COPY --from=build /appbuild/build/libs/lazyloading-all.jar /app/lazyloading-all.jar
COPY --from=build /appbuild/src/main/resources/ /app/resources/
COPY --from=build /appbuild/assets/ /app/assets/

USER $APPLICATION_USER

WORKDIR /app

ENTRYPOINT ["java","-jar","/app/lazyloading-all.jar"]