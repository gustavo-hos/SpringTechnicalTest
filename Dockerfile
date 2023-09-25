FROM openjdk:11-jre-slim

WORKDIR /app

COPY "build/libs/order_manager-1.0.jar" .

CMD ["java", "-jar", "order_manager-1.0.jar"]
