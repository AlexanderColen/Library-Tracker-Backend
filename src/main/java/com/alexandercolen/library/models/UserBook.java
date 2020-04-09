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
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Alexander Colen
 */
@Document(collection = "userbooks")
public class UserBook {
    @Id
    private String id;

    private String userId;
    @DBRef
    private Book book;
    private BookLocationStatus locationStatus;
    private BookProgressStatus progressStatus;
    private String comment;

    /**
     * Default constructor.
     */
    public UserBook() {
        this.locationStatus = BookLocationStatus.OWNED;
        this.progressStatus = BookProgressStatus.UNREAD;
    }    
    
    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the value of userId
     *
     * @return the value of userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Set the value of userId
     *
     * @param userId new value of userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the value of book
     *
     * @return the value of book
     */
    public Book getBook() {
        return this.book;
    }

    /**
     * Set the value of book
     *
     * @param book new value of book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Get the value of locationStatus
     *
     * @return the value of locationStatus
     */
    public BookLocationStatus getLocationStatus() {
        return this.locationStatus;
    }

    /**
     * Set the value of locationStatus
     *
     * @param locationStatus new value of locationStatus
     */
    public void setLocationStatus(BookLocationStatus locationStatus) {
        this.locationStatus = locationStatus;
    }

    /**
     * Get the value of progressStatus
     *
     * @return the value of progressStatus
     */
    public BookProgressStatus getProgressStatus() {
        return this.progressStatus;
    }

    /**
     * Set the value of progressStatus
     *
     * @param progressStatus new value of progressStatus
     */
    public void setProgressStatus(BookProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    /**
     * Get the value of comment
     *
     * @return the value of comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Set the value of comment
     *
     * @param comment new value of comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}