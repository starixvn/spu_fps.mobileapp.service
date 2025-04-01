import { onCheckFPSTrans } from 'fps';
import { Environment, EnvironmentService, DEFAULT_ENVIRONMENT } from './EnvironmentService';

/**
 * Service class for handling FPS (Fraud Prevention System) functionality
 */
export class FPSService {
  private environment: Environment = DEFAULT_ENVIRONMENT;
  
  /**
   * Sets the current environment for API calls
   * @param environment The environment to use
   */
  public setEnvironment(environment: Environment): void {
    this.environment = environment;
  }
  
  /**
   * Gets the current environment
   * @returns The current environment
   */
  public getEnvironment(): Environment {
    return this.environment;
  }
  
  /**
   * Gets the current API endpoint based on the selected environment
   * @returns The API endpoint URL
   */
  private getApiEndpoint(): string {
    return EnvironmentService.getHostname(this.environment);
  }
  
  /**
   * Processes an FPS transaction for the specified user during login
   * 
   * @param username - The username attempting to login
   * @param subscriberName - The subscriber name
   * @returns A promise resolving to a boolean indicating success or failure
   */
  public async processLoginTransaction(username: string, subscriberName: string): Promise<boolean> {
    try {
      // Call FPS library to get transaction data
      const fpsTransactionData = this.generateTransactionData(
        subscriberName,
        username,
        'login'
      );
      
      if (!fpsTransactionData) {
        console.log('No FPS transaction data was generated');
        return true; // Continue with login even if FPS doesn't return data
      }
      
      // Send the transaction data to the server
      return await this.sendTransactionData(fpsTransactionData);
    } catch (error) {
      console.error('FPS transaction error:', error);
      return false;
    }
  }

  /**
   * Generates FPS transaction data using the native FPS library
   * 
   * @param subscriberName - The subscriber name
   * @param username - The username
   * @param action - The action being performed (e.g., 'login')
   * @returns The generated FPS transaction data string or null
   */
  private generateTransactionData(
    subscriberName: string, 
    username: string, 
    action: string
  ): string | null {
    return onCheckFPSTrans(
      subscriberName,
      username,
      action,
      false // isOnStart is false for login actions
    );
  }

  /**
   * Sends the FPS transaction data to the specified API endpoint
   * 
   * @param transactionData - The FPS transaction data to send
   * @returns A promise resolving to a boolean indicating success or failure
   */
  private async sendTransactionData(transactionData: string): Promise<boolean> {
    try {
      const apiEndpoint = this.getApiEndpoint() + "/FPS/MobileCheckTransaction";
      console.log(`Sending FPS transaction to: ${apiEndpoint}`);
      
      const response = await fetch(apiEndpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ transaction: transactionData }),
      });
      
      // Display HTTP status code
      console.log(`FPS API response status: ${response.status} (${response.statusText})`);
      
      if (!response.ok) {
        console.error(`FPS transaction API error: ${response.status} - ${response.statusText}`);
        return false;
      }
      
      return true;
    } catch (error) {
      console.error('Error sending FPS transaction data:', error);
      return false;
    }
  }
}

// Create a singleton instance of the service
export const fpsService = new FPSService();

// Default export for convenience
export default fpsService;