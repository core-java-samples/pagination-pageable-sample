# Pagination Pattern — Sample

A minimal demonstration of pagination abstraction in Java + Spring Boot.

## What It Demonstrates

Pagination is a mechanism for splitting a large dataset into fixed-size chunks. Instead of returning all records at once, the repository returns a single page — a slice of the full result with metadata: current page number, page size, and total record count.

The project illustrates this with a concrete example: a `CustomerRepository` that accepts a `Pageable` request and returns a `Page<Customer>` — only the requested slice of data.

## Structure

The entire project intentionally fits in a single file — so the pattern is visible in full, without unnecessary noise:

```
PaginationPageable.java
│
├── Customer                # Model — record with id and name
├── Page<T>                 # Pagination result — content + metadata (page, size, total)
├── Pageable                # Pagination request — page number and size
├── CustomerRepository      # Data access — returns Page<Customer> for a given Pageable
├── FakeCustomerTable       # In-memory data source — simulates a database table
└── PaginationPageable      # Spring Boot entry point + demo()
```

## Key Points

`Pageable` describes the request — which page and how many records per page. `offset()` computes the starting position in the dataset.

`Page<T>` wraps the result — carries the content slice alongside pagination metadata. The caller always knows the total number of records and can compute total pages.

`CustomerRepository` applies the pagination parameters to the in-memory dataset — simulating what a real database query with `LIMIT` and `OFFSET` would do.

## Running

```
./mvnw spring-boot:run
```

## Console Output

```
Page[content=[Customer[id=1, name=Alice], Customer[id=2, name=Bob]], page=0, size=2, total=5]
Page[content=[Customer[id=3, name=Charlie], Customer[id=4, name=Diana]], page=1, size=2, total=5]
Page[content=[Customer[id=5, name=Eve]], page=2, size=2, total=5]
```

Three pages from five records. The last page contains only one record — the remainder after even division.
