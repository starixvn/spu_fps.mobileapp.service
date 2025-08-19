# Self-Hosted GitLab Configuration for FPS SDK
# GitLab Instance: https://gitlab.nexdev.net/

## Overview

This project is configured to work with the self-hosted GitLab instance at `https://gitlab.nexdev.net/`. The CI/CD pipeline and related tooling have been specifically configured for this environment.

## Initial Setup

### 1. Configure GitLab CLI

If you haven't already, install and configure the GitLab CLI for the self-hosted instance:

```bash
# Install GitLab CLI (if not installed)
# See: https://gitlab.com/gitlab-org/cli#installation

# Configure for self-hosted instance
glab config set gitlab_host gitlab.nexdev.net

# Authenticate with your self-hosted GitLab
glab auth login --hostname gitlab.nexdev.net
```

### 2. Verify Configuration

Check that your GitLab CLI is properly configured:

```bash
# Check current host configuration
glab config get gitlab_host

# Should output: gitlab.nexdev.net

# Test authentication
glab repo list
```

### 3. Run Pipeline Setup

Use the provided setup tools:

```bash
# Option 1: Use Makefile (recommended)
make gitlab-config  # Configure GitLab CLI
make setup         # Setup pipeline variables

# Option 2: Run scripts directly
./scripts/setup-pipeline.sh    # Unix/Linux
# or
.\scripts\setup-pipeline.ps1   # Windows PowerShell
```

## GitLab-Specific Configuration

### Container Images

The pipeline uses the following image strategy:

1. **Primary images**: Public Docker Hub images (cimg/android, cirrusci/flutter, node)
2. **GitLab-specific images**: Self-hosted registry when available
   - Release CLI: `https://gitlab.nexdev.net/gitlab-org/release-cli:latest`

### Registry Access

If your self-hosted GitLab uses a private container registry, you may need to:

1. **Configure registry authentication** in GitLab CI/CD variables:
   ```
   CI_REGISTRY_USER=<your-username>
   CI_REGISTRY_PASSWORD=<your-token>
   ```

2. **Update image references** in `.gitlab-ci.yml` if needed:
   ```yaml
   # Example: Use self-hosted registry
   image: gitlab.nexdev.net/your-group/android-build:latest
   ```

### API Access

The pipeline may require GitLab API access for certain operations. Configure:

```bash
# Set GitLab private token for API access
glab variable set GITLAB_PRIVATE_TOKEN <your-token>
```

## Environment Variables

### Required for Self-Hosted GitLab

| Variable | Description | Required |
|----------|-------------|----------|
| `GITLAB_PRIVATE_TOKEN` | GitLab API access token | Yes |
| `CI_REGISTRY_USER` | Container registry username | If using private registry |
| `CI_REGISTRY_PASSWORD` | Container registry password | If using private registry |

### Platform-Specific Variables

See the main [CI/CD Pipeline documentation](CI_CD_PIPELINE.md) for complete variable requirements.

## Troubleshooting

### Common Issues

1. **"Repository not found" errors**
   - Verify GitLab CLI is configured: `glab config get gitlab_host`
   - Check authentication: `glab auth status`

2. **Container image pull failures**
   - Check if using private registry requires authentication
   - Verify image exists in the specified registry

3. **Pipeline variables not accessible**
   - Ensure variables are set at project level
   - Check variable visibility (protected vs. public)

4. **Release creation fails**
   - Verify the release-cli image exists in your GitLab instance
   - Check if release feature is enabled

### Getting Help

1. **Check GitLab logs**: Go to CI/CD → Pipelines → [Pipeline] → Job logs
2. **Verify GitLab configuration**: Use `glab config list`
3. **Test connectivity**: Use `glab api projects` to test API access

## Migration Notes

If migrating from GitLab.com to self-hosted:

1. **Update remote URLs**:
   ```bash
   git remote set-url origin https://gitlab.nexdev.net/your-group/fps.mobileapp.service.git
   ```

2. **Reconfigure GitLab CLI**:
   ```bash
   glab config set gitlab_host gitlab.nexdev.net
   glab auth login --hostname gitlab.nexdev.net
   ```

3. **Migrate CI/CD variables**: Export from old instance and import to new
4. **Update webhook URLs**: If using external integrations
5. **Verify runner configuration**: Ensure runners are available

## Security Considerations

### Self-Hosted GitLab Security

1. **Use HTTPS**: Ensure all connections use `https://gitlab.nexdev.net`
2. **Token management**: Use project-specific tokens with minimal permissions
3. **Registry security**: If using private registry, use strong authentication
4. **Network access**: Ensure runners can access external dependencies (Docker Hub, npm, etc.)

### Best Practices

1. **Protected branches**: Configure `main` and `develop` as protected
2. **Pipeline triggers**: Limit pipeline execution to authorized users
3. **Variable masking**: Mask sensitive variables in CI/CD settings
4. **Runner isolation**: Use dedicated runners for production deployments

## Support

For issues specific to the self-hosted GitLab instance:

1. **GitLab administration**: Contact your GitLab admin team
2. **Pipeline issues**: Check this documentation and job logs
3. **CLI issues**: Refer to [GitLab CLI documentation](https://gitlab.com/gitlab-org/cli)

---

**Instance Information:**
- Host: `gitlab.nexdev.net`
- Protocol: `https`
- Project: `spu/fps.mobileapp.service`
