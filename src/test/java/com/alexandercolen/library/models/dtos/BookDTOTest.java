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
package com.alexandercolen.library.models.dtos;

import com.alexandercolen.library.models.*;
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
public class BookDTOTest {
    private static final Logger LOG = Logger.getLogger(BookDTOTest.class.getName());
    private BookDTO bookDTO;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting BookDTO model Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished BookDTO model Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.bookDTO = new BookDTO();
    }
    
    @Test
    public void testIdProperties() {
        LOG.log(Level.INFO, "Testing Id properties...");
        
        assertEquals(null, this.bookDTO.getId());
        
        String id = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.bookDTO.setId(id);
        assertEquals(id, this.bookDTO.getId());
        
        id = "";
        this.bookDTO.setId(id);
        assertEquals(id, this.bookDTO.getId());
        
        this.bookDTO.setId(null);
        assertEquals(null, this.bookDTO.getId());
        
        LOG.log(Level.INFO, "Finished testing Id properties.");
    }
    
    @Test
    public void testISBNProperties() {
        LOG.log(Level.INFO, "Testing ISBN properties...");
        
        assertEquals(null, this.bookDTO.getIsbn());
        
        String isbn = "978-0-13-601970-1";
        this.bookDTO.setIsbn(isbn);
        assertEquals(isbn, this.bookDTO.getIsbn());
        
        isbn = "9780136019701";
        this.bookDTO.setIsbn(isbn);
        assertEquals(isbn, this.bookDTO.getIsbn());
        
        isbn = "0-545-01022-5";
        this.bookDTO.setIsbn(isbn);
        assertEquals(isbn, this.bookDTO.getIsbn());
        
        isbn = "0545010225";
        this.bookDTO.setIsbn(isbn);
        assertEquals(isbn, this.bookDTO.getIsbn());
        
        isbn = "";
        this.bookDTO.setIsbn(isbn);
        assertEquals(isbn, this.bookDTO.getIsbn());
        
        this.bookDTO.setIsbn(null);
        assertEquals(null, this.bookDTO.getIsbn());
        
        LOG.log(Level.INFO, "Finished testing ISBN properties...");
    }
    
    @Test
    public void testTitleProperties() {
        LOG.log(Level.INFO, "Testing Title properties...");
        
        assertEquals(null, this.bookDTO.getTitle());
        
        String title = "Book Title";
        this.bookDTO.setTitle(title);
        assertEquals(title, this.bookDTO.getTitle());
        
        title = "";
        this.bookDTO.setTitle(title);
        assertEquals(title, this.bookDTO.getTitle());
        
        this.bookDTO.setTitle(null);
        assertEquals(null, this.bookDTO.getTitle());
        
        LOG.log(Level.INFO, "Finished testing Title properties.");
    }
    
    @Test
    public void testAuthorProperties() {
        LOG.log(Level.INFO, "Testing Author properties...");
        
        assertEquals(null, this.bookDTO.getAuthor());
        
        String author = "Book Author";
        this.bookDTO.setAuthor(author);
        assertEquals(author, this.bookDTO.getAuthor());
        
        author = "";
        this.bookDTO.setAuthor(author);
        assertEquals(author, this.bookDTO.getAuthor());
        
        this.bookDTO.setAuthor(null);
        assertEquals(null, this.bookDTO.getAuthor());
        
        LOG.log(Level.INFO, "Finished testing Author properties.");
    }
    
    @Test
    public void testPagesProperties() {
        LOG.log(Level.INFO, "Testing Pages properties...");
        
        assertEquals(0, this.bookDTO.getPages());
        
        int pages = 1000;
        this.bookDTO.setPages(pages);
        assertEquals(pages, this.bookDTO.getPages());
        
        pages = -1;
        this.bookDTO.setPages(pages);
        assertEquals(pages, this.bookDTO.getPages());
        
        this.bookDTO.setPages(0);
        assertEquals(0, this.bookDTO.getPages());
        
        LOG.log(Level.INFO, "Finished testing Pages properties.");
    }
    
    @Test
    public void testImageProperties() {
        LOG.log(Level.INFO, "Testing Image properties...");
        
        assertEquals(null, this.bookDTO.getImage());
        
        String image = "https://www.image.url.png";
        this.bookDTO.setImage(image);
        assertEquals(image, this.bookDTO.getImage());
        
        image = "";
        this.bookDTO.setImage(image);
        assertEquals(image, this.bookDTO.getImage());
        
        this.bookDTO.setImage(null);
        assertEquals(null, this.bookDTO.getImage());
        
        LOG.log(Level.INFO, "Finished testing Image properties.");
    }
}