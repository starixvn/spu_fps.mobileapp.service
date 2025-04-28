import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-fps' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const Fps = NativeModules.Fps
  ? NativeModules.Fps
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

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
