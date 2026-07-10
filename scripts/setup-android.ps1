$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
$localPropertiesPath = Join-Path $projectRoot "local.properties"

function Find-AndroidSdk {
    $candidates = @(
        $env:ANDROID_HOME,
        $env:ANDROID_SDK_ROOT,
        (Join-Path $env:LOCALAPPDATA "Android\Sdk"),
        (Join-Path $env:USERPROFILE "AppData\Local\Android\Sdk"),
        "C:\Android\Sdk"
    ) | Where-Object { $_ -and (Test-Path $_) }

    foreach ($candidate in $candidates) {
        $platformTools = Join-Path $candidate "platform-tools"
        if (Test-Path $platformTools) {
            return $candidate
        }
    }

    return $null
}

$sdkPath = Find-AndroidSdk

if (-not $sdkPath) {
    Write-Host "Android SDK not found." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Install Android Studio, then open it once and complete the SDK setup wizard."
    Write-Host "Default SDK path on Windows:"
    Write-Host "  $env:LOCALAPPDATA\Android\Sdk"
    Write-Host ""
    Write-Host "After installation, run this script again:"
    Write-Host "  .\scripts\setup-android.ps1"
    exit 1
}

$escapedSdkPath = $sdkPath -replace "\\", "\\\\"
$content = "sdk.dir=$escapedSdkPath`n"

Set-Content -Path $localPropertiesPath -Value $content -Encoding ASCII

Write-Host "Created local.properties" -ForegroundColor Green
Write-Host "sdk.dir=$sdkPath"
Write-Host ""
Write-Host "Build with:"
Write-Host "  .\gradlew.bat assembleDebug; if (`$?) { .\gradlew.bat installDebug }"
