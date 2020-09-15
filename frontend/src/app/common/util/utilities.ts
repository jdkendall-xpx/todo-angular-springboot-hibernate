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
