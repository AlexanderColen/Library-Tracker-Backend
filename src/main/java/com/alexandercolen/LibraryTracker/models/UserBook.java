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
package com.alexandercolen.LibraryTracker.models;

import com.alexandercolen.LibraryTracker.models.enums.BookLocationStatus;
import com.alexandercolen.LibraryTracker.models.enums.BookProgressStatus;
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

    private String user_id;
    @DBRef
    private Book book;
    private BookLocationStatus location_status;
    private BookProgressStatus progress_status;

    /**
     * Default constructor.
     */
    public UserBook() {
        this.location_status = BookLocationStatus.OWNED;
        this.progress_status = BookProgressStatus.UNREAD;
    }    
    
    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return id;
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
     * Get the value of user_id
     *
     * @return the value of user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Set the value of user_id
     *
     * @param user_id new value of user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Get the value of book
     *
     * @return the value of book
     */
    public Book getBook() {
        return book;
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
     * Get the value of location_status
     *
     * @return the value of location_status
     */
    public BookLocationStatus getLocation_status() {
        return location_status;
    }

    /**
     * Set the value of location_status
     *
     * @param location_status new value of location_status
     */
    public void setLocation_status(BookLocationStatus location_status) {
        this.location_status = location_status;
    }

    /**
     * Get the value of progress_status
     *
     * @return the value of progress_status
     */
    public BookProgressStatus getProgress_status() {
        return progress_status;
    }

    /**
     * Set the value of progress_status
     *
     * @param progress_status new value of progress_status
     */
    public void setProgress_status(BookProgressStatus progress_status) {
        this.progress_status = progress_status;
    }
}