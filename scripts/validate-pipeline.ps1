# Pipeline Validation Script
# This script validates the CI/CD pipeline configuration locally
# GitLab Instance: https://gitlab.nexdev.net/

Write-Host "🔍 Validating FPS SDK Pipeline Configuration" -ForegroundColor Green
Write-Host "🌐 GitLab Instance: https://gitlab.nexdev.net/" -ForegroundColor Cyan
Write-Host ""

$errors = @()
$warnings = @()

# Check GitLab CLI configuration for self-hosted instance
Write-Host "🔗 Checking GitLab CLI configuration..." -ForegroundColor Blue

if (Get-Command "glab" -ErrorAction SilentlyContinue) {
    try {
        $currentHost = glab config get gitlab_host 2>$null
        if ($currentHost -eq "gitlab.nexdev.net") {
            Write-Host "✅ GitLab CLI configured for gitlab.nexdev.net" -ForegroundColor Green
        } elseif ($currentHost) {
            Write-Host "⚠️  GitLab CLI configured for different host: $currentHost" -ForegroundColor Yellow
            $warnings += "GitLab CLI configured for $currentHost instead of gitlab.nexdev.net"
        } else {
            Write-Host "⚠️  GitLab CLI not configured for any host" -ForegroundColor Yellow
            $warnings += "GitLab CLI not configured for gitlab.nexdev.net"
        }
    } catch {
        Write-Host "⚠️  Cannot check GitLab CLI configuration" -ForegroundColor Yellow
        $warnings += "GitLab CLI configuration check failed"
    }
} else {
    Write-Host "⚠️  GitLab CLI not found" -ForegroundColor Yellow
    $warnings += "GitLab CLI not found - install from https://gitlab.com/gitlab-org/cli"
}

# Check if required files exist
Write-Host "📁 Checking required files..." -ForegroundColor Blue

$requiredFiles = @(
    ".gitlab-ci.yml",
    "src/Java_Kotlin/build.gradle.kts",
    "src/Java_Kotlin/gradlew",
    "src/Flutter/pubspec.yaml",
    "src/ReactNative/package.json"
)

foreach ($file in $requiredFiles) {
    if (Test-Path $file) {
        Write-Host "✅ $file" -ForegroundColor Green
    } else {
        Write-Host "❌ $file" -ForegroundColor Red
        $errors += "Missing required file: $file"
    }
}

Write-Host ""
Write-Host "🔧 Checking build tools..." -ForegroundColor Blue

# Check Java
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "✅ Java: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "⚠️  Java not found" -ForegroundColor Yellow
    $warnings += "Java not found - required for Android builds"
}

# Check Node.js
try {
    $nodeVersion = node --version 2>$null
    Write-Host "✅ Node.js: $nodeVersion" -ForegroundColor Green
} catch {
    Write-Host "⚠️  Node.js not found" -ForegroundColor Yellow
    $warnings += "Node.js not found - required for React Native builds"
}

# Check Flutter (if available)
try {
    $flutterVersion = flutter --version 2>$null | Select-Object -First 1
    Write-Host "✅ Flutter: $flutterVersion" -ForegroundColor Green
} catch {
    Write-Host "⚠️  Flutter not found" -ForegroundColor Yellow
    $warnings += "Flutter not found - required for Flutter builds"
}

Write-Host ""
Write-Host "📋 Validating pipeline configuration..." -ForegroundColor Blue

# Check .gitlab-ci.yml syntax
if (Test-Path ".gitlab-ci.yml") {
    try {
        # Basic YAML syntax check
        $yamlContent = Get-Content ".gitlab-ci.yml" -Raw
        if ($yamlContent -match "stages:" -and $yamlContent -match "image:") {
            Write-Host "✅ .gitlab-ci.yml syntax appears valid" -ForegroundColor Green
        } else {
            Write-Host "⚠️  .gitlab-ci.yml may have syntax issues" -ForegroundColor Yellow
            $warnings += ".gitlab-ci.yml syntax validation failed"
        }
    } catch {
        Write-Host "❌ Error reading .gitlab-ci.yml" -ForegroundColor Red
        $errors += "Cannot read .gitlab-ci.yml file"
    }
}

# Check Android configuration
Write-Host ""
Write-Host "📱 Validating Android configuration..." -ForegroundColor Blue

if (Test-Path "src/Java_Kotlin/gradle.properties") {
    $gradleProps = Get-Content "src/Java_Kotlin/gradle.properties"
    if ($gradleProps -match "VERSION_NAME") {
        Write-Host "✅ VERSION_NAME found in gradle.properties" -ForegroundColor Green
    } else {
        Write-Host "⚠️  VERSION_NAME not found in gradle.properties" -ForegroundColor Yellow
        $warnings += "VERSION_NAME not set in gradle.properties"
    }
} else {
    Write-Host "❌ gradle.properties not found" -ForegroundColor Red
    $errors += "gradle.properties file missing"
}

