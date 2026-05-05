## ⚠️ ANTES DE EJECUTAR - CHECKLIST

Antes de compilar y ejecutar el backend, verifica que:

### 1. ✅ Requisitos del Sistema
- [ ] Java 17 o superior instalado: `java -version`
- [ ] Maven 3.6+ instalado: `mvn -version`
- [ ] MariaDB/MySQL 8.0+ instalado
- [ ] Git instalado (si lo necesitas)

### 2. ✅ Base de Datos

**Opción A: Usando el script SQL**
```bash
mysql -u root -p < src/main/resources/init.sql
```

**Opción B: Manualmente**
```sql
-- Conectarte a MySQL/MariaDB como root
mysql -u root -p

-- Ejecutar estos comandos:
CREATE DATABASE IF NOT EXISTS grupo_familiar DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE grupo_familiar;

-- Luego copia el contenido del script init.sql y pégalo
```

### 3. ✅ Configuración de application.properties

Editar: `src/main/resources/application.properties`

```properties
# CAMBIAR ESTAS LÍNEAS CON TUS VALORES:
spring.datasource.url=jdbc:mariadb://localhost:3306/grupo_familiar
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA_AQUI

# IMPORTANTE: Cambiar en producción
app.jwt.secret=your-super-secret-key-change-this-in-production-environment-very-important-key-256bit-minimum
```

### 4. ✅ Variables de Entorno (Opcional)

Puedes crear variables de entorno para que no esté hardcodeada la contraseña:

**En Windows:**
```cmd
set MYSQL_PASSWORD=tu_contraseña
set JWT_SECRET=tu-secreto-jwt
```

**En Linux/Mac:**
```bash
export MYSQL_PASSWORD=tu_contraseña
export JWT_SECRET=tu-secreto-jwt
```

### 5. ✅ Puertos

Verificar que estos puertos estén disponibles:
- [ ] Puerto 8080 (API)
- [ ] Puerto 3306 (MySQL/MariaDB)

Si están en uso, cambiar:
- API: `server.port=8081` en application.properties
- MySQL: Cambiar puerto de conexión

### 6. ✅ Verificar Dependencias

```bash
mvn dependency:check
```

---

## 🚀 PASOS PARA EJECUTAR

### 1. Compilar el Proyecto
```bash
mvn clean install
```

El proyecto debería compilar sin errores. Espera hasta 2-3 minutos.

### 2. Ejecutar la Aplicación

**Opción A: Con Maven**
```bash
mvn spring-boot:run
```

**Opción B: Con el script (Windows)**
```cmd
run.bat
```

**Opción C: Con el script (Linux/Mac)**
```bash
chmod +x run.sh
./run.sh
```

**Opción D: JAR compilado**
```bash
java -jar target/grupofamiliar_backend-0.0.1-SNAPSHOT.jar
```

### 3. Verificar que Está Running

Deberías ver algo como:
```
====================================
Grupo Familiar Backend iniciado
API disponible en: http://localhost:8080/api
Swagger UI: http://localhost:8080/api/swagger-ui.html
====================================
```

### 4. Probar los Endpoints

#### En Navegador (Swagger UI)
```
http://localhost:8080/api/swagger-ui.html
```

#### Con curl (Login)
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@grupofamiliar.com",
    "password": "admin123"
  }'
```

#### Con Postman
1. Importar: `Grupo_Familiar_API.postman_collection.json`
2. Configurar variables:
   - `base_url`: http://localhost:8080/api
3. Ir a "Auth" → "Login" → "Send"

---

## 🐛 SOLUCIÓN DE PROBLEMAS

### Error: Connection refused
```
java.sql.SQLException: Could not connect to address=(host=localhost)(port=3306)
```
**Solución:**
- [ ] Verificar que MySQL está ejecutándose
- [ ] Verificar puerto 3306
- [ ] Verificar credenciales en application.properties

### Error: Access denied for user
```
java.sql.SQLInvalidAuthorizationSpecException: Access denied
```
**Solución:**
- [ ] Verificar usuario y contraseña en application.properties
- [ ] Verificar que la BD existe
- [ ] Ejecutar el script init.sql

### Error: Port already in use
```
Address already in use: localhost:8080
```
**Solución:**
- Cambiar puerto: `server.port=8081` en application.properties
- O matar proceso en puerto 8080

### Error: Maven not found
```
'mvn' is not recognized
```
**Solución:**
- [ ] Usar Maven Wrapper: `mvnw clean install` (Windows)
- [ ] O: `./mvnw clean install` (Linux/Mac)
- O instalar Maven globalmente

### Error: Java version
```
The Java version does not match
```
**Solución:**
- Verificar Java 17+: `java -version`
- Actualizar Java si es necesario

---

## ✨ VERIFICACIÓN FINAL

Una vez que esté ejecutándose, verifica:

1. **Acceso a Swagger**
   - Abre: http://localhost:8080/api/swagger-ui.html
   - Deberías ver todos los endpoints documentados

2. **Test de Login**
   - En Swagger, encuentra AuthController → Login
   - Email: `admin@grupofamiliar.com`
   - Password: `admin123`
   - Presiona "Try it out" y luego "Execute"

3. **Test de Usuario Autenticado**
   - En Swagger, encuentra UsuarioController → GET /usuarios
   - Haz clic en el botón de autorización 🔒
   - Pega el `accessToken` que recibiste del login
   - Presiona "Try it out" y luego "Execute"

---

## 📞 Si Algo No Funciona

1. Revisa los logs en la consola
2. Verifica que todos los requisitos están instalados
3. Confirma que application.properties está correctamente configurado
4. Revisa la documentación en README_BACKEND.md

---

**¡Listo! El backend debería estar funcionando correctamente.**
