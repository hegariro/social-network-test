# =========================================
# Multi-stage build
# =========================================

# Etapa 1: Build de la aplicación
FROM maven:3.9.9-amazoncorretto-21 AS builder

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven primero (para cache de dependencias)
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

# Hacer ejecutable el wrapper de Maven
RUN chmod +x mvnw

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src/

# Compilar y empaquetar la aplicación
RUN mvn clean package -DskipTests -B

# =========================================
# Etapa 2: Solo para tests (opcional)
FROM builder AS test-stage

# Esta etapa está disponible para ejecutar tests
# Los tests se ejecutarán cuando se especifique al run del contenedor
# Ejemplo: docker run --rm test-stage mvn test

# =========================================
# Etapa 3: Imagen runtime optimizada
# =========================================
FROM maven:3.9.9-amazoncorretto-21 AS runtime

# Instalar herramientas necesarias
RUN yum install -y curl tzdata && yum clean all

# Configurar zona horaria
ENV TZ=America/Bogota

# Crear usuario no-root para seguridad
RUN groupadd -g 1000 appgroup && \
        useradd -u 1000 -g appgroup -m -s /bin/bash appuser

# Crear directorios de trabajo
RUN mkdir -p /app/logs /app/tmp && chown -R appuser:appgroup /app

# Cambiar al usuario no-root
USER appuser

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=builder --chown=appuser:appgroup /app/target/*.jar app.jar

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SPRING_PROFILES_ACTIVE=docker \
    SERVER_PORT=8081 \
    LOGGING_LEVEL_ROOT=DEBUG \
    MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info,metrics

# Exponer puerto
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8081/api/actuator/health || exit 1

# Configurar JVM para contenedores
ENV JAVA_OPTS="$JAVA_OPTS \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -XX:+OptimizeStringConcat \
    -XX:+UseStringDeduplication \
    -XX:+ExitOnOutOfMemoryError \
    -Djava.security.egd=file:/dev/./urandom \
    -Djava.awt.headless=true"

# Comando de inicio
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]

# Metadata adicional
LABEL org.opencontainers.image.title="Social Network Backend Microservice"
LABEL org.opencontainers.image.description="Microservicio de gestión backend"
LABEL org.opencontainers.image.vendor="Periferia"
