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
public class UserTest {
    private static final Logger LOG = Logger.getLogger(UserTest.class.getName());
    private User user;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting User model Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished User model Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.user = new User();
    }
    
    @Test
    public void testIdProperties() {
        LOG.log(Level.INFO, "Testing Id properties...");
        
        assertEquals(null, this.user.getId());
        
        String id = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.user.setId(id);
        assertEquals(id, this.user.getId());
        
        id = "";
        this.user.setId(id);
        assertEquals(id, this.user.getId());
        
        this.user.setId(null);
        assertEquals(null, this.user.getId());
        
        LOG.log(Level.INFO, "Finished testing Id properties.");
    }
    
    @Test
    public void testUsernameProperties() {
        LOG.log(Level.INFO, "Testing Username properties...");
        
        assertEquals(null, this.user.getUsername());
        
        String username = "LibraryUser12";
        this.user.setUsername(username);
        assertEquals(username, this.user.getUsername());
        
        username = "";
        this.user.setUsername(username);
        assertEquals(username, this.user.getUsername());
        
        this.user.setUsername(null);
        assertEquals(null, this.user.getUsername());
        
        LOG.log(Level.INFO, "Finished testing Username properties.");
    }
    
    @Test
    public void testPasswordProperties() {
        LOG.log(Level.INFO, "Testing Password properties...");
        
        assertEquals(null, this.user.getPassword());
        
        String password = "NotAPassword";
        this.user.setPassword(password);
        assertEquals(password, this.user.getPassword());
        
        password = "";
        this.user.setPassword(password);
        assertEquals(password, this.user.getPassword());
        
        this.user.setPassword(null);
        assertEquals(null, this.user.getPassword());
        
        LOG.log(Level.INFO, "Finished testing Password properties.");
    }
    
    @Test
    public void testEnabledProperties() {
        LOG.log(Level.INFO, "Testing Enabled properties...");
        
        assertEquals(false, this.user.isEnabled());
        
        boolean enabled = true;
        this.user.setEnabled(enabled);
        assertEquals(enabled, this.user.isEnabled());
        
        enabled = false;
        this.user.setEnabled(enabled);
        assertEquals(enabled, this.user.isEnabled());
        
        LOG.log(Level.INFO, "Finished testing Enabled properties.");
    }
    
    @Test
    public void testRolesProperties() {
        LOG.log(Level.INFO, "Testing Roles properties...");
        
        assertEquals(null, this.user.getRoles());
        
        Set<Role> roles = null;
        this.user.setRoles(roles);
        assertEquals(roles, this.user.getRoles());
        
        roles = new HashSet<>();
        this.user.setRoles(roles);
        assertEquals(roles, this.user.getRoles());
        
        roles.add(new Role());
        this.user.setRoles(roles);
        assertEquals(roles, this.user.getRoles());
        
        Role role = new Role();
        role.setId("123");
        role.setRoleName("ADMIN");
        roles.add(role);
        this.user.setRoles(roles);
        assertEquals(roles, this.user.getRoles());
        
        roles.remove(role);
        this.user.setRoles(roles);
        assertEquals(roles, this.user.getRoles());
        
        this.user.setRoles(null);
        assertEquals(null, this.user.getRoles());
        
        LOG.log(Level.INFO, "Finished testing Roles properties.");
    }
}
