# FPS SDK CI/CD Pipeline

This repository contains a comprehensive GitLab CI/CD pipeline for the FPS (Fraud Protection System) multi-platform SDK.

**GitLab Instance**: https://gitlab.nexdev.net/

## Pipeline Overview

The pipeline supports three platforms:

-   **Android (Kotlin/Java)** - Native Android SDK
-   **Flutter** - Cross-platform Flutter plugin
-   **React Native** - React Native library

## Self-Hosted GitLab Configuration

This pipeline is designed for the self-hosted GitLab instance at `gitlab.nexdev.net`. Key considerations:

### GitLab CLI Configuration
Before using the setup scripts, configure the GitLab CLI for your self-hosted instance:

```bash
# Configure GitLab CLI for self-hosted instance
glab config set gitlab_host gitlab.nexdev.net

# Authenticate with your self-hosted GitLab
glab auth login --hostname gitlab.nexdev.net
```

### Container Registry
The pipeline uses the self-hosted container registry for custom images:
- Release CLI: `https://gitlab.nexdev.net/gitlab-org/release-cli:latest`
- Base images fallback to public registries when needed

### Repository URLs
All repository references are automatically configured for `gitlab.nexdev.net` domain.

## Pipeline Stages

### 1. Setup

-   Configures build environments for each platform
-   Downloads dependencies and caches them
-   Validates build tools and versions

### 2. Lint

-   **Android**: Runs ktlint and detekt for code quality
-   **Flutter**: Runs dart analyze and format checks
-   **React Native**: Runs ESLint and TypeScript checks

### 3. Test

-   **Android**: Executes unit tests with JUnit
-   **Flutter**: Runs Flutter test suite
-   **React Native**: Executes Jest tests

### 4. Build

-   **Android**: Builds release and debug AAR files
-   **Flutter**: Builds Android Archive (AAR) for plugin
-   **React Native**: Compiles TypeScript and prepares package

### 5. Package

-   Creates distributable packages for each platform
-   Generates artifacts for deployment
-   Prepares packages for publishing

### 6. Deploy

-   **Staging**: Automatic deployment to staging on `develop` branch
-   **Production**: Manual deployment triggered by Git tags

## Configuration

### Required GitLab CI/CD Variables

Set these in your GitLab project settings under Settings → CI/CD → Variables:

#### Android/Gradle

```
GRADLE_PUBLISH_USERNAME    # Maven repository username
GRADLE_PUBLISH_PASSWORD    # Maven repository password
SIGNING_KEY_ID             # GPG key ID for signing
SIGNING_PASSWORD           # GPG key password
SIGNING_SECRET_KEY         # Base64 encoded GPG private key
```

#### Flutter

```
PUB_DEV_TOKEN             # Token for pub.dev publishing
```

#### React Native/NPM

```
NPM_TOKEN                 # NPM registry authentication token
NPM_REGISTRY              # Custom NPM registry URL (optional)
```

#### General

```
GITLAB_PRIVATE_TOKEN      # Token for GitLab API access
```

### Environment-specific Variables

You can override variables for different environments:

**Staging Environment:**

-   `MAVEN_REPOSITORY_URL_STAGING`
-   `NPM_REGISTRY_STAGING`

**Production Environment:**

-   `MAVEN_REPOSITORY_URL_PRODUCTION`
-   `NPM_REGISTRY_PRODUCTION`

## Triggering Builds

### Automatic Triggers

-   **All branches**: Runs setup, lint, and test stages
-   **develop branch**: Runs full pipeline including staging deployment
-   **main branch**: Runs full pipeline excluding deployment
-   **merge requests**: Runs full pipeline excluding deployment

### Manual Triggers

-   **Production deployment**: Manual deployment for tagged releases
-   **Security scan**: On-demand security vulnerability scanning
-   **Cache cleanup**: Manual cache clearing

### Release Process

1. Create a new tag following semantic versioning:

    ```bash
    git tag -a v1.0.6 -m "Release version 1.0.6"
    git push origin v1.0.6
    ```

2. The pipeline will automatically:
    - Build all platforms
    - Create release artifacts
    - Create a GitLab release
    - Enable manual production deployment

## Artifacts

The pipeline generates the following artifacts:

### Android

-   `*.aar` files (release and debug)
-   Maven publications
-   Test reports

### Flutter

-   Plugin AAR files
-   Pub package
-   Analysis reports

### React Native

-   NPM package (`.tgz`)
-   TypeScript declarations
-   Build outputs

## Caching Strategy

The pipeline uses GitLab CI cache to speed up builds:

-   **Gradle**: `.gradle/wrapper` and `.gradle/caches`
-   **Flutter**: `.pub-cache/`
-   **Node.js**: `.npm/` and `node_modules/`

Cache keys are branch-specific to avoid conflicts.

## Monitoring and Debugging

### Pipeline Monitoring

-   Check pipeline status in GitLab CI/CD → Pipelines
-   View detailed logs for each job
-   Monitor artifact generation

### Common Issues

1. **Build failures**: Check dependency versions and platform requirements
2. **Test failures**: Review test logs and coverage reports
3. **Publishing failures**: Verify credentials and repository access
4. **Cache issues**: Use manual cache cleanup job

### Debug Mode

Enable debug logging by setting:

```yaml
variables:
    CI_DEBUG_TRACE: 'true'
```

## Security

The pipeline includes security best practices:

-   Dependency vulnerability scanning
-   Code quality checks
-   Secure credential handling
-   Artifact signing and verification

## Customization

### Adding New Platforms

1. Add setup, lint, test, build jobs for the new platform
2. Update package and deploy stages
3. Add platform-specific variables and caching

### Modifying Build Steps

1. Edit the relevant job in `.gitlab-ci.yml`
2. Test changes in a feature branch
3. Update documentation

### Custom Deployment Targets

1. Add new environment configurations
2. Create platform-specific deployment jobs
3. Configure access credentials

## Support

For pipeline issues:

1. Check the GitLab CI/CD documentation
2. Review job logs for specific errors
3. Verify environment variables and credentials
4. Contact the development team for assistance
