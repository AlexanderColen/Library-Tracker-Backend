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
public class UserBookDTOTest {
    private static final Logger LOG = Logger.getLogger(UserBookDTOTest.class.getName());
    private UserBookDTO userBookDTO;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting UserBookDTO model Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished UserBookDTO model Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.userBookDTO = new UserBookDTO();
    }
    
    @Test
    public void testIdProperties() {
        LOG.log(Level.INFO, "Testing Id properties...");
        
        assertEquals(null, this.userBookDTO.getId());
        
        String id = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.userBookDTO.setId(id);
        assertEquals(id, this.userBookDTO.getId());
        
        id = "";
        this.userBookDTO.setId(id);
        assertEquals(id, this.userBookDTO.getId());
        
        this.userBookDTO.setId(null);
        assertEquals(null, this.userBookDTO.getId());
        
        LOG.log(Level.INFO, "Finished testing Id properties.");
    }
    
    @Test
    public void testUserIdProperties() {
        LOG.log(Level.INFO, "Testing UserId properties...");
        
        assertEquals(null, this.userBookDTO.getUserId());
        
        String userId = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.userBookDTO.setUserId(userId);
        assertEquals(userId, this.userBookDTO.getUserId());
        
        userId = "";
        this.userBookDTO.setUserId(userId);
        assertEquals(userId, this.userBookDTO.getUserId());
        
        this.userBookDTO.setUserId(null);
        assertEquals(null, this.userBookDTO.getUserId());
        
        LOG.log(Level.INFO, "Finished testing UserId properties.");
    }
    
    @Test
    public void testBookProperties() {
        LOG.log(Level.INFO, "Testing Book properties...");
        
        assertEquals(null, this.userBookDTO.getBook());
        
        Book book = new Book();
        this.userBookDTO.setBook(book);
        assertEquals(book, this.userBookDTO.getBook());
        
        String isbn = "978-0-13-601970-1";
        String title = "Book Title";
        String author = "Book Author";
        int pages = 123;
        String image = "https://www.image.url.png";
        book = new Book(isbn, title, author, pages, image);
        this.userBookDTO.setBook(book);
        assertEquals(book, this.userBookDTO.getBook());
        
        this.userBookDTO.setBook(null);
        assertEquals(null, this.userBookDTO.getBook());
        
        LOG.log(Level.INFO, "Finished testing Book properties.");
    }
    
    @Test
    public void testLocationStatusProperties() {
        LOG.log(Level.INFO, "Testing LocationStatus properties...");
        
        BookLocationStatus locationStatus = BookLocationStatus.BORROWED;
        this.userBookDTO.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBookDTO.getLocationStatus());
        
        locationStatus = BookLocationStatus.LOANED;
        this.userBookDTO.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBookDTO.getLocationStatus());
        
        locationStatus = BookLocationStatus.OWNED;
        this.userBookDTO.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBookDTO.getLocationStatus());
        
        locationStatus = BookLocationStatus.WISHED;
        this.userBookDTO.setLocationStatus(locationStatus);
        assertEquals(locationStatus, this.userBookDTO.getLocationStatus());
        
        this.userBookDTO.setLocationStatus(null);
        assertEquals(null, this.userBookDTO.getLocationStatus());
        
        LOG.log(Level.INFO, "Finished testing LocationStatus properties.");
    }
    
    @Test
    public void testProgressStatusProperties() {
        LOG.log(Level.INFO, "Testing ProgressStatus properties...");
        
        BookProgressStatus progressStatus = BookProgressStatus.ABANDONED;
        this.userBookDTO.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBookDTO.getProgressStatus());
        
        progressStatus = BookProgressStatus.PLAN_TO_READ;
        this.userBookDTO.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBookDTO.getProgressStatus());
        
        progressStatus = BookProgressStatus.READ;
        this.userBookDTO.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBookDTO.getProgressStatus());
        
        progressStatus = BookProgressStatus.READING;
        this.userBookDTO.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBookDTO.getProgressStatus());
        
        progressStatus = BookProgressStatus.UNREAD;
        this.userBookDTO.setProgressStatus(progressStatus);
        assertEquals(progressStatus, this.userBookDTO.getProgressStatus());
        
        this.userBookDTO.setProgressStatus(null);
        assertEquals(null, this.userBookDTO.getProgressStatus());
        
        LOG.log(Level.INFO, "Finished testing ProgressStatus properties.");
    }
    
    @Test
    public void testCommentProperties() {
        LOG.log(Level.INFO, "Testing Comment properties...");
        
        assertEquals(null, this.userBookDTO.getComment());
        
        String comment = "10/10 Book! Would read again!";
        this.userBookDTO.setComment(comment);
        assertEquals(comment, this.userBookDTO.getComment());
        
        comment = "";
        this.userBookDTO.setComment(comment);
        assertEquals(comment, this.userBookDTO.getComment());
        
        this.userBookDTO.setComment(null);
        assertEquals(null, this.userBookDTO.getComment());
        
        LOG.log(Level.INFO, "Finished testing Comment properties.");
    }
}
