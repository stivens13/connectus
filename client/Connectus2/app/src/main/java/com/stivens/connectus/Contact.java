package com.stivens.connectus;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by stivens on 10/22/17.
 */

public class Contact {

    public String name;
    public String phone;
    public String email;
    public String Facebook;
    public String Instagram;

    public static ArrayList<Contact> getContactsFromFile(String filename, Context context) {
        final ArrayList<Contact> contactsList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            // Get Contact objects from data
            for (int i = 0; i < recipes.length(); i++) {
                Contact recipe = new Contact();

                recipe.name = recipes.getJSONObject(i).getString("name");
                recipe.phone = recipes.getJSONObject(i).getString("phone");
                recipe.email = recipes.getJSONObject(i).getString("email");
//                recipe.Facebook = recipes.getJSONObject(i).getString("url");
//                recipe.Instagram = recipes.getJSONObject(i).getString("dietLabel");

                contactsList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contactsList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
