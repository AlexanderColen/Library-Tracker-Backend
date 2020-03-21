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

import com.alexandercolen.LibraryTracker.configurations.JwtTokenProvider;
import com.alexandercolen.LibraryTracker.models.User;
import com.alexandercolen.LibraryTracker.repositories.UserRepository;
import com.alexandercolen.LibraryTracker.services.CustomUserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import static org.springframework.http.ResponseEntity.ok;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
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
        origins = { "http://localhost:4200"},
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
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationBody data) {
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            User foundUser = this.users.findByUsername(username);
            String token = jwtTokenProvider.createToken(username, foundUser.getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("user_id", foundUser.getId());
            return ok(model);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Login failed. Nice try.", HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value = "Register to the application.")
    @SuppressWarnings("rawtypes")
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register(@RequestBody User user) {
        User userExists = userService.findUserByUsername(user.getUsername());
        if (userExists != null) {
            return new ResponseEntity<>("This username has already been taken!", HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }
}