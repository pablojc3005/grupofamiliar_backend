#!/bin/bash

# Script con ejemplos de curl para probar los endpoints de la API

API_URL="http://localhost:8080/api"

# Colores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}================================${NC}"
echo -e "${BLUE}Grupo Familiar API - Test Script${NC}"
echo -e "${BLUE}================================${NC}"
echo ""

# ====================================
# 1. LOGIN
# ====================================
echo -e "${YELLOW}1. Realizando login...${NC}"

LOGIN_RESPONSE=$(curl -s -X POST "$API_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@grupofamiliar.com",
    "password": "admin123"
  }')

echo "$LOGIN_RESPONSE" | jq '.'
echo ""

# Extraer el access token
ACCESS_TOKEN=$(echo "$LOGIN_RESPONSE" | jq -r '.data.access_token // empty')
REFRESH_TOKEN=$(echo "$LOGIN_RESPONSE" | jq -r '.data.refresh_token // empty')

if [ -z "$ACCESS_TOKEN" ]; then
    echo -e "${YELLOW}⚠️  No se pudo obtener el access token${NC}"
    echo "Respuesta completa:"
    echo "$LOGIN_RESPONSE" | jq '.'
    exit 1
fi

echo -e "${GREEN}✓ Autenticado exitosamente${NC}"
echo -e "Token: ${BLUE}${ACCESS_TOKEN:0:50}...${NC}"
echo ""

# ====================================
# 2. OBTENER USUARIOS
# ====================================
echo -e "${YELLOW}2. Obteniendo lista de usuarios...${NC}"

curl -s -X GET "$API_URL/usuarios" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" | jq '.'

echo ""

# ====================================
# 3. OBTENER USUARIO POR ID
# ====================================
echo -e "${YELLOW}3. Obteniendo usuario con ID 1...${NC}"

curl -s -X GET "$API_URL/usuarios/1" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" | jq '.'

echo ""

# ====================================
# 4. OBTENER SECTORES
# ====================================
echo -e "${YELLOW}4. Obteniendo lista de sectores...${NC}"

curl -s -X GET "$API_URL/sectores" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" | jq '.'

echo ""

# ====================================
# 5. CREAR UN SECTOR
# ====================================
echo -e "${YELLOW}5. Creando un nuevo sector...${NC}"

SECTOR_RESPONSE=$(curl -s -X POST "$API_URL/sectores" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "SECTOR PRUEBA",
    "codigo": "SP1",
    "supervisorId": 3
  }')

echo "$SECTOR_RESPONSE" | jq '.'
SECTOR_ID=$(echo "$SECTOR_RESPONSE" | jq -r '.data.id // empty')
echo ""

# ====================================
# 6. CREAR UN GRUPO
# ====================================
if [ ! -z "$SECTOR_ID" ]; then
    echo -e "${YELLOW}6. Creando un nuevo grupo...${NC}"

    GRUPO_RESPONSE=$(curl -s -X POST "$API_URL/grupos" \
      -H "Authorization: Bearer $ACCESS_TOKEN" \
      -H "Content-Type: application/json" \
      -d "{
        \"nombre\": \"LIDER PRUEBA\",
        \"codigo\": \"LP1\",
        \"sectorId\": $SECTOR_ID,
        \"liderId\": 2
      }")

    echo "$GRUPO_RESPONSE" | jq '.'
    GRUPO_ID=$(echo "$GRUPO_RESPONSE" | jq -r '.data.id // empty')
    echo ""
else
    echo -e "${YELLOW}⚠️  No se pudo obtener el ID del sector${NC}"
    echo ""
fi

# ====================================
# 7. OBTENER GRUPOS
# ====================================
echo -e "${YELLOW}7. Obteniendo lista de grupos...${NC}"

curl -s -X GET "$API_URL/grupos" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" | jq '.'

echo ""

# ====================================
# 8. OBTENER REPORTES
# ====================================
echo -e "${YELLOW}8. Obteniendo lista de reportes...${NC}"

curl -s -X GET "$API_URL/reportes" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" | jq '.'

echo ""

# ====================================
# 9. REFRESCAR TOKEN
# ====================================
echo -e "${YELLOW}9. Refrescando token...${NC}"

curl -s -X POST "$API_URL/auth/refresh" \
  -H "Content-Type: application/json" \
  -d "{
    \"refreshToken\": \"$REFRESH_TOKEN\"
  }" | jq '.'

echo ""

# ====================================
# 10. LOGOUT
# ====================================
echo -e "${YELLOW}10. Cerrando sesión...${NC}"

curl -s -X POST "$API_URL/auth/logout" \
  -H "Content-Type: application/json" \
  -d "{
    \"refreshToken\": \"$REFRESH_TOKEN\"
  }" | jq '.'

echo ""
echo -e "${GREEN}================================${NC}"
echo -e "${GREEN}✓ Test completado${NC}"
echo -e "${GREEN}================================${NC}"
