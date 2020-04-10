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
public class StringBodyTest {
    private static final Logger LOG = Logger.getLogger(StringBodyTest.class.getName());
    private StringBody stringBody;
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting StringBody body Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished StringBody body Testing.");
    }
    
    @BeforeEach
    public void setUp() {
        this.stringBody = new StringBody();
    }

    @Test
    public void testContentProperties() {
        LOG.log(Level.INFO, "Testing Content properties...");
        
        assertEquals(null, this.stringBody.getContent());
        
        String content = "This is content";
        this.stringBody.setContent(content);
        assertEquals(content, this.stringBody.getContent());
        
        content = "";
        this.stringBody.setContent(content);
        assertEquals(content, this.stringBody.getContent());
     
        this.stringBody.setContent(null);
        assertEquals(null, this.stringBody.getContent());
        
        LOG.log(Level.INFO, "Finished testing Content properties.");
    }
}