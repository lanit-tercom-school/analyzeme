package com.analyzeme.repository.storage;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.util.*;

/**
 * IStorage implementation for using with Firebase DB
 */
public class FirebaseStorage implements IStorage {

    @Override
    public void connect(Object configuration) {
        FirebaseOptions options = (FirebaseOptions) configuration;
        FirebaseApp.initializeApp(options);
    }

    @Override
    public String saveEntry(String collection, Object entry) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference collectionReference = database.getReference(collection);
        DatabaseReference entryReference = collectionReference.push();
        entryReference.setValue(entry);
        return entryReference.getKey();
    }

    @Override
    public String saveEntry(String collection, Object entry, String entryName) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference collectionReference = database.getReference(collection);
        DatabaseReference entryReference = collectionReference.child(entryName);
        entryReference.setValue(entry);
        return entryReference.getKey();
    }

    @Override
    public <T> void getEntry(String collection, String entryName, Class<T> entryClass, IStorageResult<T> result) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference entryReference = database.getReference(collection.concat("/").concat(entryName));
        entryReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result.onComplete(dataSnapshot.getValue(entryClass));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                result.onError(databaseError.getMessage());
            }
        });
    }

    @Override
    public <T> void getAllFromCollection(String collection, Class<T> entryClass, IStorageResult<List<T>> result) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(collection);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> entries = new ArrayList<>();
                dataSnapshot.getChildren().iterator().forEachRemaining(x -> entries.add(x.getValue(entryClass)));
                result.onComplete(entries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                result.onError(databaseError.getMessage());
            }
        });
    }
}
