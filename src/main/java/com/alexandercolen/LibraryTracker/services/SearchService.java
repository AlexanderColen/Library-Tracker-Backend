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
package com.alexandercolen.LibraryTracker.services;

import com.alexandercolen.LibraryTracker.models.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Colen
 */
@Service
public class SearchService {
    private static final Logger LOG = Logger.getLogger(SearchService.class.getName());
    private static final String GOOGLE_BOOKS_API = "https://www.googleapis.com/books/v1/volumes?q=";
    
    private final OkHttpClient client = new OkHttpClient();
    
    public Iterable<Book> searchForBook(String criteria) {
        List<Book> foundBooks = new ArrayList<>();
        Request request = new Request.Builder()
            .url(GOOGLE_BOOKS_API + criteria)
            .build();
 
        Call call = this.client.newCall(request);
        
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                
                // Start parsing the JSON object to fetch our desired info.
                JSONObject json = new JSONObject(response.body().string());
                if (json.getInt("totalItems") > 0) {
                    try {
                        JSONArray foundItems = json.getJSONArray("items");
                        foundItems.forEach(_item -> {
                            JSONObject itemJson = (JSONObject) _item;
                            JSONObject itemInfo = itemJson.getJSONObject("volumeInfo");
                            if (itemInfo.getString("printType").equals("BOOK")) {
                                Book b = new Book();
                                
                                // Set ISBN
                                if (itemInfo.has("industryIdentifiers")) {
                                    JSONArray isbnArray = itemInfo.getJSONArray("industryIdentifiers");
                                    isbnArray.forEach(_isbn -> {
                                        JSONObject isbnJson = (JSONObject) _isbn;
                                        if (isbnJson.getString("type").contains("ISBN")) {
                                            b.setIsbn(isbnJson.getString("identifier"));
                                        }
                                    });
                                }

                                // Set Title
                                if (itemInfo.has("title")) {
                                    b.setTitle(itemInfo.getString("title"));
                                }

                                // Set Author
                                if (itemInfo.has("authors")) {
                                    JSONArray authorArray = itemInfo.getJSONArray("authors");
                                    b.setAuthor((String) authorArray.get(0));
                                }

                                // Set Pages
                                if (itemInfo.has("pageCount")) {
                                    b.setPages(itemInfo.getInt("pageCount"));
                                }

                                // Set Cover Image
                                if (itemInfo.has("imageLinks")) {
                                    JSONObject imageObj = itemInfo.getJSONObject("imageLinks");
                                    b.setImage(imageObj.getString("thumbnail"));
                                }

                                foundBooks.add(b);
                            }
                        });
                    } catch (JSONException ex) {
                        LOG.log(Level.SEVERE, ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
        
        return foundBooks;
    }
}
