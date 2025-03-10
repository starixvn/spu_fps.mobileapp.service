import FpsSdk from './NativeFpsSdk';

export function multiply(a: number, b: number): number {
  return FpsSdk.multiply(a, b);
}
