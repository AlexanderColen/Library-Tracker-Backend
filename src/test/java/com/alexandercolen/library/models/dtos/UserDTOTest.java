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

import com.alexandercolen.library.models.Role;
import com.alexandercolen.library.models.User;
import java.util.HashSet;
import java.util.Set;
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
public class UserDTOTest {
    private static final Logger LOG = Logger.getLogger(UserDTOTest.class.getName());
    private UserDTO userDTO;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting UserDTO model Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished UserDTO model Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.userDTO = new UserDTO();
    }
    
    @Test
    public void testUsernameProperties() {
        LOG.log(Level.INFO, "Testing Username properties...");
        
        assertEquals(null, this.userDTO.getUsername());
        
        String username = "LibraryUser12";
        this.userDTO.setUsername(username);
        assertEquals(username, this.userDTO.getUsername());
        
        username = "";
        this.userDTO.setUsername(username);
        assertEquals(username, this.userDTO.getUsername());
        
        this.userDTO.setUsername(null);
        assertEquals(null, this.userDTO.getUsername());
        
        LOG.log(Level.INFO, "Finished testing Username properties.");
    }
    
    @Test
    public void testPasswordProperties() {
        LOG.log(Level.INFO, "Testing Password properties...");
        
        assertEquals(null, this.userDTO.getPassword());
        
        String password = "NotAPassword";
        this.userDTO.setPassword(password);
        assertEquals(password, this.userDTO.getPassword());
        
        password = "";
        this.userDTO.setPassword(password);
        assertEquals(password, this.userDTO.getPassword());
        
        this.userDTO.setPassword(null);
        assertEquals(null, this.userDTO.getPassword());
        
        LOG.log(Level.INFO, "Finished testing Password properties.");
    }
}