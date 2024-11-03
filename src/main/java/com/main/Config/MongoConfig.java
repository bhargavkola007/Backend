package com.main.Config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {
    private static final String CONNECTION_STRING = "mongodb+srv://bhargavkola53:12345@mydtabase.5iadk.mongodb.net/userDB?retryWrites=true&w=majority&tls=true";
    private static final String APP_NAME = "UserProfileApp";

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .applicationName(APP_NAME)
                .build();

        return MongoClients.create(clientSettings);
    }
}
