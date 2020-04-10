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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Alexander Colen
 */
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    
    private String isbn;
    private String title;
    private String author;
    private int pages;
    private String image;


    /**
     * Default constructor.
     */
    public Book() {
    }

    /**
     * Full constructor
     * @param isbn The ISBN number of the Book.
     * @param title The title of the Book.
     * @param author The author of the Book.
     * @param pages The amount of pages that the Book has.
     * @param image The cover image URL of the Book.
     */
    public Book(String isbn, String title, String author, int pages, String image) {
        super();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.image = image;
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
     * Get the value of isbn
     *
     * @return the value of isbn
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * Set the value of isbn
     *
     * @param isbn new value of isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the value of author
     *
     * @return the value of author
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Set the value of author
     *
     * @param author new value of author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the value of pages
     *
     * @return the value of pages
     */
    public int getPages() {
        return this.pages;
    }

    /**
     * Set the value of pages
     *
     * @param pages new value of pages
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * Get the value of image
     *
     * @return the value of image
     */
    public String getImage() {
        return this.image;
    }

    /**
     * Set the value of image
     *
     * @param image new value of image
     */
    public void setImage(String image) {
        this.image = image;
    }
}