package com.db.bookstore.service;

import com.db.bookstore.model.Book;
import com.db.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll() throws Exception {
        List<Book> books = bookRepository.getAllBooks();

        if (books.size() == 0) {
            throw new Exception("There are no books to be shown here!");
        }

        return books;
    }

    public void insertBook(Book book) {
        bookRepository.save(book);
    }

    public int getBookNo() {
        return bookRepository.getBookNo();
    }
}
