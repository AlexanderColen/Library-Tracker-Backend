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

import com.alexandercolen.library.models.User;
import com.alexandercolen.library.models.UserBook;
import com.alexandercolen.library.models.dtos.UserBookDTO;
import com.alexandercolen.library.services.CustomUserDetailsService;
import com.alexandercolen.library.services.UserBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    CustomUserDetailsService userService;
    
    @Autowired
    UserBookService userBookService;

    @ApiOperation(value = "Fetches all UserBooks.")
    @GetMapping()
    public Iterable<UserBook> getUserBooks() {
        return this.userBookService.getUserBooks();
    }

    @ApiOperation(value = "Fetche a specific UserBook.")
    @GetMapping(value = "/{id}")
    public UserBook getSpecificUserBook(@PathVariable("id") String id) {
        return this.userBookService.getSpecificUserBook(id);
    }
    
    @ApiOperation(value = "Post a UserBook.")
    @PostMapping()
    public UserBook postUserBook(@RequestBody UserBookDTO userBookDTO) {
        return this.userBookService.createUserBook(userBookDTO);
    }
    
    @ApiOperation(value = "Delete a UserBook.")
    @DeleteMapping(value = "/{id}")
    public boolean deleteUserBook(@PathVariable("id") String id) {
        return this.userBookService.deleteUserBook(id);
    }
    
    @ApiOperation(value = "Edit a UserBook.")
    @PutMapping(value = "/{id}")
    public UserBook putUserBook(@PathVariable("id") String id, @RequestBody UserBookDTO userBookDTO) {
        return this.userBookService.editUserBook(id, userBookDTO);
    }
    
    @ApiOperation(value = "Fetch all UserBooks for a specific User.")
    @GetMapping(value="/user/{userId}")
    public Iterable<UserBook> getUserBooksForUser(@PathVariable("userId") String userId) {
        return this.userBookService.getUserBooksForUser(userId);
    }
    
    @ApiOperation(value = "Fetch statistics for a specific User.")
    @GetMapping(value="/user/{username}/statistics")
    public ResponseEntity getStatisticsForUser(@PathVariable("username") String username) {
        User foundUser = this.userService.findUserByUsername(username);
        
        if (foundUser == null) {
            return new ResponseEntity<>("No user found with this username!", HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok(this.userBookService.getUserBookStatisticsForUser(foundUser.getId()));
    }
}