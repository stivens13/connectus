package com.sourcey.connectus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        Intent intent = getIntent();

        int pos = intent.getExtras().getInt("Position");


//        ContactAdapter adapter = new ContactAdapter(this);

        ImageView profileImg = findViewById(R.id.imgProfileDetail);
        TextView txtProfileName = findViewById(R.id.txtProfileName);
        TextView txtProfilePhone = findViewById(R.id.txtProfilePhone);
//        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtEmailHolder = findViewById(R.id.txtEmailHolder);

        Contact contact = (Contact) MainActivity.contactAdapter.getItem(pos);
        profileImg.setImageResource( contact.image );
        txtProfileName.setText(contact.name);
        txtProfilePhone.setText(MainActivity.format_phone(contact.phone));
        if(contact.email != null && !contact.email.equals("")) {
            txtEmailHolder.setText(contact.email);
        }
        else
            txtEmailHolder.setText("no email(");

    }
}
