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
package com.alexandercolen.LibraryTracker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Alexander Colen
 */
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String role;
    
    public String getId() {
            return id;
    }
    public void setId(String id) {
            this.id = id;
    }
    public String getRole() {
            return role;
    }
    public void setRole(String role) {
            this.role = role;
    }
}