# Check Flutter configuration
Write-Host ""
Write-Host "🐦 Validating Flutter configuration..." -ForegroundColor Blue

if (Test-Path "src/Flutter/pubspec.yaml") {
    $pubspec = Get-Content "src/Flutter/pubspec.yaml"
    if ($pubspec -match "version:") {
        Write-Host "✅ Version found in pubspec.yaml" -ForegroundColor Green
    } else {
        Write-Host "⚠️  Version not found in pubspec.yaml" -ForegroundColor Yellow
        $warnings += "Version not set in pubspec.yaml"
    }
    
    if ($pubspec -match "flutter:") {
        Write-Host "✅ Flutter plugin configuration found" -ForegroundColor Green
    } else {
        Write-Host "❌ Flutter plugin configuration missing" -ForegroundColor Red
        $errors += "Flutter plugin configuration missing in pubspec.yaml"
    }
} else {
    Write-Host "❌ pubspec.yaml not found" -ForegroundColor Red
    $errors += "pubspec.yaml file missing"
}

# Check React Native configuration
Write-Host ""
Write-Host "📦 Validating React Native configuration..." -ForegroundColor Blue

if (Test-Path "src/ReactNative/package.json") {
    try {
        $packageJson = Get-Content "src/ReactNative/package.json" | ConvertFrom-Json
        if ($packageJson.version) {
            Write-Host "✅ Version found in package.json: $($packageJson.version)" -ForegroundColor Green
        } else {
            Write-Host "⚠️  Version not found in package.json" -ForegroundColor Yellow
            $warnings += "Version not set in package.json"
        }
        
        if ($packageJson.scripts.prepare) {
            Write-Host "✅ Prepare script found in package.json" -ForegroundColor Green
        } else {
            Write-Host "⚠️  Prepare script not found in package.json" -ForegroundColor Yellow
            $warnings += "Prepare script missing in package.json"
        }
    } catch {
        Write-Host "❌ Invalid JSON in package.json" -ForegroundColor Red
        $errors += "package.json contains invalid JSON"
    }
} else {
    Write-Host "❌ package.json not found" -ForegroundColor Red
    $errors += "package.json file missing"
}

# Check for GitLab specific files
Write-Host ""
Write-Host "🔗 Checking GitLab integration..." -ForegroundColor Blue

if (Test-Path ".git") {
    $gitRemote = git remote get-url origin 2>$null
    if ($gitRemote -match "gitlab.nexdev.net") {
        Write-Host "✅ Self-hosted GitLab repository detected (gitlab.nexdev.net)" -ForegroundColor Green
    } elseif ($gitRemote -match "gitlab") {
        Write-Host "⚠️  GitLab repository detected but not self-hosted instance" -ForegroundColor Yellow
        Write-Host "   Remote: $gitRemote" -ForegroundColor Gray
        $warnings += "Repository is not on gitlab.nexdev.net"
    } else {
        Write-Host "⚠️  Not a GitLab repository" -ForegroundColor Yellow
        $warnings += "Repository is not hosted on GitLab"
    }
} else {
    Write-Host "❌ Not a Git repository" -ForegroundColor Red
    $errors += "Not a Git repository"
}

# Summary
Write-Host ""
Write-Host "📊 Validation Summary" -ForegroundColor Magenta
Write-Host "=====================" -ForegroundColor Magenta

if ($errors.Count -eq 0 -and $warnings.Count -eq 0) {
    Write-Host "🎉 All validations passed! Your pipeline is ready to go!" -ForegroundColor Green
} else {
    if ($errors.Count -gt 0) {
        Write-Host ""
        Write-Host "❌ Errors found ($($errors.Count)):" -ForegroundColor Red
        foreach ($error in $errors) {
            Write-Host "   • $error" -ForegroundColor Red
        }
    }
    
    if ($warnings.Count -gt 0) {
        Write-Host ""
        Write-Host "⚠️  Warnings ($($warnings.Count)):" -ForegroundColor Yellow
        foreach ($warning in $warnings) {
            Write-Host "   • $warning" -ForegroundColor Yellow
        }
    }
}

Write-Host ""
Write-Host "💡 Next steps:" -ForegroundColor Cyan
Write-Host "1. Run 'make gitlab-config' to configure GitLab CLI for gitlab.nexdev.net"
Write-Host "2. Fix any errors listed above"
Write-Host "3. Run 'make setup' to configure GitLab variables"
Write-Host "4. Commit and push .gitlab-ci.yml to trigger your first pipeline"
Write-Host "5. Monitor the pipeline execution in GitLab CI/CD at gitlab.nexdev.net"
Write-Host ""

# Exit with error code if there are critical errors
if ($errors.Count -gt 0) {
    exit 1
} else {
    exit 0
}
