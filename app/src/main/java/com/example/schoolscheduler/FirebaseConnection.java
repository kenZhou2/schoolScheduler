package com.example.schoolscheduler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
    FirebaseConnection should be used to store any data within Firebase's JSON object store.
    For now this is only used for the Courses added to the application but due to its scalability,
    Firebase should be used for all future database needs.
 */
public class FirebaseConnection {
    public final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference ref;

    public FirebaseConnection(String subset) {
        // Initial reference to the table we want to access.
        ref = database.getReference(subset);
    }
}
