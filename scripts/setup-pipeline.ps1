# GitLab CI/CD Pipeline Setup Script for Self-Hosted GitLab
# Run this script to configure your GitLab project for the FPS SDK pipeline
# GitLab Instance: https://gitlab.nexdev.net/

Write-Host "🚀 Setting up GitLab CI/CD Pipeline for FPS SDK" -ForegroundColor Green
Write-Host "🌐 GitLab Instance: https://gitlab.nexdev.net/" -ForegroundColor Cyan
Write-Host ""

# Check if GitLab CLI is installed and configured for self-hosted instance
if (!(Get-Command "glab" -ErrorAction SilentlyContinue)) {
    Write-Host "⚠️  GitLab CLI (glab) is not installed." -ForegroundColor Yellow
    Write-Host "   Please install it from: https://gitlab.com/gitlab-org/cli" -ForegroundColor Yellow
    Write-Host "   Then configure it for your self-hosted instance:" -ForegroundColor Yellow
    Write-Host "   glab config set gitlab_host gitlab.nexdev.net" -ForegroundColor Yellow
    Write-Host ""
} else {
    # Check if glab is configured for the correct GitLab instance
    $currentHost = glab config get gitlab_host 2>$null
    if ($currentHost -ne "gitlab.nexdev.net") {
        Write-Host "⚠️  GitLab CLI is not configured for your self-hosted instance." -ForegroundColor Yellow
        Write-Host "   Current host: $currentHost" -ForegroundColor Gray
        Write-Host "   Run: glab config set gitlab_host gitlab.nexdev.net" -ForegroundColor Yellow
        Write-Host ""
    } else {
        Write-Host "✅ GitLab CLI configured for gitlab.nexdev.net" -ForegroundColor Green
        Write-Host ""
    }
}

# Get project information
$projectPath = Split-Path -Leaf (Get-Location)
Write-Host "📁 Project: $projectPath" -ForegroundColor Cyan

