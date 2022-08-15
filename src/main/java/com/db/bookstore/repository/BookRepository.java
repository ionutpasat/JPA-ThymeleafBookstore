package com.db.bookstore.repository;

import com.db.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM book", nativeQuery = true)
    List<Book> getAllBooks();
//    @Query(value = "INSERT INTO book (pages, publisher, title) VALUES (?, ?, ?)", nativeQuery = true)
//    void insertBook(int pages, String publisher, String title);
    @Query(value = "SELECT COUNT(*) FROM books", nativeQuery = true)
    int getBookNo();
}
