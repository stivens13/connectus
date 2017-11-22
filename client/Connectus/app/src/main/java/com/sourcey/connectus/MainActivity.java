package com.sourcey.connectus;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.Iterator;

//import android.support.v7.app.ActionBarActivity;


public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private Dialog mDialog;
    private Button btnSendFind;
    private TextView mTextView;
    private TextView num;
    private EditText edtNum;

    public static String register = "/createprofile";
    public static String editprofile = "/editprofile";
    public static String createlogin = "/createlogin";
    public static String checklogin = "/checklogin";
    private String TAG = "MainActivity";
    public static String port = "8080";
    public static String url = "http://ec2-34-210-242-157.us-west-2.compute.amazonaws.com:" + port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Main started");

        initiateAdapter();
    }


    protected void initiateAdapter() {

        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);



        ContactAdapter adapter = new ContactAdapter(this);


        mListView = findViewById(R.id.contact_list_view);


        mListView.setAdapter(adapter);


        FloatingActionButton btnAddUser = (FloatingActionButton) findViewById(R.id.btnAddUser);


        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserCallScreen();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent newIntent = new Intent(getApplicationContext(), ProfileDetail.class);
                newIntent.putExtra("Position", i);
                startActivity(newIntent);
            }
        });
    }



    protected void addUserCallScreen() {

        Log.d(TAG, "Main addUserCallScreen()");

        mDialog = new Dialog(MainActivity.this);
        mDialog.setContentView(R.layout.add_user_1);
        mDialog.setTitle("Add user");

        num = (TextView) mDialog.findViewById(R.id.txtNumber);
        edtNum = (EditText) mDialog.findViewById(R.id.edtxtNumber);
        btnSendFind = (Button) mDialog.findViewById(R.id.btnSendFind);
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

        Log.d(TAG, "Main registerUser()");

        RequestQueue queue = Volley.newRequestQueue(this);

        if(user.phone == null || user.phone.equals(""))
            return;

        sendRequest(url + register + "?" + "id=" + user.phone);

        completeRegiser(user);

    }

    private void completeRegiser(Contact user) {

        Log.d(TAG, "Main completeRegister()");


        if( user.email != null ) {
            sendRequest(generate_string(user.phone, "email", user.email));
            Log.v("input", generate_string(user.phone, "email", user.email));
        }

        Iterator it = user.data.entrySet().iterator();
        for (String key : user.data.keySet()) {
            String entry = user.data.get(key);
            if(entry != null && !entry.equals("")){
                String link = generate_string(user.phone, key, entry);
                sendRequest(link);
                Log.v("input", link);
            }
        }
    }

    private String generate_string(String id, String type, String data) {

        return String.format("%s%s?id=%s&name=%s&data=%s ", url, editprofile, id, type, data);

    }

    private void sendRequest(String request) {

        Log.d(TAG, " sendRequest()");

        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.v(TAG, error.getLocalizedMessage());
                }
                catch (Exception e) {
                    Log.v(TAG, e.getMessage());
                }
            }
        });
        queue.add(stringRequest);

    }

    public static String format_phone(String phone) {
        phone = phone.substring(0, 3) + " " + phone.substring(3, 6) + " " + phone.substring(6);
        return phone;
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
