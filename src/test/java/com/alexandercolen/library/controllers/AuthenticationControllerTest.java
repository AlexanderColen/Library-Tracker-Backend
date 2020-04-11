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

import com.alexandercolen.library.models.dtos.UserDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author Alexander Colen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class AuthenticationControllerTest extends AbstractControllerTest {
    private static final Logger LOG = Logger.getLogger(AuthenticationControllerTest.class.getName());
    
    @BeforeAll
    public static void setUpClass() {
        LOG.log(Level.INFO, "Starting AuthenticationController Testing...");
    }
    
    @AfterAll
    public static void tearDownClass() {
        LOG.log(Level.INFO, "Finished AuthenticationController Testing.");
    }
    
    @Test
    public void testLogin() throws Exception {
        LOG.log(Level.INFO, "Testing POST /api/auth/login...");
        
        // New username.
        String uri = "/api/auth/register";
        String username = "JuniorTester";
        UserDTO userDTOInput = this.createUserDTO(username);
        String inputJson = super.mapToJson(userDTOInput);
       
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Map<Object, Object> output = super.mapFromJson(content, HashMap.class);
        assertNotNull(output);
        assertEquals("User registered successfully", output.get("message"));
        
        // Correct login.
        uri = "/api/auth/login";
        
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        output = super.mapFromJson(content, HashMap.class);
        assertNotNull(output);
        assertNotNull(output.get("token"));
        assertNotNull(output.get("userId"));
        assertEquals(username, output.get("username"));
        
        // Faulty login.
        userDTOInput = this.createUserDTO("FakeTester");
        inputJson = super.mapToJson(userDTOInput);
        
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(401, status);

        content = mvcResult.getResponse().getContentAsString();
        assertEquals("Login failed. Nice try.", content);
        assertNotNull(content);
        
        LOG.log(Level.INFO, "Finished testing POST /api/auth/login.");
    }
    
    @Test
    public void testRegister() throws Exception {
        LOG.log(Level.INFO, "Testing POST /api/auth/register...");
        
        // New username.
        String uri = "/api/auth/register";
        UserDTO userDTOInput = this.createUserDTO("SomeUsername");
        String inputJson = super.mapToJson(userDTOInput);
       
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Map<Object, Object> output = super.mapFromJson(content, HashMap.class);
        assertNotNull(output);
        assertEquals("User registered successfully", output.get("message"));
        
        // Duplicate username.
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson))
            .andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(409, status);

        content = mvcResult.getResponse().getContentAsString();
        assertEquals("This username has already been taken!", content);
        assertNotNull(content);
        
        LOG.log(Level.INFO, "Finished testing POST /api/auth/register.");
    }
}