import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  onResetFPSLastLiveTime(): void;
  onCheckFPSTrans(
    subscriberName: string,
    userName: string,
    action: string,
    isOnStart: boolean
  ): string | null;
}

export default TurboModuleRegistry.getEnforcing<Spec>('Fps');
