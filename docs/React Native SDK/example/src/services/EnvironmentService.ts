/**
 * Environment options for the application
 */
export enum Environment {
  local = 'local',
  uat = 'uat',
  prod = 'prod',
  dev = 'dev'
}

/**
 * Service for managing environment settings
 */
export class EnvironmentService {
  /**
   * Gets the hostname for the selected environment
   * @param environment The environment to get the hostname for
   * @returns The hostname for the selected environment
   */
  public static getHostname(environment: Environment): string {
    switch (environment) {
      case Environment.local:
        return 'https://fpt-local.nexdev.net';
      case Environment.uat:
        return 'https://fpt-uat.starixuat.com:6012';
      case Environment.prod:
        return 'https://api.detecas.com';
      case Environment.dev:
        return 'http://10.0.2.2:5010';
    }
  }

  /**
   * Gets all available environments with their display names
   * @returns Array of environment options for dropdown
   */
  public static getEnvironmentOptions(): Array<{ label: string; value: Environment }> {
    return [
      { label: 'Local', value: Environment.local },
      { label: 'UAT', value: Environment.uat },
      { label: 'Production', value: Environment.prod },
      { label: 'Development', value: Environment.dev }
    ];
  }
}

// Default to local environment
export const DEFAULT_ENVIRONMENT = Environment.local;