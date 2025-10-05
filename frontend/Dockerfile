# Dockerfile para Frontend Vue.js con Vite
FROM node:20-alpine

# Establecer directorio de trabajo
WORKDIR /app

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar dependencias
RUN npm install

# Copiar el resto del código
COPY . .

# Crear directorio de logs
RUN mkdir -p /app/logs

# Exponer puerto
EXPOSE 3000

# Variables de entorno por defecto
ENV NODE_ENV=development
ENV VITE_APP_PORT=3000

# Comando para iniciar el servidor de desarrollo con logs
CMD ["sh", "-c", "npm run dev -- --host 0.0.0.0 --port $VITE_APP_PORT 2>&1 | tee -a /app/logs/frontend.log"]
