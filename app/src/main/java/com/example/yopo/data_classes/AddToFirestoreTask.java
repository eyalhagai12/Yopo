package com.example.yopo.data_classes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import com.google.gson.Gson;


public class AddToFirestoreTask extends AsyncTask<Void, Void, Void> {

    private static final String FUNCTION_URL = "https://us-central1-yopo-6aaec.cloudfunctions.net/addToFirestore";

    private String data;  // Data to be added to Firestore
    private String collectionPath;

    public AddToFirestoreTask(String data) {
        this.data = data;
    }

    public AddToFirestoreTask(HashMap<String, Object> data, String collectionPath) {
        Gson gson = new Gson();
        this.data = gson.toJson(data);
        this.collectionPath = collectionPath;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(FUNCTION_URL + "?collection=" + collectionPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes("UTF-8"));
            Log.d("Firestore", "Sent message to the Server");
            outputStream.close();

            connection.getResponseCode();  // Check for errors
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}