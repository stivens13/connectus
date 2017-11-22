package com.sourcey.connectus;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stivens on 10/22/17.
 */

public class Contact {

    public int image;
    public String name;
    public String phone;
    public String email;
    public String type;
    public String sex;

//    public String fb;
//    public String inst;
//    public String snap;
//    public String linked;

    public HashMap<String, String> data = new HashMap<String, String>() {{
        put("fb", null);
        put("inst", null);
        put("snap", null);
        put("linked", null);
    }};

    public static ArrayList<Contact> getContactsFromFile(String filename, Context context) {
        final ArrayList<Contact> contactsList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("contacts.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray users = json.getJSONArray("users");

            if( users == null || json == null) {

                Toast.makeText(context, "json was not opened", Toast.LENGTH_LONG).show();

                return null;
            }

            // Get Contact objects from data
            for (int i = 0; i < users.length(); i++) {
                Contact contact = new Contact();
                JSONObject user = users.getJSONObject(i);

                contact.name = user.getString("name");
                contact.phone = user.getString("phone");
                contact.email = user.getString("email");
                contact.type = user.getString("type");
                contact.sex = user.getString("sex");

//                contact.data.put("name", user.getString("name") );
//                contact.data.put("phone", user.getString("phone") );
//                contact.data.put("email", user.getString("email") );
//                contact.data.put("type", user.getString("type") );
//                contact.data.put("sex", user.getString("sex") );
//                contact.data.put("avatar", user.getString("avatar") );

                contact.data.put("fb", user.getString("fb") );
                contact.data.put("inst", user.getString("inst") );
                contact.data.put("linked", user.getString("linked") );
                contact.data.put("snap", user.getString("snap") );

                if(  users.getJSONObject(i).getString("sex").equals("male") ) {
                    contact.image = R.drawable.avatar_default_male;
                }
                else {
                    contact.image = R.drawable.avatar_default_female;
                }

//                if( users.getJSONObject(i).getString("fb") != null && !users.getJSONObject(i).getString("fb").isEmpty() ) {
//                    contact.fb = users.getJSONObject(i).getString("fb");
//                }
//
//                if( users.getJSONObject(i).getString("inst") != null && !users.getJSONObject(i).getString("inst").isEmpty() ) {
//                    contact.inst = users.getJSONObject(i).getString("inst");
//                }
//
//                if( users.getJSONObject(i).getString("linked") != null && !users.getJSONObject(i).getString("linked").isEmpty() ) {
//                    contact.linked = users.getJSONObject(i).getString("linked");
//                }
//
//                if( users.getJSONObject(i).getString("snap") != null && !users.getJSONObject(i).getString("snap").isEmpty() ) {
//                    contact.snap = users.getJSONObject(i).getString("snap");
//                }


                contactsList.add(contact);
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

