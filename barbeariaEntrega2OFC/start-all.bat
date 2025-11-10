@echo off
echo ========================================
echo    INICIANDO SISTEMA BARBEARIA
echo ========================================
echo.
echo Iniciando Backend (Spring Boot)...
cd Barbearia4Periodo-master\Barbearia4Periodo-master
start "Backend Barbearia - Porta 8081" cmd /k "mvnw.cmd spring-boot:run"

echo.
echo Aguardando 10 segundos para iniciar o Frontend...
timeout /t 10 /nobreak > nul

echo.
echo Iniciando Frontend (Angular)...
cd ..\..\barbearia-app
start "Frontend Barbearia - Porta 4200" cmd /k "ng serve"

echo.
echo ========================================
echo    SISTEMA INICIADO COM SUCESSO!
echo ========================================
echo.
echo Backend: http://localhost:8081/api
echo Frontend: http://localhost:4200
echo.
echo Pressione qualquer tecla para sair...
pause > nul