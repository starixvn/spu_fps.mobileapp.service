# Makefile for FPS SDK CI/CD Pipeline
# Provides common operations for managing the pipeline
# GitLab Instance: https://gitlab.nexdev.net/

.PHONY: help setup validate clean build test lint package deploy-staging gitlab-config

# Default target
help:
	@echo "🚀 FPS SDK Pipeline Commands"
	@echo "============================"
	@echo "🌐 GitLab Instance: https://gitlab.nexdev.net/"
	@echo ""
	@echo "Setup:"
	@echo "  make gitlab-config   - Configure GitLab CLI for self-hosted instance"
	@echo "  make setup           - Configure GitLab CI/CD variables"
	@echo "  make validate        - Validate pipeline configuration"
	@echo ""
	@echo "Development:"
	@echo "  make clean           - Clean all build artifacts"
	@echo "  make lint            - Run linting for all platforms"
	@echo "  make test            - Run tests for all platforms"
	@echo "  make build           - Build all platforms locally"
	@echo "  make package         - Package all platforms"
	@echo ""
	@echo "Deployment:"
	@echo "  make deploy-staging  - Deploy to staging environment"
	@echo "  make release         - Create a new release tag"
	@echo ""
	@echo "Utilities:"
	@echo "  make docker-build    - Build pipeline in Docker (for testing)"
	@echo "  make check-deps      - Check build dependencies"

# Configure GitLab CLI for self-hosted instance
gitlab-config:
	@echo "🔧 Configuring GitLab CLI for self-hosted instance..."
	@if command -v glab > /dev/null 2>&1; then \
		echo "Setting GitLab host to gitlab.nexdev.net..."; \
		glab config set gitlab_host gitlab.nexdev.net; \
		echo "✅ GitLab CLI configured for gitlab.nexdev.net"; \
		echo "💡 Run 'glab auth login --hostname gitlab.nexdev.net' to authenticate"; \
	else \
		echo "❌ GitLab CLI not found. Install from: https://gitlab.com/gitlab-org/cli"; \
		exit 1; \
	fi

# Setup pipeline
setup: gitlab-config
	@echo "🔧 Setting up GitLab CI/CD pipeline..."
	@if command -v pwsh > /dev/null 2>&1; then \
		pwsh -ExecutionPolicy Bypass -File scripts/setup-pipeline.ps1; \
	elif [ -f scripts/setup-pipeline.sh ]; then \
		bash scripts/setup-pipeline.sh; \
	else \
		echo "❌ No setup script found"; \
		exit 1; \
	fi

# Validate pipeline configuration
validate:
	@echo "🔍 Validating pipeline configuration..."
	@if command -v pwsh > /dev/null 2>&1; then \
		pwsh -ExecutionPolicy Bypass -File scripts/validate-pipeline.ps1; \
	else \
		echo "⚠️  PowerShell not available, skipping validation"; \
	fi

# Clean all build artifacts
clean:
	@echo "🧹 Cleaning build artifacts..."
	@echo "Cleaning Android..."
	@cd src/Java_Kotlin && ./gradlew clean || echo "Android clean failed"
	@echo "Cleaning Flutter..."
	@cd src/Flutter && flutter clean || echo "Flutter clean failed"
	@echo "Cleaning React Native..."
	@cd src/ReactNative && npm run clean || echo "React Native clean failed"
	@echo "✅ Clean complete"

# Check build dependencies
check-deps:
	@echo "🔍 Checking build dependencies..."
	@echo "Java:"
	@java -version 2>&1 || echo "❌ Java not found"
	@echo ""
	@echo "Node.js:"
	@node --version 2>&1 || echo "❌ Node.js not found"
	@echo ""
	@echo "Flutter:"
	@flutter --version 2>&1 | head -1 || echo "❌ Flutter not found"
	@echo ""
	@echo "Git:"
	@git --version 2>&1 || echo "❌ Git not found"

