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
package com.alexandercolen.library.controllers.bodies;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Alexander Colen
 */
@SpringBootTest
public class AuthenticationBodyTest {
    private static final Logger LOG = Logger.getLogger(AuthenticationBodyTest.class.getName());
    private AuthenticationBody authenticationBody;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting AuthenticationBody body Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished AuthenticationBody body Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.authenticationBody = new AuthenticationBody();
    }

    @Test
    public void testUsernameProperties() {
        LOG.log(Level.INFO, "Testing Username properties...");
        
        assertEquals(null, this.authenticationBody.getUsername());
        
        String username = "TestUser123";
        this.authenticationBody.setUsername(username);
        assertEquals(username, this.authenticationBody.getUsername());
        
        username = "";
        this.authenticationBody.setUsername(username);
        assertEquals(username, this.authenticationBody.getUsername());
     
        this.authenticationBody.setUsername(null);
        assertEquals(null, this.authenticationBody.getUsername());
        
        LOG.log(Level.INFO, "Finished testing Username properties.");
    }

    @Test
    public void testPasswordProperties() {
        LOG.log(Level.INFO, "Testing Password properties...");
        
        assertEquals(null, this.authenticationBody.getPassword());
        
        String password = "NotAPassword";
        this.authenticationBody.setPassword(password);
        assertEquals(password, this.authenticationBody.getPassword());
        
        password = "";
        this.authenticationBody.setPassword(password);
        assertEquals(password, this.authenticationBody.getPassword());
     
        this.authenticationBody.setPassword(null);
        assertEquals(null, this.authenticationBody.getPassword());
        
        LOG.log(Level.INFO, "Finished testing Password properties.");
    }
}