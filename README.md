# pagination-pageable-sample

A minimal demonstration of offset-based pagination in Java.

## What It Demonstrates

Pagination splits a large dataset into fixed-size chunks. Instead of returning all records at once, the repository returns a single page — a slice of the full result with metadata: current page number, page size, and total record count.

## Structure

All code is in a single file: `PaginationPageableApplication.java`

```
PaginationPageableApplication.java
│
├── Owner                   # Entity — record with id and name
├── Page<T>                 # Pagination result — content + metadata (page, size, total)
├── Pageable                # Pagination request — page number and size
├── FakeOwnerRepository     # In-memory implementation — applies LIMIT and OFFSET logic
└── PaginationPageableApplication  # Entry point + demo()
```

## Key Points

**Pageable describes the request.** Page number and size are the only inputs — the repository computes the offset internally.

**Page wraps the result.** The caller always knows the total number of records and can compute total pages without an extra call.

**Simulates LIMIT / OFFSET.** `FakeOwnerRepository` applies the same slicing logic a real database query with `LIMIT` and `OFFSET` would do.

## Console Output

```
Page[content=[Owner[id=1, name=jack1], Owner[id=2, name=jack2]], page=0, size=2, total=5]
Page[content=[Owner[id=3, name=jack3], Owner[id=4, name=jack4]], page=1, size=2, total=5]
Page[content=[Owner[id=5, name=jack5]], page=2, size=2, total=5]
```

Three pages from five records. The last page contains only one record — the remainder after even division.

## Run

```bash
./mvnw spring-boot:run
```

## See also

- Previous: [in-memory-repository-sorting-sample](https://github.com/core-java-samples/in-memory-repository-sorting-sample)
- Next: [pagination-dto-mapping-sample](https://github.com/core-java-samples/pagination-dto-mapping-sample)
