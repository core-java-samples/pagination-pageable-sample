package dev.samples.pagination.pageable;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ======================================================
// DOMAIN / MODEL (бизнес-модель)
// ======================================================
record Customer(long id, String name) {}

// ======================================================
// PAGINATION ABSTRACTIONS (контракт пагинации)
// ======================================================
record Page<T>(List<T> content, int page, int size, long total) {} // результат страницы

record Pageable(int page, int size) { // запрос пагинации

    int offset() {
        return page * size; // вычисление смещения (offset)
    }
}

// ======================================================
// REPOSITORY (доступ к данным / имитация persistence слоя)
// ======================================================
class CustomerRepository {

    Page<Customer> findAll(Pageable pageable) {

        List<Customer> all = FakeCustomerTable.DATA;

        int start = pageable.offset();
        int end = Math.min(start + pageable.size(), all.size());

        List<Customer> content;

        if (start >= all.size()) {
            content = List.of();
        } else {
            content = all.subList(start, end);
        }

        return new Page<>(
                content,
                pageable.page(),
                pageable.size(),
                all.size()
        );
    }
}

// ======================================================
// FAKE DATA SOURCE (имитация базы данных)
// ======================================================
class FakeCustomerTable {

    static final List<Customer> DATA = List.of(
            new Customer(1, "Alice"),
            new Customer(2, "Bob"),
            new Customer(3, "Charlie"),
            new Customer(4, "Diana"),
            new Customer(5, "Eve")
    );
}

// ======================================================
// APPLICATION ENTRY POINT (Spring Boot bootstrap)
// ======================================================
@SpringBootApplication
public class PaginationPageable {

    public static void main(String[] args) {
        SpringApplication.run(PaginationPageable.class, args);

        // demo запускается вручную (учебная цель)
        new PaginationPageable().demo();
    }

    // ==================================================
    // DEMO / USAGE (проверка работы пагинации)
    // ==================================================
    void demo() {

        CustomerRepository repo = new CustomerRepository();

        System.out.println(repo.findAll(new Pageable(0, 2)));
        System.out.println(repo.findAll(new Pageable(1, 2)));
        System.out.println(repo.findAll(new Pageable(2, 2)));
    }
}
