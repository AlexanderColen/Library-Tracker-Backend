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

import com.alexandercolen.library.models.enums.BookLocationStatus;
import com.alexandercolen.library.models.enums.BookProgressStatus;
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
public class UserBookTest {
    private static final Logger LOG = Logger.getLogger(UserBookTest.class.getName());
    private UserBook userBook;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting UserBook model Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished UserBook model Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.userBook = new UserBook();
    }
    
    @Test
    public void testIdProperties() {
        LOG.log(Level.INFO, "Testing Id properties...");
        
        assertEquals(null, this.userBook.getId());
        
        String id = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.userBook.setId(id);
        assertEquals(id, this.userBook.getId());
        
        id = "";
        this.userBook.setId(id);
        assertEquals(id, this.userBook.getId());
        
        this.userBook.setId(null);
        assertEquals(null, this.userBook.getId());
        
        LOG.log(Level.INFO, "Finished testing Id properties.");
    }
    
    @Test
    public void testUserIdProperties() {
        LOG.log(Level.INFO, "Testing UserId properties...");
        
        assertEquals(null, this.userBook.getUserId());
        
        String userId = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.userBook.setUserId(userId);
        assertEquals(userId, this.userBook.getUserId());
        
        userId = "";
        this.userBook.setUserId(userId);
        assertEquals(userId, this.userBook.getUserId());
        
        this.userBook.setUserId(null);
        assertEquals(null, this.userBook.getUserId());
        
        LOG.log(Level.INFO, "Finished testing UserId properties.");
    }
    
    @Test
    public void testBookProperties() {
        LOG.log(Level.INFO, "Testing Book properties...");
        
        assertEquals(null, this.userBook.getBook());
        
        Book book = new Book();
        this.userBook.setBook(book);
        assertEquals(book, this.userBook.getBook());
        
        String isbn = "978-0-13-601970-1";
        String title = "Book Title";
        String author = "Book Author";
        int pages = 123;
        String image = "https://www.image.url.png";
        book = new Book(isbn, title, author, pages, image);
        this.userBook.setBook(book);
        assertEquals(book, this.userBook.getBook());
        
        this.userBook.setBook(null);
        assertEquals(null, this.userBook.getBook());
        
        LOG.log(Level.INFO, "Finished testing Book properties.");
    }
    
    @Test
    public void testLocationStatusProperties() {
        LOG.log(Level.INFO, "Testing LocationStatus properties...");
        
        assertEquals(BookLocationStatus.OWNED, this.userBook.getLocationStatus());
        
        BookLocationStatus locationStatus = BookLocationStatus.BORROWED;
        this.userBook.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBook.getLocationStatus());
        
        locationStatus = BookLocationStatus.LOANED;
        this.userBook.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBook.getLocationStatus());
        
        locationStatus = BookLocationStatus.OWNED;
        this.userBook.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBook.getLocationStatus());
        
        locationStatus = BookLocationStatus.WISHED;
        this.userBook.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBook.getLocationStatus());
        
        this.userBook.setLocationStatus(null);
        assertEquals(null, this.userBook.getLocationStatus());
        
        LOG.log(Level.INFO, "Finished testing LocationStatus properties.");
    }
    
    @Test
    public void testProgressStatusProperties() {
        LOG.log(Level.INFO, "Testing ProgressStatus properties...");
        
        assertEquals(BookProgressStatus.UNREAD, this.userBook.getProgressStatus());
        
        BookProgressStatus progressStatus = BookProgressStatus.ABANDONED;
        this.userBook.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBook.getProgressStatus());
        
        progressStatus = BookProgressStatus.PLAN_TO_READ;
        this.userBook.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBook.getProgressStatus());
        
        progressStatus = BookProgressStatus.READ;
        this.userBook.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBook.getProgressStatus());
        
        progressStatus = BookProgressStatus.READING;
        this.userBook.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBook.getProgressStatus());
        
        progressStatus = BookProgressStatus.UNREAD;
        this.userBook.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBook.getProgressStatus());
        
        this.userBook.setProgressStatus(null);
        assertEquals(null, this.userBook.getProgressStatus());
        
        LOG.log(Level.INFO, "Finished testing ProgressStatus properties.");
    }
    
    @Test
    public void testCommentProperties() {
        LOG.log(Level.INFO, "Testing Comment properties...");
        
        assertEquals(null, this.userBook.getComment());
        
        String comment = "10/10 Book! Would read again!";
        this.userBook.setComment(comment);
        assertEquals(comment, this.userBook.getComment());
        
        comment = "";
        this.userBook.setComment(comment);
        assertEquals(comment, this.userBook.getComment());
        
        this.userBook.setComment(null);
        assertEquals(null, this.userBook.getComment());
        
        LOG.log(Level.INFO, "Finished testing Comment properties.");
    }
}
