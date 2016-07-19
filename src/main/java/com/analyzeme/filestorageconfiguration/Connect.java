package com.analyzeme.filestorageconfiguration;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Кирилл Зубов on 7/7/2016.
 */
public class Connect {
    public void conectFirebase() throws FileNotFoundException {
        FirebaseConf firebaseConf = new FirebaseConf();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream(firebaseConf.getServiceAccount()))
                .setDatabaseUrl(firebaseConf.getDataBaseUrl())
                .build();

        FirebaseApp.initializeApp(options);
    }


    public void getDatabase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

    }

}
