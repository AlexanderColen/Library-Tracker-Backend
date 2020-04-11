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
import com.alexandercolen.library.models.dtos.BookDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class BookControllerTest extends AbstractControllerTest {
    private static final Logger LOG = Logger.getLogger(BookControllerTest.class.getName());
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting BookController Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished BookController Testing.");
    }
    
    @Test
    public void testGetBooks() throws Exception {
        LOG.log(Level.INFO, "Testing GET /api/books...");
        
        String uri = "/api/books";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Book[] bookList = super.mapFromJson(content, Book[].class);
        assertFalse(bookList.length > 0);
        
        LOG.log(Level.INFO, "Finished testing GET /api/books.");
    }
    
    @Test
    public void testGetSpecificBook() throws Exception {
        LOG.log(Level.INFO, "Testing GET /api/books/{id}...");
        
        // Non-existing Book.
        String uri = "/api/books/123";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content);
        
        // Existing Book.
        // Create Book.
        uri = "/api/books";
        BookDTO bookDTOInput = this.createBookDTOModel("978-4-3920-1628-3");
        String inputJson = super.mapToJson(bookDTOInput);
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        // Test created Book.
        uri = "/api/books/".concat(bookResult.getId());

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        bookResult = super.mapFromJson(content, Book.class);
        assertNotNull(bookResult);
        assertNotNull(bookResult.getId());
        assertEquals(bookResult.getIsbn(), bookDTOInput.getIsbn());
        assertEquals(bookResult.getTitle(), bookDTOInput.getTitle());
        assertEquals(bookResult.getAuthor(), bookDTOInput.getAuthor());
        assertEquals(bookResult.getPages(), bookDTOInput.getPages());
        assertEquals(bookResult.getImage(), bookDTOInput.getImage());
        
        LOG.log(Level.INFO, "Finished testing GET /api/books/{id}.");
    }
    
    @Test
    public void testPostBook() throws Exception {
        LOG.log(Level.INFO, "Testing POST /api/books...");
        
        String uri = "/api/books";
        BookDTO bookDTOInput = this.createBookDTOModel("978-1-1104-3998-0");
        String inputJson = super.mapToJson(bookDTOInput);
       
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        assertNotNull(bookResult);
        assertNotNull(bookResult.getId());
        assertEquals(bookResult.getIsbn(), bookDTOInput.getIsbn());
        assertEquals(bookResult.getTitle(), bookDTOInput.getTitle());
        assertEquals(bookResult.getAuthor(), bookDTOInput.getAuthor());
        assertEquals(bookResult.getPages(), bookDTOInput.getPages());
        assertEquals(bookResult.getImage(), bookDTOInput.getImage());
        
        LOG.log(Level.INFO, "Finished testing POST /api/books.");
    }
    
    @Test
    public void testDeleteBook() throws Exception {
        LOG.log(Level.INFO, "Testing DELETE /api/books/{id}...");
        
        // Non-existing Book.
        String uri = "/api/books/123";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete(uri))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertNotEquals("true", content);
        assertEquals("false", content);
        
        // Existing Book.
        // Create Book.        
        uri = "/api/books";
        BookDTO bookDTOInput = this.createBookDTOModel("978-4-1933-0570-8");
        String inputJson = super.mapToJson(bookDTOInput);
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        
        // Test created Book.
        uri = "/api/books/".concat(bookResult.getId());

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
        
        LOG.log(Level.INFO, "Finished testing DELETE /api/books/{id}.");
    }
    
    @Test
    public void testPutBook() throws Exception {
        LOG.log(Level.INFO, "Testing PUT /api/books/{id}...");
        
        // Non-existing Book.
        String uri = "/api/books/123";
        
        BookDTO bookDTOInput = this.createBookDTOModel("978-5-7899-6859-8");
        String newTitle = "This is a new Book title";
        String inputJson = super.mapToJson(bookDTOInput);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content);
        
        // Existing Book.
        // Create Book.
        uri = "/api/books";
       
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        Book bookResult = super.mapFromJson(content, Book.class);
        assertNotNull(bookResult);
        assertNotEquals(newTitle, bookResult.getTitle());
        
        // Change Book.
        uri = "/api/books/".concat(bookResult.getId());
        bookDTOInput.setTitle(newTitle);
        inputJson = super.mapToJson(bookDTOInput);
        
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        
        content = mvcResult.getResponse().getContentAsString();
        bookResult = super.mapFromJson(content, Book.class);
        assertNotNull(bookResult);        
        
        // Test if specific Book has been changed afterwards.
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        bookResult = super.mapFromJson(content, Book.class);
        assertNotNull(bookResult);
        assertEquals(newTitle, bookResult.getTitle());
        
        LOG.log(Level.INFO, "Finished testing PUT /api/books/{id}.");
    }
    
//    @Test
//    public void testSearchForBook() throws Exception {
//        LOG.log(Level.INFO, "Testing GET /api/books/find....");
//        
//        String uri = "/api/books/find";
//        String criteria = "Book";
//
//        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
//            .param("criteria", criteria))
//            .andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        
//        String content = mvcResult.getResponse().getContentAsString();
//        Book[] bookList = super.mapFromJson(content, Book[].class);
//        assertTrue(bookList.length > 0);
//        
//        LOG.log(Level.INFO, "Finished testing GET /api/books/find.");
//    }
}