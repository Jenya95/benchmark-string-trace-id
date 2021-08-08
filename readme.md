All 5 methods do same thing: add traceId to the start of new line, like
```json
{
  "requestId" : 72626
}
``` 
should be converted to
```text
123 {
123   "requestId" : 72626
123 }
```

See on results done on local laptop:

```bash
Benchmark                   Mode  Cnt    Score    Error   Units
MyBenchmark.joining        thrpt    5  201.254 ± 81.341  ops/ms
MyBenchmark.replaceAll     thrpt    5   50.199 ±  6.268  ops/ms
MyBenchmark.stringBuilder  thrpt    5   79.208 ±  2.017  ops/ms
MyBenchmark.stringConcat   thrpt    5    2.178 ±  1.159  ops/ms
MyBenchmark.stringPlus     thrpt    5    2.385 ±  0.218  ops/ms
```