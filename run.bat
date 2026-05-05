@echo off

REM Script para compilar y ejecutar el backend en Windows

echo ================================
echo Grupo Familiar Backend Setup
echo ================================
echo.

REM Verificar que Maven está instalado
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven no esta instalado. Por favor instalalo primero.
    pause
    exit /b 1
)

echo OK: Maven encontrado

REM Verificar Java
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Java no esta instalado. Por favor instalalo primero.
    pause
    exit /b 1
)

for /f "tokens=*" %%i in ('java -version 2^>^&1 ^| findstr /r "version"') do set JAVA_VERSION=%%i
echo OK: %JAVA_VERSION%

echo.
echo Compilando el proyecto...
call mvnw.cmd clean install

if %errorlevel% neq 0 (
    echo ERROR: Hubo un error en la compilacion
    pause
    exit /b 1
)

echo.
echo ================================
echo OK: Compilacion exitosa
echo ================================
echo.
echo Iniciando la aplicacion...
echo.
echo La API estara disponible en:
echo   - API Base: http://localhost:8080/api
echo   - Swagger UI: http://localhost:8080/api/swagger-ui.html
echo.
echo Credenciales de prueba:
echo   - Email: admin@grupofamiliar.com
echo   - Contrasena: admin123
echo.

call mvnw.cmd spring-boot:run

pause
