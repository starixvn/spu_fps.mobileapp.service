import Fps from './NativeFps';

export function multiply(a: number, b: number): number {
  return Fps.multiply(a, b);
}

export function onCheckFPSTrans(
  subscriberName: string,
  userName: string,
  action: string,
  isOnStart: boolean
): string | undefined {
  return Fps.onCheckFPSTrans(subscriberName, userName, action, isOnStart);
}

export function onResetFPSLastLiveTime(): void {
  return Fps.onResetFPSLastLiveTime();
}