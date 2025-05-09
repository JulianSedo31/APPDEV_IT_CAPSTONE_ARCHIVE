package com.example.it_capstone_archive;

import android.content.Context;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBManager {
    private static final String CONNECTION_STRING = "your_connection_string";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static void initialize(Context context) {
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase("your_database_name");
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}