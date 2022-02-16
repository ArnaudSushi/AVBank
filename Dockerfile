FROM openjdk:16-jdk
WORKDIR /avbank
COPY . .
RUN javac -d src/component src/component/*.java
RUN javac -classpath .:lib/json_simple-1.1.jar:src/ src/main/Main.java
CMD ["java", "-cp", ":lib/json_simple-1.1.jar:bin", "main.Main"]