package com.sourcey.materiallogindemo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

//import android.support.v7.app.ActionBarActivity;


public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private Dialog mDialog;
    private Button btnSendFind;
    private TextView mTextView;
    private TextView num;
    private EditText edtNum;
    private String register = "/createprofile";
    private String editprofile = "/editprofile";
    String port = "8080";
    String url = "ec2-34-210-242-157.us-west-2.compute.amazonaws.com:" + port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        final ArrayList<Contact> contactList = Contact.getContactsFromFile("contacts.json", this);

        ContactAdapter adapter = new ContactAdapter(this, contactList);

        Log.v("contactList", contactList.toString());

        mListView = findViewById(R.id.contact_list_view);


        mListView.setAdapter(adapter);

        for(Contact user: contactList){
            registerUser(user);
            Log.v("user", user.phone);
        }

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

        if(user.phone == null || user.phone.equals(""))
            return;

        sendRequest(url + register + "?" + "id=" + user.phone);

        completeRegiser(user);

    }

    private void completeRegiser(Contact user) {


        if( user.email != null ) {
            sendRequest(generate_string(user.phone, "email", user.email));
            Log.v("input", generate_string(user.phone, "email", user.email));
        }

        if( user.fb != null ) {
            sendRequest(generate_string(user.phone, "fb", user.fb));
            Log.v("input", generate_string(user.phone, "fb", user.fb));
        }

        if( user.inst != null ) {
            sendRequest(generate_string(user.phone, "inst", user.inst));
            Log.v("input", generate_string(user.phone, "inst", user.inst));
        }

        if( user.snap != null ) {
            sendRequest(generate_string(user.phone, "snap", user.snap));
            Log.v("input", generate_string(user.phone, "snap", user.snap));
        }
    }

    private String generate_string(String id, String type, String data) {

        return String.format("%s%s?id=%s&name=%s&data=%s ", url, editprofile, id, type, data);

    }

    private void sendRequest(String request) {

        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.v("response", error.getLocalizedMessage());
                }
                catch (Exception e) {
                    Log.v("error", e.getMessage());
                }
            }
        });
//// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
