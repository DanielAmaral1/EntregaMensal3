@echo off
echo ========================================
echo    LIMPANDO E INICIANDO BARBEARIA
echo ========================================
echo.

echo Finalizando processos antigos...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080 :8081 :4200 :4300 :9090"') do (
    if not "%%a"=="0" (
        taskkill /PID %%a /F 2>nul
    )
)

echo.
echo Aguardando 3 segundos...
timeout /t 3 /nobreak > nul

echo.
echo Iniciando Backend (Spring Boot) na porta 9090...
cd Barbearia4Periodo-master\Barbearia4Periodo-master
start "Backend Barbearia - Porta 9090" cmd /k "mvnw.cmd spring-boot:run"

echo.
echo Aguardando 20 segundos para o backend inicializar...
timeout /t 20 /nobreak > nul

echo.
echo Iniciando Frontend (Angular) na porta 4300...
cd ..\..\barbearia-app
start "Frontend Barbearia - Porta 4300" cmd /k "ng serve --port 4300"

echo.
echo ========================================
echo    SISTEMA INICIADO COM SUCESSO!
echo ========================================
echo.
echo Backend: http://localhost:9090/api
echo Frontend: http://localhost:4300
echo.
echo Pressione qualquer tecla para sair...
pause > nul