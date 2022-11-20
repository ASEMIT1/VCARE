package com.vcare.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
	
	@Value("${mongo.dbHost}")
	private String dbHost;

	@Value("${mongo.dbUser}")
	private String dbUser;

	@Value("${mongo.dbPwd}")
	private String dbPwd;

	@Value("${mongo.dbPort}")
	private int dbPort;

	@Value("${mongo.dbName}")
	private String dbName;
    @Override
    protected String getDatabaseName() {
        return dbName;
    }
 

@Override
protected void configureClientSettings(Builder builder) {
	List<ServerAddress>  serverList = new ArrayList<>();
	serverList.add(new ServerAddress(dbHost, dbPort));
	builder
	    .credential(MongoCredential.createCredential(dbUser, dbName, dbPwd.toCharArray()))
	    .applyToClusterSettings(settings  -> {
	    	settings.hosts(serverList);
	    });
}
}