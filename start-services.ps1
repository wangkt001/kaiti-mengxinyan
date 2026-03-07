# Check and kill process using port 8080
Write-Host "Checking process using port 8080..."
try {
    $port8080 = Get-Process -Id (Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess)
    if ($port8080) {
        Write-Host "Found process using port 8080: $($port8080.Name) (PID: $($port8080.Id))"
        Write-Host "Killing the process..."
        $port8080 | Stop-Process -Force
        Write-Host "Process killed"
    } else {
        Write-Host "Port 8080 is not in use"
    }
} catch {
    Write-Host "Error checking port 8080: $_"
}

# Check and kill process using port 8081
Write-Host "Checking process using port 8081..."
try {
    $port8081 = Get-Process -Id (Get-NetTCPConnection -LocalPort 8081 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess)
    if ($port8081) {
        Write-Host "Found process using port 8081: $($port8081.Name) (PID: $($port8081.Id))"
        Write-Host "Killing the process..."
        $port8081 | Stop-Process -Force
        Write-Host "Process killed"
    } else {
        Write-Host "Port 8081 is not in use"
    }
} catch {
    Write-Host "Error checking port 8081: $_"
}

# Start backend service
Write-Host "Starting backend service..."
$backendDir = "d:\work\兼职\毕业设计\2026-03-06\kaiti-mengxinyan\second-hand-trade-backend"
Start-Process -FilePath "cmd.exe" -ArgumentList "/c mvn spring-boot:run" -WorkingDirectory $backendDir

# Wait for backend service to start
Write-Host "Waiting for backend service to start..."
Start-Sleep -Seconds 5

# Start frontend service
Write-Host "Starting frontend service..."
$frontendDir = "d:\work\兼职\毕业设计\2026-03-06\kaiti-mengxinyan\second-hand-trade-frontend"
Start-Process -FilePath "cmd.exe" -ArgumentList "/c npm run serve" -WorkingDirectory $frontendDir

Write-Host "Services started successfully!"
Write-Host "Backend service: http://localhost:8080/api"
Write-Host "Frontend service: http://localhost:8081"