# Lint all platforms
lint:
	@echo "🔍 Running linting for all platforms..."
	@echo "Linting Android..."
	@cd src/Java_Kotlin && ./gradlew ktlintCheck detekt || echo "Android lint failed"
	@echo "Linting Flutter..."
	@cd src/Flutter && flutter analyze && dart format --output=none --set-exit-if-changed . || echo "Flutter lint failed"
	@echo "Linting React Native..."
	@cd src/ReactNative && npm run lint && npm run typecheck || echo "React Native lint failed"
	@echo "✅ Linting complete"

# Test all platforms
test:
	@echo "🧪 Running tests for all platforms..."
	@echo "Testing Android..."
	@cd src/Java_Kotlin && ./gradlew test || echo "Android tests failed"
	@echo "Testing Flutter..."
	@cd src/Flutter && flutter test || echo "Flutter tests failed"
	@echo "Testing React Native..."
	@cd src/ReactNative && npm test || echo "React Native tests failed"
	@echo "✅ Testing complete"

# Build all platforms
build:
	@echo "🔨 Building all platforms..."
	@echo "Building Android..."
	@cd src/Java_Kotlin && ./gradlew assembleRelease || echo "Android build failed"
	@echo "Building Flutter..."
	@cd src/Flutter && flutter packages get && flutter build aar --no-debug --no-profile || echo "Flutter build failed"
	@echo "Building React Native..."
	@cd src/ReactNative && npm ci && npm run prepare || echo "React Native build failed"
	@echo "✅ Build complete"

# Package all platforms
package: build
	@echo "📦 Packaging all platforms..."
	@echo "Packaging Android..."
	@cd src/Java_Kotlin && ./gradlew publishToMavenLocal || echo "Android packaging failed"
	@echo "Packaging Flutter..."
	@cd src/Flutter && flutter pub publish --dry-run || echo "Flutter packaging failed"
	@echo "Packaging React Native..."
	@cd src/ReactNative && npm pack || echo "React Native packaging failed"
	@echo "✅ Packaging complete"

# Deploy to staging (requires GitLab CLI)
deploy-staging:
	@echo "🚀 Deploying to staging..."
	@if command -v glab > /dev/null 2>&1; then \
		glab ci run --ref develop || echo "Staging deployment failed"; \
	else \
		echo "❌ GitLab CLI not found. Install from: https://gitlab.com/gitlab-org/cli"; \
	fi

# Create a new release
release:
	@echo "🏷️  Creating new release..."
	@read -p "Enter version (e.g., 1.0.6): " version; \
	if [ -n "$$version" ]; then \
		echo "Creating tag v$$version..."; \
		git tag -a "v$$version" -m "Release version $$version"; \
		git push origin "v$$version"; \
		echo "✅ Release v$$version created"; \
	else \
		echo "❌ Version required"; \
	fi

# Build pipeline in Docker for testing
docker-build:
	@echo "🐳 Building pipeline in Docker..."
	@docker run --rm -v $(PWD):/workspace -w /workspace \
		cimg/android:2024.01 \
		bash -c "cd src/Java_Kotlin && ./gradlew assembleRelease"

# Install development dependencies
install-deps:
	@echo "📥 Installing development dependencies..."
	@echo "Installing Android dependencies..."
	@cd src/Java_Kotlin && ./gradlew dependencies || echo "Android deps failed"
	@echo "Installing Flutter dependencies..."
	@cd src/Flutter && flutter pub get || echo "Flutter deps failed"
	@echo "Installing React Native dependencies..."
	@cd src/ReactNative && npm ci || echo "React Native deps failed"
	@echo "✅ Dependencies installed"

# Quick pipeline test (without deployment)
quick-test: lint test
	@echo "✅ Quick pipeline test complete"

# Full local pipeline simulation
full-test: clean install-deps lint test build package
	@echo "✅ Full pipeline test complete"
