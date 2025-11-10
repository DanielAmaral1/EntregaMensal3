@echo off
echo ========================================
echo    INICIANDO SISTEMA BARBEARIA
echo ========================================
echo.
echo Iniciando Backend (Spring Boot) na porta 9090...
cd Barbearia4Periodo-master\Barbearia4Periodo-master
start "Backend Barbearia - Porta 9090" cmd /k "mvnw.cmd spring-boot:run"

echo.
echo Aguardando 15 segundos para iniciar o Frontend...
timeout /t 15 /nobreak > nul

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