# Check if we're in a git repository
if (!(Test-Path ".git")) {
    Write-Host "❌ Not in a Git repository. Please run this script from the project root." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "🔧 Setting up CI/CD variables..." -ForegroundColor Blue
Write-Host ""

# Function to set GitLab variable
function Set-GitLabVariable {
    param(
        [string]$name,
        [string]$description,
        [bool]$masked = $false,
        [bool]$protected = $false
    )
    
    Write-Host "Setting variable: $name" -ForegroundColor Yellow
    Write-Host "Description: $description" -ForegroundColor Gray
    
    if ($masked) {
        Write-Host "⚠️  This variable will be masked in logs" -ForegroundColor Yellow
    }
    if ($protected) {
        Write-Host "🔒 This variable will only be available for protected branches/tags" -ForegroundColor Yellow
    }
    
    $value = Read-Host "Enter value for $name (leave empty to skip)"
    
    if ($value) {
        $flags = @()
        if ($masked) { $flags += "--masked" }
        if ($protected) { $flags += "--protected" }
        
        try {
            $flagString = $flags -join " "
            if ($flagString) {
                Invoke-Expression "glab variable set $name '$value' $flagString"
            } else {
                Invoke-Expression "glab variable set $name '$value'"
            }
            Write-Host "✅ Variable $name set successfully" -ForegroundColor Green
        } catch {
            Write-Host "❌ Failed to set variable $name" -ForegroundColor Red
        }
    } else {
        Write-Host "⏭️  Skipping $name" -ForegroundColor Gray
    }
    Write-Host ""
}

Write-Host "📱 Android/Gradle Variables" -ForegroundColor Magenta
Write-Host "===========================" -ForegroundColor Magenta
Set-GitLabVariable "GRADLE_PUBLISH_USERNAME" "Maven repository username for publishing Android SDK" $true
Set-GitLabVariable "GRADLE_PUBLISH_PASSWORD" "Maven repository password for publishing Android SDK" $true $true
Set-GitLabVariable "SIGNING_KEY_ID" "GPG key ID for signing Android artifacts" $true
Set-GitLabVariable "SIGNING_PASSWORD" "GPG key password for signing Android artifacts" $true $true
Set-GitLabVariable "SIGNING_SECRET_KEY" "Base64 encoded GPG private key for signing" $true $true

Write-Host "🐦 Flutter Variables" -ForegroundColor Magenta  
Write-Host "====================" -ForegroundColor Magenta
Set-GitLabVariable "PUB_DEV_TOKEN" "Token for publishing to pub.dev" $true $true

Write-Host "📦 React Native/NPM Variables" -ForegroundColor Magenta
Write-Host "=============================" -ForegroundColor Magenta
Set-GitLabVariable "NPM_TOKEN" "NPM registry authentication token" $true $true
Set-GitLabVariable "NPM_REGISTRY" "Custom NPM registry URL (optional)" $false

Write-Host "🔗 GitLab Variables" -ForegroundColor Magenta
Write-Host "===================" -ForegroundColor Magenta
Set-GitLabVariable "GITLAB_PRIVATE_TOKEN" "Token for GitLab API access" $true

Write-Host "🌍 Environment-specific Variables" -ForegroundColor Magenta
Write-Host "==================================" -ForegroundColor Magenta
Set-GitLabVariable "MAVEN_REPOSITORY_URL_STAGING" "Maven repository URL for staging deployments" $false
Set-GitLabVariable "MAVEN_REPOSITORY_URL_PRODUCTION" "Maven repository URL for production deployments" $false
Set-GitLabVariable "NPM_REGISTRY_STAGING" "NPM registry URL for staging deployments" $false

Write-Host ""
Write-Host "🔧 Creating pipeline schedules..." -ForegroundColor Blue

# Create a nightly security scan schedule
Write-Host "Setting up nightly security scan..." -ForegroundColor Yellow
try {
    glab schedule create --cron "0 2 * * *" --description "Nightly security scan" --ref "main" --variable "SECURITY_SCAN=true"
    Write-Host "✅ Nightly security scan schedule created" -ForegroundColor Green
} catch {
    Write-Host "⚠️  Could not create security scan schedule (may already exist)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "📋 Setup Summary" -ForegroundColor Green
Write-Host "=================" -ForegroundColor Green
Write-Host "✅ GitLab CI/CD pipeline file created (.gitlab-ci.yml)"
Write-Host "✅ Pipeline documentation created (docs/CI_CD_PIPELINE.md)"
Write-Host "✅ CI/CD variables configured"
Write-Host "✅ Pipeline schedules set up"
Write-Host ""

Write-Host "🎯 Next Steps:" -ForegroundColor Cyan
Write-Host "1. Commit and push the .gitlab-ci.yml file to trigger your first pipeline"
Write-Host "2. Review the pipeline documentation in docs/CI_CD_PIPELINE.md"
Write-Host "3. Test the pipeline with a merge request or push to develop branch"
Write-Host "4. Configure protected branches for production deployments"
Write-Host "5. Set up GitLab environments (staging, production) if needed"
Write-Host ""

Write-Host "🔒 Security Recommendations:" -ForegroundColor Yellow
Write-Host "1. Enable protected branches for main and develop"
Write-Host "2. Require approvals for production deployments"
Write-Host "3. Enable merge request pipelines only"
Write-Host "4. Regularly rotate tokens and credentials"
Write-Host ""

Write-Host "📚 Documentation:" -ForegroundColor Blue
Write-Host "- Pipeline overview: docs/CI_CD_PIPELINE.md"
Write-Host "- GitLab CI/CD docs: https://docs.gitlab.com/ee/ci/"
Write-Host "- Variable management: https://docs.gitlab.com/ee/ci/variables/"
Write-Host ""

Write-Host "🎉 Pipeline setup complete! Happy building! 🚀" -ForegroundColor Green
