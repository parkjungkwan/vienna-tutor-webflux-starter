package com.example.demo.common.config;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.user.domain.UserModel;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.IntStream;
@Configuration
public class UsersCollectionConfig {
    @Bean
    CommandLineRunner initUsers(UserRepository repo) {
        System.out.println(" >>>> 1 실행 >>>>");

        var host = "223.130.153.131";
        var port = "";
        var dbname = "test";
        var databasename = "";
        var user = "yoo";
        var pw = "test";



        MongoClient mongoClient = MongoClients.create("mongodb://root:root@223.130.153.131:27017/rootdb");
        
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("users");
        Document document = new Document("name", "Café Con Leche")
                    .append("contact", new Document("phone", "228-555-0149")
                                            .append("email", "cafeconleche@example.com")
                                            .append("location",Arrays.asList(-73.92502, 40.8279556)))
               .append("stars", 3)
               .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));        

return args -> {
            IntStream.range(0, 1).forEach(i -> {
                Mono<UserModel> userMono = Mono.just(UserModel.builder()
                .email(i+"@test.com")
                .firstName("James" + i)
                .lastName("Byden " + i)
                .password("aaa")
                .roles(null)
                .build());
               
                repo.insert(userMono);
            });
        };
    }
}
