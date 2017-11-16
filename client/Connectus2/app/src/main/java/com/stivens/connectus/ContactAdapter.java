package com.stivens.connectus;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

//import android.widget.ImageView;

/**
 * Created by stivens on 10/22/17.
 */

public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Contact> mDataSource;

    private static final HashMap<String, Integer> LABEL_COLORS = new HashMap<String, Integer>() {{
        put("Friend", R.color.colorFriend);
        put("Work", R.color.colorWork);
        put("Family", R.color.colorFamily);
        put("Not friend", R.color.colorNotFriend);
        put("Unsorted", R.color.colorUnsorted);
//        put("Balanced", R.color.colorBalanced);
    }};

    public ContactAdapter(Context context, ArrayList<Contact> items) {
        mContext = context;
        mDataSource = items;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item_contact, parent, false);

            // 3
            holder = new ViewHolder();
            holder.contactImage = convertView.findViewById(R.id.contact_list_thumbnail);
            holder.contactName = convertView.findViewById(R.id.contact_list_name);
            holder.contactPhone = convertView.findViewById(R.id.contact_list_phone);
            holder.contactEmail = convertView.findViewById(R.id.contact_list_email);
            holder.contactType = convertView.findViewById(R.id.contact_list_type);

            convertView.setTag(holder);
        }

         else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = (Contact) getItem(position);


        holder.contactImage.setImageResource(contact.image);
        holder.contactName.setText(contact.name);
        holder.contactPhone.setText(contact.phone);
        holder.contactEmail.setText(contact.email);
        holder.contactType.setText(contact.type);

        RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.rl);



        // && contact.fb != null) { //!contact.fb.isEmpty()
//        if( contact.fb != null ) {
//            ImageView fb = new ImageView(mContext);
//            fb.setImageResource(R.drawable.fb);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            lp.addRule(RelativeLayout.ALIGN_END, R.id.contact_list_email);
//            fb.setLayoutParams(lp);
//            rl.addView(fb);
//
//        }

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


}
