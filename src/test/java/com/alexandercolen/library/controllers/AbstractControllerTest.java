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
import com.alexandercolen.library.controllers.bodies.AuthenticationBody;
import com.alexandercolen.library.models.Book;
import com.alexandercolen.library.models.UserBook;
import com.alexandercolen.library.models.enums.BookLocationStatus;
import com.alexandercolen.library.models.enums.BookProgressStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
    protected MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    protected void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
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
     * Create a mock UserBook model.
     * @param book An already created Book model.
     * @return The created UserBook model.
     */
    protected UserBook createUserBookModel(Book book) {
        String comment = "This is a comment.";
        UserBook userBook = new UserBook();
        userBook.setBook(book);
        userBook.setComment(comment);
        userBook.setLocationStatus(BookLocationStatus.OWNED);
        userBook.setProgressStatus(BookProgressStatus.READ);
        userBook.setUserId("e1ae98cd-5c0c-4d2c-9f09-043705f38391");
        
        return userBook;
    }
    
    /**
     * Create a AuthenticationBody model.
     * @param username The username for the User.
     * @return The created AuthenticationBody model.
     */
    protected AuthenticationBody createAuthenticationBody(String username) {
        String password = "NotAPassword";
        AuthenticationBody authenticationBody = new AuthenticationBody();
        authenticationBody.setUsername(username);
        authenticationBody.setPassword(password);
        return authenticationBody;
    }
}