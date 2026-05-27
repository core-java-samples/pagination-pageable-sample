package dev.samples.pagination.pageable;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// -------------------- DOMAIN --------------------
record Customer(long id, String name) {}

record Page<T>(List<T> content, int page, int size, long total) {} // обертка

record Pageable(int page, int size) { // запрос
	int offset() { return page * size; } // смещение
}

class CustomerRepository {
	Page<Customer> findAll(Pageable pageable) {

		List<Customer> all = FakeCustomerTable.DATA;

		int start = pageable.offset(); // начало выборки
		int end = Math.min(start + pageable.size(), all.size()); //конец выборки

		List<Customer> content;

		if (start >= all.size()) content = List.of();
		else content = all.subList(start, end);

		return new Page<>(content, pageable.page(), pageable.size(), all.size());
	}
}

class FakeCustomerTable {
	static final List<Customer> DATA = List.of(
			new Customer(1, "Alice"),
			new Customer(2, "Bob"),
			new Customer(3, "Charlie"),
			new Customer(4, "Diana"),
			new Customer(5, "Eve")
	);
}

@SpringBootApplication
public class PaginationPageable {
	public static void main(String[] args) {
		SpringApplication.run(PaginationPageable.class, args);
		new PaginationPageable().demo();
	}
	void demo() {
		CustomerRepository repo = new CustomerRepository();

		System.out.println(repo.findAll(new Pageable(0, 2)));
		System.out.println(repo.findAll(new Pageable(1, 2)));
		System.out.println(repo.findAll(new Pageable(2, 2)));
	}
}
