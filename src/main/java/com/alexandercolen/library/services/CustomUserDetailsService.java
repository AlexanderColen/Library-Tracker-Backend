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
package com.alexandercolen.library.services;

import com.alexandercolen.library.models.Role;
import com.alexandercolen.library.models.User;
import com.alexandercolen.library.repositories.RoleRepository;
import com.alexandercolen.library.repositories.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Colen
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = this.roleRepository.findByRoleName("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(new HashSet<>(roles));
        this.userRepository.save(user);
    }
    
    public boolean deleteUser(String id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = userOptional.orElse(null);
        
        if (user == null) {
            return false;
        }
        
        this.userRepository.deleteById(id);
        
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> 
            roles.add(new SimpleGrantedAuthority(role.getRoleName()))
        );

        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}