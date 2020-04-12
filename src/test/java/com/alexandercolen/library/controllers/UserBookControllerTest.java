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

import com.alexandercolen.library.models.Book;
import com.alexandercolen.library.models.User;
import com.alexandercolen.library.models.UserBook;
import com.alexandercolen.library.models.dtos.BookDTO;
import com.alexandercolen.library.models.dtos.UserBookDTO;
import com.alexandercolen.library.models.dtos.UserDTO;
import com.alexandercolen.library.models.enums.BookLocationStatus;
import com.alexandercolen.library.models.enums.BookProgressStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author Alexander Colen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class UserBookControllerTest extends AbstractControllerTest {
    private static final Logger LOG = Logger.getLogger(UserBookControllerTest.class.getName());
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting UserBookController Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished UserBookController Testing.");
    }
    
    @Test
    public void testGetUserBooks() throws Exception {
        LOG.log(Level.INFO, "Testing GET /api/userbooks...");
        
        String uri = "/api/userbooks";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        UserBook[] userBookList = super.mapFromJson(content, UserBook[].class);
        assertNotNull(userBookList);
        
        LOG.log(Level.INFO, "Finished testing GET /api/userbooks.");
    }
    
    @Test
    public void testGetSpecificUserBook() throws Exception {
        LOG.log(Level.INFO, "Testing GET /api/userbooks/{id}...");
        
        // Non-existing UserBook.
        String uri = "/api/userbooks/123";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content);
        
        // Existing UserBook.      
        // Create Book.
        uri = "/api/books";
        Book bookInput = this.createBookModel("978-7-8767-5875-4");
        String bookInputJson = super.mapToJson(bookInput);
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bookInputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdBooks.add(bookResult);
        }
        
        // Create UserBook
        UserBookDTO userBookDTOInput = this.createUserBookDTOModel(bookResult);
        String inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        UserBook userBookResult = super.mapFromJson(content, UserBook.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Test created UserBook.
        uri = "/api/userbooks/".concat(userBookResult.getId());

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        assertNotNull(userBookResult);
        assertNotNull(userBookResult.getId());
        assertEquals(userBookResult.getBook().getTitle(), userBookDTOInput.getBook().getTitle());
        assertEquals(userBookResult.getComment(), userBookDTOInput.getComment());
        assertEquals(userBookResult.getLocationStatus(), userBookDTOInput.getLocationStatus());
        assertEquals(userBookResult.getProgressStatus(), userBookDTOInput.getProgressStatus());
        assertEquals(userBookResult.getUserId(), userBookDTOInput.getUserId());
        
        LOG.log(Level.INFO, "Finished testing GET /api/userbooks/{id}.");
    }
    
    @Test
    public void testPostUserBook() throws Exception {
        LOG.log(Level.INFO, "Testing POST /api/userbooks...");
        
        // With existing Book with ISBN.
        // Create Book.
        String uri = "/api/books";
        Book bookInput = this.createBookModel("978-9-1014-3285-9");
        String bookInputJson = super.mapToJson(bookInput);
       
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bookInputJson))
            .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdBooks.add(bookResult);
        }
        
        UserBookDTO userBookDTOInput = this.createUserBookDTOModel(bookResult);
        String inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        UserBook userBookResult = super.mapFromJson(content, UserBook.class);
        assertNotNull(userBookResult);
        assertNotNull(userBookResult.getId());
        assertEquals(userBookResult.getBook().getTitle(), userBookDTOInput.getBook().getTitle());
        assertEquals(userBookResult.getComment(), userBookDTOInput.getComment());
        assertEquals(userBookResult.getLocationStatus(), userBookDTOInput.getLocationStatus());
        assertEquals(userBookResult.getProgressStatus(), userBookDTOInput.getProgressStatus());
        assertEquals(userBookResult.getUserId(), userBookDTOInput.getUserId());
        
        if (status == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // With existing Book without ISBN.
        uri = "/api/books";
        bookInput = this.createBookModel(null);
        bookInputJson = super.mapToJson(bookInput);
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bookInputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        bookResult = super.mapFromJson(content, Book.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdBooks.add(bookResult);
        }
        
        userBookDTOInput = this.createUserBookDTOModel(bookResult);
        inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        assertNotNull(userBookResult);
        assertNotNull(userBookResult.getId());
        assertEquals(userBookResult.getBook().getTitle(), userBookDTOInput.getBook().getTitle());
        assertEquals(userBookResult.getComment(), userBookDTOInput.getComment());
        assertEquals(userBookResult.getLocationStatus(), userBookDTOInput.getLocationStatus());
        assertEquals(userBookResult.getProgressStatus(), userBookDTOInput.getProgressStatus());
        assertEquals(userBookResult.getUserId(), userBookDTOInput.getUserId());
        
        if (status == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Without existing Book.
        // Create Book.
        Book book = this.createBookModel("978-1-0849-0986-1");
        
        userBookDTOInput = this.createUserBookDTOModel(book);
        inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        assertNotNull(userBookResult);
        assertNotNull(userBookResult.getId());
        assertEquals(userBookResult.getBook().getTitle(), userBookDTOInput.getBook().getTitle());
        assertEquals(userBookResult.getComment(), userBookDTOInput.getComment());
        assertEquals(userBookResult.getLocationStatus(), userBookDTOInput.getLocationStatus());
        assertEquals(userBookResult.getProgressStatus(), userBookDTOInput.getProgressStatus());
        assertEquals(userBookResult.getUserId(), userBookDTOInput.getUserId());
        
        if (status == 200) {
            this.createdUserBooks.add(userBookResult);
            this.createdBooks.add(userBookResult.getBook());
        }
        
        LOG.log(Level.INFO, "Finished testing POST /api/userbooks.");
    }
    
    @Test
    public void testDeleteUserBook() throws Exception {
        LOG.log(Level.INFO, "Testing DELETE /api/userbooks/{id}...");
        
        // Non-existing UserBook.
        String uri = "/api/userbooks/123";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete(uri))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertNotEquals("true", content);
        assertEquals("false", content);
        
        // Existing UserBook.        
        // Create Book.
        uri = "/api/books";
        Book bookInput = this.createBookModel("978-9-4981-3006-2");
        String bookInputJson = super.mapToJson(bookInput);
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bookInputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdBooks.add(bookResult);
        }
        
        // Create UserBook.
        UserBookDTO userBookDTOInput = this.createUserBookDTOModel(bookResult);
        String inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        UserBook userBookResult = super.mapFromJson(content, UserBook.class);
        
        // Test created UserBook.
        uri = "/api/userbooks/".concat(userBookResult.getId());

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete(uri))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        assertEquals("true", content);
        assertNotEquals("false", content);
        
        // Test if specific Book exists afterwards.
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content);
        
        LOG.log(Level.INFO, "Finished testing DELETE /api/userbooks/{id}.");
    }
    
    @Test
    public void testPutUserBook() throws Exception {
        LOG.log(Level.INFO, "Testing PUT /api/userbooks/{id}...");
        
        // Non-existing UserBook.
        // Create Book.
        String uri = "/api/books";
        Book bookInput = this.createBookModel("978-3-9206-5586-4");
        String bookInputJson = super.mapToJson(bookInput);
       
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bookInputJson))
            .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdBooks.add(bookResult);
        }
        
        UserBookDTO userBookDTOInput = this.createUserBookDTOModel(bookResult);
        String newComment = "This is a different comment.";
        BookLocationStatus newLocation = BookLocationStatus.LOANED;
        BookProgressStatus newProgress = BookProgressStatus.ABANDONED;
        String inputJson = super.mapToJson(userBookDTOInput);

        uri = "/api/userbooks/123";
        
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        
        content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content);
        
        // Existing UserBook.
        // Create UserBook.
        uri = "/api/userbooks";
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        UserBook userBookResult = super.mapFromJson(content, UserBook.class);
        assertNotNull(userBookResult);
        assertNotEquals(newComment, userBookResult.getComment());
        assertNotEquals(newLocation, userBookResult.getLocationStatus());
        assertNotEquals(newProgress, userBookResult.getProgressStatus());
        
        if (status == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Change UserBook.
        uri = "/api/userbooks/".concat(userBookResult.getId());
        userBookDTOInput.setComment(newComment);
        userBookDTOInput.setLocationStatus(newLocation);
        userBookDTOInput.setProgressStatus(newProgress);
        inputJson = super.mapToJson(userBookDTOInput);
        
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        
        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        assertNotNull(userBookResult);        
        
        // Test if specific UserBook has been changed afterwards.
        uri = "/api/userbooks/".concat(userBookResult.getId());
        
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        assertNotNull(userBookResult);
        assertEquals(newLocation, userBookResult.getLocationStatus());
        assertEquals(newProgress, userBookResult.getProgressStatus());
        assertEquals(newComment, userBookResult.getComment());
        
        LOG.log(Level.INFO, "Finished testing PUT /api/userbooks/{id}.");
    }
    
    @Test
    public void testGetUserBooksForUser() throws Exception {
        LOG.log(Level.INFO, "Testing GET /api/userbooks/user/{userId}...");
        
        // Non-existing UserID.
        String uri = "/api/userbooks/user/123";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        UserBook[] userBookList = super.mapFromJson(content, UserBook[].class);
        assertFalse(userBookList.length > 0);
        
        // Existing UserID.
        // Create Book.
        uri = "/api/books";
        Book bookInput = this.createBookModel("978-3-0729-8981-7");
        String bookInputJson = super.mapToJson(bookInput);
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(bookInputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdBooks.add(bookResult);
        }
        
        // Create UserBook.
        UserBookDTO userBookDTOInput = this.createUserBookDTOModel(bookResult);
        String inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        UserBook userBookResult = super.mapFromJson(content, UserBook.class);
        
        if (status == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Test created UserBook.
        uri = "/api/userbooks/user/".concat(userBookResult.getUserId());

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        userBookList = super.mapFromJson(content, UserBook[].class);
        assertTrue(userBookList.length > 0);
        
        LOG.log(Level.INFO, "Finished testing GET /api/userbooks/user/{userId}.");
    }
    
    @Test
    public void testGetStatisticsForUser() throws Exception {
        LOG.log(Level.INFO, "Testing GET /api/userbooks/user/{username}/statistics...");
        
        String totalPages = "totalPages";
        String booksRead = "booksRead";
        String booksUnread = "booksUnread";
        String booksReading = "booksReading";
        String booksPlanToRead = "booksPlanToRead";
        String booksAbandoned = "booksAbandoned";
        String booksOwned = "booksOwned";
        String booksLoaned = "booksLoaned";
        String booksBorrowed = "booksBorrowed";
        String booksWished = "booksWished";
        
        // Non-existant User.
        String uri = "/api/userbooks/user/FakeUser/statistics";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("No user found with this username!", content);
        
        // Existing User.
        // Create User.
        uri = "/api/auth/register";
        String username = "SomeUsername";
        UserDTO userDTOInput = this.createUserDTO(username);
        String inputJson = super.mapToJson(userDTOInput);
       
        this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson));
        
        uri = "/api/auth/login";

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        Map<Object, Object> output = super.mapFromJson(content, HashMap.class);
        User createdUser = new User();
        String userId = output.get("userId").toString();
        createdUser.setId(userId);
        this.createdUsers.add(createdUser);
        
        // No UserBooks.
        uri = "/api/userbooks/user/".concat(username).concat("/statistics");

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        output = super.mapFromJson(content, HashMap.class);
        assertNotNull(output);
        assertEquals(0, output.get(totalPages));
        assertEquals(0, output.get(booksRead));
        assertEquals(0, output.get(booksUnread));
        assertEquals(0, output.get(booksReading));
        assertEquals(0, output.get(booksPlanToRead));
        assertEquals(0, output.get(booksAbandoned));
        assertEquals(0, output.get(booksOwned));
        assertEquals(0, output.get(booksLoaned));
        assertEquals(0, output.get(booksBorrowed));
        assertEquals(0, output.get(booksWished));
        
        // Existing UserBooks.
        // Create Book.
        uri = "/api/books";
        BookDTO bookDTOInput = this.createBookDTOModel("978-0-1995-9230-2");
        inputJson = super.mapToJson(bookDTOInput);
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdBooks.add(bookResult);
        }
        
        // Create OWNED READ UserBook.
        UserBookDTO userBookDTOInput = this.createUserBookDTOModel(bookResult);
        userBookDTOInput.setLocationStatus(BookLocationStatus.OWNED);
        userBookDTOInput.setProgressStatus(BookProgressStatus.READ);
        userBookDTOInput.setUserId(userId);
        inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();
        
        content = mvcResult.getResponse().getContentAsString();
        UserBook userBookResult = super.mapFromJson(content, UserBook.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Create LOANED UNREAD UserBook.
        userBookDTOInput = this.createUserBookDTOModel(bookResult);
        userBookDTOInput.setLocationStatus(BookLocationStatus.LOANED);
        userBookDTOInput.setProgressStatus(BookProgressStatus.UNREAD);
        userBookDTOInput.setUserId(userId);
        inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();
        
        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Create BORROWED ABANDONED UserBook.
        userBookDTOInput = this.createUserBookDTOModel(bookResult);
        userBookDTOInput.setLocationStatus(BookLocationStatus.BORROWED);
        userBookDTOInput.setProgressStatus(BookProgressStatus.ABANDONED);
        userBookDTOInput.setUserId(userId);
        inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();
        
        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Create WISHED PLAN_TO_READ UserBook.
        userBookDTOInput = this.createUserBookDTOModel(bookResult);
        userBookDTOInput.setLocationStatus(BookLocationStatus.WISHED);
        userBookDTOInput.setProgressStatus(BookProgressStatus.PLAN_TO_READ);
        userBookDTOInput.setUserId(userId);
        inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();
        
        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Create OWNED READING UserBook.
        userBookDTOInput = this.createUserBookDTOModel(bookResult);
        userBookDTOInput.setLocationStatus(BookLocationStatus.OWNED);
        userBookDTOInput.setProgressStatus(BookProgressStatus.READING);
        userBookDTOInput.setUserId(userId);
        inputJson = super.mapToJson(userBookDTOInput);
       
        uri = "/api/userbooks";
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();
        
        content = mvcResult.getResponse().getContentAsString();
        userBookResult = super.mapFromJson(content, UserBook.class);
        
        if (mvcResult.getResponse().getStatus() == 200) {
            this.createdUserBooks.add(userBookResult);
        }
        
        // Check statistics.
        // Total pages should be 123. (Because only 1 Book was marked as read.)
        // Created UserBooks location: OWNED 2 - LOANED 1 - BORROWED 1 - WISHED 1.
        // Created UserBooks progress: READ 1 - UNREAD 1 - PLAN_TO_READ 1 - READING 1 - ABANDONED 1.
        uri = "/api/userbooks/user/".concat(username).concat("/statistics");

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        output = super.mapFromJson(content, HashMap.class);
        assertNotNull(output);
        assertEquals(123, output.get(totalPages));
        assertEquals(1, output.get(booksRead));
        assertEquals(1, output.get(booksUnread));
        assertEquals(1, output.get(booksReading));
        assertEquals(1, output.get(booksPlanToRead));
        assertEquals(1, output.get(booksAbandoned));
        assertEquals(2, output.get(booksOwned));
        assertEquals(1, output.get(booksLoaned));
        assertEquals(1, output.get(booksBorrowed));
        assertEquals(1, output.get(booksWished));
    }
}