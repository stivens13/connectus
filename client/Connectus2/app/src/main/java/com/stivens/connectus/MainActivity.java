package com.stivens.connectus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Contact> contactList = Contact.getContactsFromFile("contacts.json", this);

        ContactAdapter adapter = new ContactAdapter(this, contactList);

        mListView = (ListView) findViewById(R.id.contact_list_view);

        mListView.setAdapter(adapter);

    }

//    private void loadContacts() {
//
//        Faker faker;
//        Contact contact;
//
//        for(int i = 0; i < 10; i += 1) {
//
//            faker = new Faker();
//
//            contact = new Contact();
//
//            contact.name = faker.name().fullName();
//            contact.phone = faker.phoneNumber().cellPhone();
//            contact.email = "john.doe@gmail.com";
//        }
//    }



}
