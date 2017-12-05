package com.sourcey.connectus;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by stivens on 10/22/17.
 */

public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Contact> mDataSource;
    String res;

    private final String TAG = "ContactAdapter";

//    final ArrayList<Contact> contactList = Contact.getContactsFromFile("contacts.json", this);

    private static final HashMap<String, Integer> LABEL_COLORS = new HashMap<String, Integer>() {{
        put("Friend", R.color.colorFriend);
        put("Work", R.color.colorWork);
        put("Family", R.color.colorFamily);
        put("Not friend", R.color.colorNotFriend);
        put("Unsorted", R.color.colorUnsorted);
    }};

    public ContactAdapter(Context context) {
        mContext = context;
//        mDataSource = Contact.getContactsFromFile("contacts.json", context);
//        mDataSource =
        sendRequest(MainActivity.generateStringConnections(MainActivity.userPhoneNumber));

    }

    public void ContactAdapterHelper() {
        JSONObject req = MainActivity.stringToJSON(res);
        mDataSource = Contact.getContactsFromServer(req, mContext);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addNewContact(Contact contact) {
        if(contact != null) {
            mDataSource.add(0, contact);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item_contact, parent, false);

            // 3
            holder = new ViewHolder();
            holder.contactImage =(ImageView) convertView.findViewById(R.id.contact_list_thumbnail);
            holder.contactName = (TextView) convertView.findViewById(R.id.contact_list_name);
            holder.contactPhone =(TextView) convertView.findViewById(R.id.contact_list_phone);
            holder.contactEmail =(TextView) convertView.findViewById(R.id.contact_list_email);
            holder.contactType = (TextView) convertView.findViewById(R.id.contact_list_type);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = (Contact) getItem(position);


        holder.contactImage.setImageResource(contact.image);
        holder.contactName.setText(contact.name);
        holder.contactPhone.setText(MainActivity.format_phone(contact.phone));
        holder.contactEmail.setText(contact.email);
        holder.contactType.setText(contact.type);

        RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.rl);


        // && contact.fb != null) { //!contact.fb.isEmpty()
        int counter = 0;
        if( contact.fb != null ) {
            ImageView fb = new ImageView(mContext);
            fb.setImageResource(R.drawable.fb);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_END, R.id.contact_list_type);
            fb.setLayoutParams(lp);
            rl.addView(fb, counter);
            counter ++;
        }

        holder.contactType.setTextColor(ContextCompat.getColor(mContext, LABEL_COLORS.get(contact.type)));

        return convertView;
    }

    private static class ViewHolder {
        public ImageView contactImage;
        public ImageView fb;
        public ImageView inst;
        public ImageView snap;
        public ImageView linked;
        public TextView contactName;
        public TextView contactPhone;
        public TextView contactEmail;
        public TextView contactType;
    }

    private void sendRequest(String request) {

        Log.d(TAG, " sendRequest()");

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        res = response;
                        ContactAdapterHelper();
                        Toast.makeText(mContext, res, Toast.LENGTH_LONG).show();
                        Log.v(TAG, "The response is" + response);
//                        status = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.v(TAG, "Try error: " + error.getLocalizedMessage());
                }
                catch (Exception e) {
                    Log.v(TAG, "Catch error" + e.getMessage());
                }
            }
        });
        queue.add(stringRequest);

    }

}