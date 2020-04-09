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
import com.alexandercolen.library.services.BookService;
import com.alexandercolen.library.services.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Alexander Colen
 */
@Api(value = "Book Controller")
@CrossOrigin(
        origins = { "*" },
        methods = { RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.DELETE,
                    RequestMethod.OPTIONS})
@RequestMapping("/api/books")
@RestController
public class BookController {
    @Autowired
    BookService bookService;
    
    @Autowired
    SearchService searchService;

    @ApiOperation(value = "Fetches all Books.")
    @GetMapping()
    public Iterable<Book> getBooks() {
        return this.bookService.getBooks();
    }

    @ApiOperation(value = "Fetche a specific Book.")
    @GetMapping(value = "/{id}")
    public Book getSpecificBook(@PathVariable("id") String id) {
        return this.bookService.getSpecificBook(id);
    }
    
    @ApiOperation(value = "Post a Book.")
    @PostMapping()
    public Book postBook(@RequestBody Book book) {
        return this.bookService.createBook(book);
    }
    
    @ApiOperation(value = "Delete a Book.")
    @DeleteMapping(value = "/{id}")
    public boolean deleteBook(@PathVariable("id") String id) {
        return this.bookService.deleteBook(id);
    }
    
    @ApiOperation(value = "Edit a Book.")
    @PutMapping(value = "/{id}")
    public Book putBook(@PathVariable("id") String id, @RequestBody Book book) {
        return this.bookService.editBook(id, book);
    }
    
    @ApiOperation(value = "Find a new Book.")
    @PostMapping(value = "/find")
    public Iterable<Book> searchForBook(@RequestParam(value = "criteria") String criteria) {
        return this.searchService.searchForBook(criteria);
    }
}