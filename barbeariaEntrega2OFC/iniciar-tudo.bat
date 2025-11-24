@echo off
echo Iniciando todos os servicos da Barbearia...
echo.

echo 1. Verificando se o MySQL esta rodando...
net start mysql80 2>nul
if %errorlevel% == 0 (
    echo ✓ MySQL iniciado
) else (
    echo ℹ MySQL ja estava rodando ou erro ao iniciar
)
echo.

echo 2. Aguardando MySQL inicializar...
timeout /t 3 /nobreak > nul

echo 3. Iniciando Backend Spring Boot...
cd "Barbearia4Periodo-master\Barbearia4Periodo-master"
start "Backend" cmd /k "mvn spring-boot:run"
echo ✓ Backend iniciando...
cd ..\..
echo.

echo 4. Aguardando Backend inicializar...
timeout /t 15 /nobreak > nul

echo 5. Iniciando Frontend Angular...
cd barbearia-app
start "Frontend" cmd /k "ng serve"
echo ✓ Frontend iniciando...
cd ..
echo.

echo 6. Aguardando servicos iniciarem completamente...
timeout /t 10 /nobreak > nul

echo.
echo ========================================
echo Servicos iniciados!
echo.
echo Backend: http://localhost:8086/api
echo Frontend: http://localhost:4200
echo.
echo Aguarde alguns segundos e acesse o frontend.
echo ========================================
echo.

pause