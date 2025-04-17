import Fps from './NativeFps';

export function onResetFPSLastLiveTime(): void {
  Fps.onResetFPSLastLiveTime();
}

export function onCheckFPSTrans(
  subscriberName: string,
  userName: string,
  action: string,
  isOnStart: boolean
): string | null {
  return Fps.onCheckFPSTrans(
    subscriberName,
    userName,
    action,
    isOnStart
  );
}