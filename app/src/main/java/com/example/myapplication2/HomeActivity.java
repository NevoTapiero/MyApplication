package com.example.myapplication2;

import com.example.myapplication2.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, UserArrayAdapter.OnItemClickListener {

    private TextView welcomeTextView;

    // Declare an instance variable for FirebaseDatabase
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the UserArrayAdapter using your custom Adapter
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("John Doe", "john.doe@example.com", "nevotapiero12345", 16, "john_doe_profile_image"));
        userList.add(new User("Jane Smith", "jane.smith@example.com", "nevotapiero12345", 16, "john_doe_profile_image"));
        userList.add(new User("Bob Johnson", "bob.johnson@example.com", "nevotapiero12345", 16, "john_doe_profile_image"));
        UserArrayAdapter adapter = new UserArrayAdapter(this, userList, this);

        // Initialize the ListView
        ListView userListView = findViewById(R.id.user_listview);

        // Set the ListView adapter to the one you made
        userListView.setAdapter(adapter);

        // Set the OnItemClickListener to the ListView
        userListView.setOnItemClickListener(this);

        welcomeTextView = findViewById(R.id.welcome_textview);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        welcomeTextView.setText("Welcome, " + email + "!");

        // Get a reference to the "users" child node
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        // Add a value event listener to the "users" node to listen for changes
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // Loop through all the child nodes of the "users" node
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Get the User object for this child node
                    User user = userSnapshot.getValue(User.class);

                    // Do something with the user object (e.g. add it to a list, display it in a UI, etc.)
                    Log.d("HomeActivity", "User: " + user.getName() + " (" + user.getEmail() + ")");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HomeActivity", "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    public void onItemClick(String userId) {
        // Do something with the clicked user ID
        Log.d("HomeActivity", "User clicked with ID: " + userId);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get the

    }

    private void writeNewUser(User newUser) {
        // Get a reference to the "users" child node
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        // Get a new unique key for the new user
        String userId = usersRef.push().getKey();

        // Set the value of the new user object in the database
        usersRef.child(userId).setValue(newUser);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_signout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}