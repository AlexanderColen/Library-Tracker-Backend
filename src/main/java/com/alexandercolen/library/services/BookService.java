/*
 * Copyright (C) 2020 Alexander Colen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alexandercolen.library.services;

import com.alexandercolen.library.models.Book;
import com.alexandercolen.library.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Colen
 */
@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    
    /**
     * Get all the books from the database.
     * @return A list of Book objects.
     */
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
    
    /**
     * Get a specific book from the database.
     * @param id The ID of the Book object.
     * @return The found Book object. Null if not found.
     */
    public Book getSpecificBook(String id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        return bookOptional.orElse(null);
    }
    
    /**
     * Insert a book in the database.
     * @param book The Book with all the fields to insert.
     * @return The Book object with the new ID.
     */
    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }
    
    /**
     * Delete a book from the database based on ID.
     * @param id The ID of the Book object.
     * @return True if the deletion worked, False otherwise.
     */
    public boolean deleteBook(String id) {
        this.bookRepository.deleteById(id);
        
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        Book book = bookOptional.orElse(null);
        
        return book == null;
    }
    
    /**
     * Edit a book in the database.
     * @param id The ID of the Book object.
     * @param book The updated Book object.
     * @return Null if there is no Book with the ID, otherwise the edited Book object.
     */
    public Book editBook(String id, Book book) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        Book foundBook = bookOptional.orElse(null);
        
        if (foundBook == null) {
            return null;
        }
        
        foundBook.setIsbn(book.getIsbn());
        foundBook.setTitle(book.getTitle());
        foundBook.setAuthor(book.getAuthor());
        foundBook.setPages(book.getPages());
        foundBook.setImage(book.getImage());
        
        return this.bookRepository.save(foundBook);
    }
}
