package com.example.gamesservice.util.counter;

import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class VisitCounter {

    private MongoCollection<Document> counterCollection;

    public VisitCounter(MongoTemplate mongoTemplate) {
        try {
            counterCollection = mongoTemplate.createCollection("visit_counter");
        } catch (Exception ex) {
            counterCollection = mongoTemplate.getCollection("visit_counter");
        }
    }

    public int count() {
        Document searchQuery = new Document("id", 1);
        Document first = counterCollection.find(searchQuery).first();
        if(first == null)
            return 0;
        return first.getInteger("count", 0);
    }

    public void increment() {
        if(count() == 0) {
            counterCollection.insertOne(new Document("id", 1).append("count", 1));
        } else {
            counterCollection.updateOne(new Document("id", 1), new Document("$set", new Document("count", count() + 1)));
        }
    }

}
