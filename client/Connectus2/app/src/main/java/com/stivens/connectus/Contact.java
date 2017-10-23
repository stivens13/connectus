package com.stivens.connectus;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by stivens on 10/22/17.
 */

public class Contact {

    public int image;
    public String name;
    public String phone;
    public String email;
    public String type;
    public String fb;
    public String inst;
    public String snap;
    public String linked;

    public static ArrayList<Contact> getContactsFromFile(String filename, Context context) {
        final ArrayList<Contact> contactsList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("contacts.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("users");

            if( recipes == null || json == null) {

                Toast.makeText(context, "json was not opened", Toast.LENGTH_LONG).show();

                return null;
            }

            // Get Contact objects from data
            for (int i = 0; i < recipes.length(); i++) {
                Contact recipe = new Contact();

                recipe.name = recipes.getJSONObject(i).getString("name");
                recipe.phone = recipes.getJSONObject(i).getString("phone");
                recipe.email = recipes.getJSONObject(i).getString("email");
                recipe.type = recipes.getJSONObject(i).getString("type");

                if(  recipes.getJSONObject(i).getString("sex").equals("male") ) {
                    recipe.image = R.drawable.avatar_default_male;
                }
                else {
                    recipe.image = R.drawable.avatar_default_female;
                }

                if( recipes.getJSONObject(i).getString("fb") != null && !recipes.getJSONObject(i).getString("fb").isEmpty() ) {
                    recipe.fb = recipes.getJSONObject(i).getString("fb");
                }

                if( recipes.getJSONObject(i).getString("inst") != null && !recipes.getJSONObject(i).getString("inst").isEmpty() ) {
                    recipe.inst = recipes.getJSONObject(i).getString("inst");
                }

                if( recipes.getJSONObject(i).getString("linked") != null && !recipes.getJSONObject(i).getString("linked").isEmpty() ) {
                    recipe.linked = recipes.getJSONObject(i).getString("linked");
                }

                if( recipes.getJSONObject(i).getString("snap") != null && !recipes.getJSONObject(i).getString("snap").isEmpty() ) {
                    recipe.snap = recipes.getJSONObject(i).getString("snap");
                }


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
