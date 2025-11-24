@echo off
echo Verificando servicos...
echo.

echo 1. Verificando se o backend esta rodando na porta 8086...
netstat -an | findstr :8086
if %errorlevel% == 0 (
    echo ✓ Backend rodando na porta 8086
) else (
    echo ✗ Backend NAO esta rodando na porta 8086
)
echo.

echo 2. Verificando se o frontend esta rodando na porta 4200...
netstat -an | findstr :4200
if %errorlevel% == 0 (
    echo ✓ Frontend rodando na porta 4200
) else (
    echo ✗ Frontend NAO esta rodando na porta 4200
)
echo.

echo 3. Verificando se o MySQL esta rodando na porta 3306...
netstat -an | findstr :3306
if %errorlevel% == 0 (
    echo ✓ MySQL rodando na porta 3306
) else (
    echo ✗ MySQL NAO esta rodando na porta 3306
)
echo.

echo 4. Testando conexao com o backend...
curl -s http://localhost:8086/api/funcionarios > nul
if %errorlevel% == 0 (
    echo ✓ Backend respondendo
) else (
    echo ✗ Backend NAO esta respondendo
)
echo.

pause