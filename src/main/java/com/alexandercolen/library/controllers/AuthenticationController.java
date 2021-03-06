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

import com.alexandercolen.library.configurations.JwtTokenProvider;
import com.alexandercolen.library.models.User;
import com.alexandercolen.library.models.dtos.UserDTO;
import com.alexandercolen.library.repositories.UserRepository;
import com.alexandercolen.library.services.CustomUserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexander Colen
 */
@Api(value = "Authentication Controller")
@CrossOrigin(
    origins = "*",
    methods = { RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private CustomUserDetailsService userService;

    @ApiOperation(value = "Login to the application.")
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserDTO userDTO) {
        try {
            String username = userDTO.getUsername();
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userDTO.getPassword()));
            User foundUser = this.users.findByUsername(username);
            String token = this.jwtTokenProvider.createToken(username, foundUser.getRoles());
            Map<String, String> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("userId", foundUser.getId());
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Login failed. Nice try.", HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value = "Register to the application.")
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserDTO userDTO) {
        User userExists = this.userService.findUserByUsername(userDTO.getUsername());
        if (userExists != null) {
            return new ResponseEntity<>("This username has already been taken!", HttpStatus.CONFLICT);
        }
        this.userService.saveUser(new User(userDTO));
        Map<String, String> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }
    
    @ApiOperation(value = "Deletes a User")
    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        return this.userService.deleteUser(id);
    }
}