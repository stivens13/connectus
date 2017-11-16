package com.stivens.connectus;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private Dialog mDialog;
    private Button btnSendFind;
    private TextView mTextView;
    private TextView num;
    private EditText edtNum;
    private String register = 
    String url = "localhost:8080";
//    RequestQueue queue = Volley.newRequestQueue(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Contact> contactList = Contact.getContactsFromFile("contacts.json", this);

        ContactAdapter adapter = new ContactAdapter(this, contactList);

        mListView = (ListView) findViewById(R.id.contact_list_view);

        mListView.setAdapter(adapter);

        FloatingActionButton btnAddUser = (FloatingActionButton) findViewById(R.id.btnAddUser);


        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
//                View newView = getLayoutInflater().inflate(R.layout.add_user, null);
                addUserCallScreen();
            }
        });

    }

    protected void addUserCallScreen() {

        mDialog = new Dialog(MainActivity.this);
        mDialog.setContentView(R.layout.add_user);
        mDialog.setTitle("Add user");

        num = (TextView) mDialog.findViewById(R.id.txtNumber);
        edtNum = (EditText) mDialog.findViewById(R.id.edtxtNumber);
        btnSendFind = (Button) mDialog.findViewById(R.id.btnSendFind);
//        Toast.makeText(MainActivity.this, "Fab pressed",
//                Toast.LENGTH_SHORT).show();

//        num.setEnabled(true);
//        edtNum.setEnabled(true);
        btnSendFind.setEnabled(true);

        btnSendFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hi there", Toast.LENGTH_SHORT).show();
            }
        });

        mDialog.show();


    }

    protected void getUser(String id) {

    }
    protected void registerUser(Contact user) {

    RequestQueue queue = Volley.newRequestQueue(this);

    String request = url +
//
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

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
