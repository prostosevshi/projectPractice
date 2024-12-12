# Используем официальный образ для Java
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем скомпилированный .jar файл в контейнер
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Открываем порт для приложения
EXPOSE 8081