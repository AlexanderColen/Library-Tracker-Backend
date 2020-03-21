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
package com.alexandercolen.LibraryTracker.services;

import com.alexandercolen.LibraryTracker.models.Book;
import com.alexandercolen.LibraryTracker.models.UserBook;
import com.alexandercolen.LibraryTracker.repositories.BookRepository;
import com.alexandercolen.LibraryTracker.repositories.UserBookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Colen
 */
@Service
public class UserBookService {
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    UserBookRepository userBookRepository;
    
    /**
     * Get all the userBooks from the database.
     * @return A list of UserBook objects.
     */
    public List<UserBook> getUserBooks() {
        return this.userBookRepository.findAll();
    }
    
    /**
     * Get a specific userBook from the database.
     * @param id The ID of the UserBook object.
     * @return The found UserBook object. Null if not found.
     */
    public UserBook getSpecificUserBook(String id) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        return userBookOptional.orElse(null);
    }
    
    /**
     * Insert a userBook in the database.
     * @param userBook The UserBook with all the fields to insert.
     * @return The UserBook object with the new ID.
     */
    public UserBook createUserBook(UserBook userBook) {
        Optional<Book> bookOptional = this.bookRepository.findByISBN(userBook.getBook().getIsbn());
        Book book = bookOptional.orElse(null);
        
        // Save new Book if it doesn't exist yet.
        if (book == null) {
            book = this.bookRepository.save(userBook.getBook());
            userBook.setBook(book);
        }
        
        return this.userBookRepository.save(userBook);
    }
    
    /**
     * Delete a userBook from the database based on ID.
     * @param id The ID of the UserBook object.
     * @return True if the deletion worked, False otherwise.
     */
    public boolean deleteUserBook(String id) {
        this.userBookRepository.deleteById(id);
        
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        UserBook userBook = userBookOptional.orElse(null);
        
        return userBook == null;
    }
    
    /**
     * Edit a userBook in the database.
     * @param id The ID of the UserBook object.
     * @param userBook The updated UserBook object.
     * @return Null if there is no UserBook with the ID, otherwise the edited UserBook object.
     */
    public UserBook editUserBook(String id, UserBook userBook) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        UserBook foundUserBook = userBookOptional.orElse(null);
        
        if (foundUserBook == null) {
            return null;
        }
        
        foundUserBook.setUser_id(userBook.getUser_id());
        foundUserBook.setBook(userBook.getBook());
        foundUserBook.setStatus(userBook.getStatus());
        
        return this.userBookRepository.save(foundUserBook);
    }
    
    /**
     * Get all the userBooks for a specific User from the database.
     * @param user_id The ID of the User in question.
     * @return A list of UserBook objects.
     */
    public Iterable<UserBook> getUserBooksForUser(String user_id) {
        return this.userBookRepository.findByUserId(user_id);
    }
}
