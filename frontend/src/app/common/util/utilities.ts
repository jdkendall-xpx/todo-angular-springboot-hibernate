// Replace first item in an array
export function replaceFirst<T>(current: T[], newObj: T, compare: (e: T, n: T) => boolean): T[] {
  const indexToUpdate = current.findIndex(obj => compare(newObj, obj));
  const updated = [...current];
  updated[indexToUpdate] = newObj;
  return updated;
}

// Allows zipping of two arrays together with proper TypeScript type inference
type Zip<A extends ReadonlyArray<any>> = {
  [K in keyof A]: A[K] extends ReadonlyArray<infer T> ? T : never
};

export function zip<Arrays extends ReadonlyArray<any>[]>(...arrays: Arrays): Array<Zip<Arrays>> {
  const len = Math.min(...arrays.map(a => a.length));
  const zipped: Zip<Arrays>[] = new Array(len);

  for (let i = 0; i < len; i++) {
    zipped[i] = arrays.map(a => a[i]) as Zip<Arrays>;
  }

  return zipped;
}
