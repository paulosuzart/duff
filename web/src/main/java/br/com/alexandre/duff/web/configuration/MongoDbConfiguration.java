package br.com.alexandre.duff.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages="br.com.alexandre.duff.web.repository")
public class MongoDbConfiguration { }
