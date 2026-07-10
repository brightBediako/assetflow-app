# AssetFlow App

Native Android app for shared organizational asset management.

## Prerequisites

1. Install [Android Studio](https://developer.android.com/studio)
2. Open Android Studio once and finish the SDK setup wizard
3. Create `local.properties` by running:

```powershell
.\scripts\setup-android.ps1
```

If the SDK is installed somewhere else, copy `local.properties.example` to `local.properties` and update `sdk.dir`.

## Build and install

PowerShell 5.x:

```powershell
.\gradlew.bat assembleDebug; if ($?) { .\gradlew.bat installDebug }
```

PowerShell 7+:

```powershell
./gradlew assembleDebug && ./gradlew installDebug
```
