import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  onCheckFPSTrans(
    subscriberName: string,
    userName: string,
    action: string,
    isOnStart: Boolean
  ): string | undefined;
  onResetFPSLastLiveTime() : void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('Fps');
