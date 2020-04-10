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
public class RoleTest {
    private static final Logger LOG = Logger.getLogger(RoleTest.class.getName());
    private Role role;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting Role model Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished Role model Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.role = new Role();
    }
    
    @Test
    public void testIdProperties() {
        LOG.log(Level.INFO, "Testing Id properties...");
        
        assertEquals(null, this.role.getId());
        
        String id = "7ade1981-8784-4b34-a3ab-7f2e9e0473e4";
        this.role.setId(id);
        assertEquals(id, this.role.getId());
        
        id = "";
        this.role.setId(id);
        assertEquals(id, this.role.getId());
        
        this.role.setId(null);
        assertEquals(null, this.role.getId());
        
        LOG.log(Level.INFO, "Finished testing Id properties.");
    }
    
    @Test
    public void testRoleNameProperties() {
        LOG.log(Level.INFO, "Testing Role properties...");
        
        assertEquals(null, this.role.getRoleName());
        
        String id = "ADMIN";
        this.role.setRoleName(id);
        assertEquals(id, this.role.getRoleName());
        
        id = "";
        this.role.setRoleName(id);
        assertEquals(id, this.role.getRoleName());
        
        this.role.setRoleName(null);
        assertEquals(null, this.role.getRoleName());
        
        LOG.log(Level.INFO, "Finished testing Role properties.");
    }
}
