package com.example.it_capstone_archive;

import android.content.Context;
import android.util.Log;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBAtlasHelper {
    private static final String TAG = "MongoDBAtlasHelper";
    private static final String MONGODB_URI = "mongodb+srv://2201104172:12345@cluster0.9qyv7.mongodb.net/auth-db?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "auth-db";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    // Initialize MongoDB connection
    public static void init(Context context) {
        try {
            MongoClientURI uri = new MongoClientURI(MONGODB_URI);
            mongoClient = MongoClients.create(uri.toString());
            mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
            Log.d(TAG, "Connected to MongoDB Atlas");
        } catch (Exception e) {
            Log.e(TAG, "Error connecting to MongoDB Atlas: " + e.getMessage());
        }
    }

    // Get a collection from the database
    public static MongoCollection<Document> getCollection(String collectionName) {
        if (mongoDatabase == null) {
            Log.e(TAG, "Database not initialized");
            return null;
        }
        return mongoDatabase.getCollection(collectionName);
    }

    // Close the MongoDB connection
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            Log.d(TAG, "MongoDB connection closed");
        }
    }
}