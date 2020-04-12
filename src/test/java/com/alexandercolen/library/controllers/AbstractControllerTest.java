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
package com.alexandercolen.library.controllers;

import com.alexandercolen.library.LibraryApplication;
import com.alexandercolen.library.models.Book;
import com.alexandercolen.library.models.User;
import com.alexandercolen.library.models.UserBook;
import com.alexandercolen.library.models.dtos.BookDTO;
import com.alexandercolen.library.models.dtos.UserBookDTO;
import com.alexandercolen.library.models.dtos.UserDTO;
import com.alexandercolen.library.models.enums.BookLocationStatus;
import com.alexandercolen.library.models.enums.BookProgressStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Alexander Colen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@WebAppConfiguration
public abstract class AbstractControllerTest {
    private static final Logger LOG = Logger.getLogger(AbstractControllerTest.class.getName());
    
    protected MockMvc mockMvc;
    protected List<Book> createdBooks;
    protected List<User> createdUsers;
    protected List<UserBook> createdUserBooks;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    protected void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.createdBooks = new ArrayList<>();
        this.createdUsers = new ArrayList<>();
        this.createdUserBooks = new ArrayList<>();
    }
    
    @AfterEach
    protected void tearDown() {
        try {
            LOG.log(Level.INFO, "Deleting created test Books...");
            for (Book book : this.createdBooks) {
                this.deleteBook(book.getId());
            }

            LOG.log(Level.INFO, "Deleting created test Users...");
            for (User user : this.createdUsers) {
                this.deleteUser(user.getId());
            }

            LOG.log(Level.INFO, "Deleting created test UserBooks...");
            for (UserBook userBook : this.createdUserBooks) {
                this.deleteUserBook(userBook.getId());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
   
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
   
    protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
    
    /**
     * Create a mock Book model.
     * @param isbn The ISBN that the Book should have.
     * @return The created Book model.
     */
    protected Book createBookModel(String isbn) {
        String title = "Book Title";
        String author = "Book Author";
        int pages = 123;
        String image = "https://www.image.url.png";
        return new Book(isbn, title, author, pages, image);
    }
    
    /**
     * Create a mock BookDTO model.
     * @param isbn The ISBN that the BookDTO should have.
     * @return The created BookDTO model.
     */
    protected BookDTO createBookDTOModel(String isbn) {
        String title = "Book Title";
        String author = "Book Author";
        int pages = 123;
        String image = "https://www.image.url.png";
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(isbn);
        bookDTO.setTitle(title);
        bookDTO.setAuthor(author);
        bookDTO.setPages(pages);
        bookDTO.setImage(image);
        return bookDTO;
    }
    
    /**
     * Create a mock UserBookDTO model.
     * @param book An already created Book model.
     * @return The created UserBookDTO model.
     */
    protected UserBookDTO createUserBookDTOModel(Book book) {
        String comment = "This is a comment.";
        UserBookDTO userBookDTO = new UserBookDTO();
        userBookDTO.setBook(book);
        userBookDTO.setComment(comment);
        userBookDTO.setLocationStatus(BookLocationStatus.OWNED);
        userBookDTO.setProgressStatus(BookProgressStatus.READ);
        userBookDTO.setUserId("e1ae98cd-5c0c-4d2c-9f09-043705f38391");
        
        return userBookDTO;
    }
    
    /**
     * Create a UserDTO model.
     * @param username The username for the User.
     * @return The created UserDTO model.
     */
    protected UserDTO createUserDTO(String username) {
        String password = "NotAPassword";
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        return userDTO;
    }
    
    /**
     * Delete a Book if it exists.
     * @param bookId The ID of the Book to delete.
     * @throws java.lang.Exception
     */
    protected void deleteBook(String bookId) throws Exception {
        String uri = "/api/books/".concat(bookId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(uri));
    }
    
    /**
     * Delete a User if it exists.
     * @param userId The ID of the User to delete.
     * @throws java.lang.Exception
     */
    protected void deleteUser(String userId) throws Exception {
        String uri = "/api/auth/".concat(userId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(uri));
    }
    
    /**
     * Delete a UserBook if it exists.
     * @param userBookId The ID of the UserBook to delete.
     * @throws java.lang.Exception
     */
    protected void deleteUserBook(String userBookId) throws Exception {
        String uri = "/api/userbooks/".concat(userBookId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(uri));
    }
}