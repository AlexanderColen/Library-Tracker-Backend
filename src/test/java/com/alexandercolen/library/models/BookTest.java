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
package com.alexandercolen.library.models;

import com.alexandercolen.library.models.dtos.BookDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Alexander Colen
 */
@SpringBootTest
public class BookTest {
    private static final Logger LOG = Logger.getLogger(BookTest.class.getName());
    private Book book;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting Book model Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished Book model Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.book = new Book();
    }
    
    @Test
    public void testConstructors() {
        LOG.log(Level.INFO, "Testing constructors...");
        
        // Full constructor.
        String isbn = "978-0-13-601970-1";
        String title = "Book Title";
        String author = "Book Author";
        int pages = 123;
        String image = "https://www.image.url.png";
        this.book = new Book(isbn, title, author, pages, image);
        
        assertEquals(isbn, this.book.getIsbn());
        assertEquals(title, this.book.getTitle());
        assertEquals(author, this.book.getAuthor());
        assertEquals(pages, this.book.getPages());
        assertEquals(image, this.book.getImage());
        
        // Constructor using BookDTO.
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(isbn);
        bookDTO.setTitle(title);
        bookDTO.setAuthor(author);
        bookDTO.setPages(pages);
        bookDTO.setImage(image);
        this.book = new Book(bookDTO);
        
        assertEquals(bookDTO.getIsbn(), this.book.getIsbn());
        assertEquals(bookDTO.getTitle(), this.book.getTitle());
        assertEquals(bookDTO.getAuthor(), this.book.getAuthor());
        assertEquals(bookDTO.getPages(), this.book.getPages());
        assertEquals(bookDTO.getImage(), this.book.getImage());
        
        LOG.log(Level.INFO, "Finished testing constructors.");
    }
    
    @Test
    public void testIdProperties() {
        LOG.log(Level.INFO, "Testing Id properties...");
        
        assertEquals(null, this.book.getId());
        
        String id = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.book.setId(id);
        assertEquals(id, this.book.getId());
        
        id = "";
        this.book.setId(id);
        assertEquals(id, this.book.getId());
        
        this.book.setId(null);
        assertEquals(null, this.book.getId());
        
        LOG.log(Level.INFO, "Finished testing Id properties.");
    }
    
    @Test
    public void testISBNProperties() {
        LOG.log(Level.INFO, "Testing ISBN properties...");
        
        assertEquals(null, this.book.getIsbn());
        
        String isbn = "978-0-13-601970-1";
        this.book.setIsbn(isbn);
        assertEquals(isbn, this.book.getIsbn());
        
        isbn = "9780136019701";
        this.book.setIsbn(isbn);
        assertEquals(isbn, this.book.getIsbn());
        
        isbn = "0-545-01022-5";
        this.book.setIsbn(isbn);
        assertEquals(isbn, this.book.getIsbn());
        
        isbn = "0545010225";
        this.book.setIsbn(isbn);
        assertEquals(isbn, this.book.getIsbn());
        
        isbn = "";
        this.book.setIsbn(isbn);
        assertEquals(isbn, this.book.getIsbn());
        
        this.book.setIsbn(null);
        assertEquals(null, this.book.getIsbn());
        
        LOG.log(Level.INFO, "Finished testing ISBN properties...");
    }
    
    @Test
    public void testTitleProperties() {
        LOG.log(Level.INFO, "Testing Title properties...");
        
        assertEquals(null, this.book.getTitle());
        
        String title = "Book Title";
        this.book.setTitle(title);
        assertEquals(title, this.book.getTitle());
        
        title = "";
        this.book.setTitle(title);
        assertEquals(title, this.book.getTitle());
        
        this.book.setTitle(null);
        assertEquals(null, this.book.getTitle());
        
        LOG.log(Level.INFO, "Finished testing Title properties.");
    }
    
    @Test
    public void testAuthorProperties() {
        LOG.log(Level.INFO, "Testing Author properties...");
        
        assertEquals(null, this.book.getAuthor());
        
        String author = "Book Author";
        this.book.setAuthor(author);
        assertEquals(author, this.book.getAuthor());
        
        author = "";
        this.book.setAuthor(author);
        assertEquals(author, this.book.getAuthor());
        
        this.book.setAuthor(null);
        assertEquals(null, this.book.getAuthor());
        
        LOG.log(Level.INFO, "Finished testing Author properties.");
    }
    
    @Test
    public void testPagesProperties() {
        LOG.log(Level.INFO, "Testing Pages properties...");
        
        assertEquals(0, this.book.getPages());
        
        int pages = 1000;
        this.book.setPages(pages);
        assertEquals(pages, this.book.getPages());
        
        pages = -1;
        this.book.setPages(pages);
        assertEquals(pages, this.book.getPages());
        
        this.book.setPages(0);
        assertEquals(0, this.book.getPages());
        
        LOG.log(Level.INFO, "Finished testing Pages properties.");
    }
    
    @Test
    public void testImageProperties() {
        LOG.log(Level.INFO, "Testing Image properties...");
        
        assertEquals(null, this.book.getImage());
        
        String image = "https://www.image.url.png";
        this.book.setImage(image);
        assertEquals(image, this.book.getImage());
        
        image = "";
        this.book.setImage(image);
        assertEquals(image, this.book.getImage());
        
        this.book.setImage(null);
        assertEquals(null, this.book.getImage());
        
        LOG.log(Level.INFO, "Finished testing Image properties.");
    }
}
