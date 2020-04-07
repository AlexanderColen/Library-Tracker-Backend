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
package com.alexandercolen.LibraryTracker.controllers;

import com.alexandercolen.LibraryTracker.models.UserBook;
import com.alexandercolen.LibraryTracker.services.UserBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexander Colen
 */
@Api(value = "UserBook Controller")
@CrossOrigin(
        origins = { "*" },
        methods = { RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.DELETE,
                    RequestMethod.OPTIONS})
@RequestMapping("/api/userbooks")
@RestController
public class UserBookController {
    @Autowired
    UserBookService userBookService;

    @ApiOperation(value = "Fetches all UserBooks.")
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<UserBook> getUserBooks() {
        return this.userBookService.getUserBooks();
    }

    @ApiOperation(value = "Fetche a specific UserBook.")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public UserBook getSpecificUserBook(@PathVariable("id") String id) {
        return this.userBookService.getSpecificUserBook(id);
    }
    
    @ApiOperation(value = "Post a UserBook.")
    @RequestMapping(method = RequestMethod.POST)
    public UserBook postUserBook(@RequestBody UserBook userBook) {
        return this.userBookService.createUserBook(userBook);
    }
    
    @ApiOperation(value = "Delete a UserBook.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public boolean deleteUserBook(@PathVariable("id") String id) {
        return this.userBookService.deleteUserBook(id);
    }
    
    @ApiOperation(value = "Edit a UserBook.")
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public UserBook putUserBook(@PathVariable("id") String id, @RequestBody UserBook userBook) {
        return this.userBookService.editUserBook(id, userBook);
    }
    
    @ApiOperation(value = "Fetch all UserBooks for a specific user.")
    @RequestMapping(method = RequestMethod.GET, value="/user/{user_id}")
    public Iterable<UserBook> GetUserBooksForUser(@PathVariable("user_id") String user_id) {
        return this.userBookService.getUserBooksForUser(user_id);
    }
}