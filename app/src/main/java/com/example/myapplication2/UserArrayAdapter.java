package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class UserArrayAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final ArrayList<User> users;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String name);
    }

    public UserArrayAdapter(Context context, ArrayList<User> users, OnItemClickListener listener) {
        super(context, R.layout.activity_user_details, users);

        this.context = context;
        this.users = users;
        this.listener = listener;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_user_details, parent, false);

        TextView nameTextView = rowView.findViewById(R.id.name_textview);
        TextView emailTextView = rowView.findViewById(R.id.email_textview);
        Button detailsButton = rowView.findViewById(R.id.details_button);

        final User user = users.get(position);
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(user.getName());
            }
        });

        return rowView;
    }
}

