#!/bin/bash

# Script para compilar y ejecutar el backend

echo "================================"
echo "Grupo Familiar Backend Setup"
echo "================================"
echo ""

# Verificar que Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado. Por favor instálalo primero."
    exit 1
fi

echo "✅ Maven encontrado"

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado. Por favor instálalo primero."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -1)
echo "✅ $JAVA_VERSION"

echo ""
echo "Compilando el proyecto..."
mvn clean install

if [ $? -ne 0 ]; then
    echo "❌ Error en la compilación"
    exit 1
fi

echo ""
echo "================================"
echo "✅ Compilación exitosa"
echo "================================"
echo ""
echo "Iniciando la aplicación..."
echo ""
echo "La API estará disponible en:"
echo "  - API Base: http://localhost:8080/api"
echo "  - Swagger UI: http://localhost:8080/api/swagger-ui.html"
echo ""
echo "Credenciales de prueba:"
echo "  - Email: admin@grupofamiliar.com"
echo "  - Contraseña: admin123"
echo ""

mvn spring-boot:run
