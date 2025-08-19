#!/bin/bash

# GitLab CI/CD Pipeline Setup Script for Self-Hosted GitLab
# Run this script to configure your GitLab project for the FPS SDK pipeline
# GitLab Instance: https://gitlab.nexdev.net/

set -e

echo "🚀 Setting up GitLab CI/CD Pipeline for FPS SDK"
echo "🌐 GitLab Instance: https://gitlab.nexdev.net/"
echo ""

# Check if GitLab CLI is installed and configured for self-hosted instance
if ! command -v glab &> /dev/null; then
    echo "⚠️  GitLab CLI (glab) is not installed."
    echo "   Please install it from: https://gitlab.com/gitlab-org/cli"
    echo "   Then configure it for your self-hosted instance:"
    echo "   glab config set gitlab_host gitlab.nexdev.net"
    echo ""
    exit 1
else
    # Check if glab is configured for the correct GitLab instance
    current_host=$(glab config get gitlab_host 2>/dev/null || echo "")
    if [ "$current_host" != "gitlab.nexdev.net" ]; then
        echo "⚠️  GitLab CLI is not configured for your self-hosted instance."
        echo "   Current host: $current_host"
        echo "   Run: glab config set gitlab_host gitlab.nexdev.net"
        echo ""
    else
        echo "✅ GitLab CLI configured for gitlab.nexdev.net"
        echo ""
    fi
fi

# Get project information
PROJECT_PATH=$(basename "$PWD")
echo "📁 Project: $PROJECT_PATH"

# Check if we're in a git repository
if [ ! -d ".git" ]; then
    echo "❌ Not in a Git repository. Please run this script from the project root."
    exit 1
fi

echo ""
echo "🔧 Setting up CI/CD variables..."
echo ""

# Function to set GitLab variable
set_gitlab_variable() {
    local name=$1
    local description=$2
    local masked=${3:-false}
    local protected=${4:-false}
    
    echo "Setting variable: $name"
    echo "Description: $description"
    
    if [ "$masked" = true ]; then
        echo "⚠️  This variable will be masked in logs"
    fi
    if [ "$protected" = true ]; then
        echo "🔒 This variable will only be available for protected branches/tags"
    fi
    
    read -p "Enter value for $name (leave empty to skip): " value
    
    if [ -n "$value" ]; then
        local flags=""
        if [ "$masked" = true ]; then
            flags="$flags --masked"
        fi
        if [ "$protected" = true ]; then
            flags="$flags --protected"
        fi
        
        if glab variable set "$name" "$value" $flags; then
            echo "✅ Variable $name set successfully"
        else
            echo "❌ Failed to set variable $name"
        fi
    else
        echo "⏭️  Skipping $name"
    fi
    echo ""
}

echo "📱 Android/Gradle Variables"
echo "==========================="
set_gitlab_variable "GRADLE_PUBLISH_USERNAME" "Maven repository username for publishing Android SDK" true
set_gitlab_variable "GRADLE_PUBLISH_PASSWORD" "Maven repository password for publishing Android SDK" true true
set_gitlab_variable "SIGNING_KEY_ID" "GPG key ID for signing Android artifacts" true
set_gitlab_variable "SIGNING_PASSWORD" "GPG key password for signing Android artifacts" true true
set_gitlab_variable "SIGNING_SECRET_KEY" "Base64 encoded GPG private key for signing" true true

echo "🐦 Flutter Variables"
echo "===================="
set_gitlab_variable "PUB_DEV_TOKEN" "Token for publishing to pub.dev" true true

echo "📦 React Native/NPM Variables"
echo "============================="
set_gitlab_variable "NPM_TOKEN" "NPM registry authentication token" true true
set_gitlab_variable "NPM_REGISTRY" "Custom NPM registry URL (optional)" false

echo "🔗 GitLab Variables"
echo "==================="
set_gitlab_variable "GITLAB_PRIVATE_TOKEN" "Token for GitLab API access" true

echo "🌍 Environment-specific Variables"
echo "=================================="
set_gitlab_variable "MAVEN_REPOSITORY_URL_STAGING" "Maven repository URL for staging deployments" false
set_gitlab_variable "MAVEN_REPOSITORY_URL_PRODUCTION" "Maven repository URL for production deployments" false
set_gitlab_variable "NPM_REGISTRY_STAGING" "NPM registry URL for staging deployments" false

echo ""
echo "🔧 Creating pipeline schedules..."

# Create a nightly security scan schedule
echo "Setting up nightly security scan..."
if glab schedule create --cron "0 2 * * *" --description "Nightly security scan" --ref "main" --variable "SECURITY_SCAN=true"; then
    echo "✅ Nightly security scan schedule created"
else
    echo "⚠️  Could not create security scan schedule (may already exist)"
fi

echo ""
echo "📋 Setup Summary"
echo "================="
echo "✅ GitLab CI/CD pipeline file created (.gitlab-ci.yml)"
echo "✅ Pipeline documentation created (docs/CI_CD_PIPELINE.md)"
echo "✅ CI/CD variables configured"
echo "✅ Pipeline schedules set up"
echo ""

echo "🎯 Next Steps:"
echo "1. Commit and push the .gitlab-ci.yml file to trigger your first pipeline"
echo "2. Review the pipeline documentation in docs/CI_CD_PIPELINE.md"
echo "3. Test the pipeline with a merge request or push to develop branch"
echo "4. Configure protected branches for production deployments"
echo "5. Set up GitLab environments (staging, production) if needed"
echo ""

echo "🔒 Security Recommendations:"
echo "1. Enable protected branches for main and develop"
echo "2. Require approvals for production deployments"
echo "3. Enable merge request pipelines only"
echo "4. Regularly rotate tokens and credentials"
echo ""

echo "📚 Documentation:"
echo "- Pipeline overview: docs/CI_CD_PIPELINE.md"
echo "- GitLab CI/CD docs: https://docs.gitlab.com/ee/ci/"
echo "- Variable management: https://docs.gitlab.com/ee/ci/variables/"
echo ""

echo "🎉 Pipeline setup complete! Happy building! 🚀"
