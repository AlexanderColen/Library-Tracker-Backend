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
import com.alexandercolen.library.models.UserBook;
import com.alexandercolen.library.models.dtos.UserBookDTO;
import com.alexandercolen.library.repositories.BookRepository;
import com.alexandercolen.library.repositories.UserBookRepository;
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
     * Get all the UserBooks from the database.
     * @return A list of UserBook objects.
     */
    public List<UserBook> getUserBooks() {
        return this.userBookRepository.findAll();
    }
    
    /**
     * Get a specific UserBook from the database.
     * @param id The ID of the UserBook object.
     * @return The found UserBook object. Null if not found.
     */
    public UserBook getSpecificUserBook(String id) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        return userBookOptional.orElse(null);
    }
    
    /**
     * Insert a UserBook in the database.
     * @param userBookDTO The UserBookDTO with all the fields to insert.
     * @return The UserBook object with the new ID.
     */
    public UserBook createUserBook(UserBookDTO userBookDTO) {
        Book book = null;
        
        // Check if ISBN is not null before searching.
        if (userBookDTO.getBook().getIsbn() != null) {
            Optional<Book> bookOptional = this.bookRepository.findByISBN(userBookDTO.getBook().getIsbn());
            book = bookOptional.orElse(null);
        }
        
        // Save new Book if it doesn't exist yet.
        if (book == null) {
            book = this.bookRepository.save(userBookDTO.getBook());
        }
        
        UserBook userBook = new UserBook(userBookDTO);
        userBook.setBook(book);
        
        return this.userBookRepository.save(userBook);
    }
    
    /**
     * Delete a UserBook from the database based on ID.
     * @param id The ID of the UserBook object.
     * @return True if the deletion worked, False if the UserBook doesn't exist.
     */
    public boolean deleteUserBook(String id) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        UserBook userBook = userBookOptional.orElse(null);
        
        if (userBook == null) {
            return false;
        }
        
        this.userBookRepository.deleteById(id);
                
        return true;
    }
    
    /**
     * Edit a UserBook in the database.
     * @param id The ID of the UserBook object.
     * @param userBookDTO The updated UserBookDTO object.
     * @return Null if there is no UserBook with the ID, otherwise the edited UserBook object.
     */
    public UserBook editUserBook(String id, UserBookDTO userBookDTO) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        UserBook foundUserBook = userBookOptional.orElse(null);
        
        if (foundUserBook == null) {
            return null;
        }
        
        foundUserBook.setUserId(userBookDTO.getUserId());
        foundUserBook.setBook(userBookDTO.getBook());
        foundUserBook.setLocationStatus(userBookDTO.getLocationStatus());
        foundUserBook.setProgressStatus(userBookDTO.getProgressStatus());
        foundUserBook.setComment(userBookDTO.getComment());
        
        return this.userBookRepository.save(foundUserBook);
    }
    
    /**
     * Get all the UserBooks for a specific User from the database.
     * @param userId The ID of the User in question.
     * @return A list of UserBook objects.
     */
    public Iterable<UserBook> getUserBooksForUser(String userId) {
        return this.userBookRepository.findByUserId(userId);
    }
}
