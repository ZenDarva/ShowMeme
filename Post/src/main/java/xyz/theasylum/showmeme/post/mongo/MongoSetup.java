package xyz.theasylum.showmeme.post.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

@Component
public class MongoSetup {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    Environment environment;

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {

        boolean docker = false;

        for (String activeProfile : environment.getActiveProfiles()) {
            if (activeProfile.equals("docker")){
                docker = true;
            }
        }
        if (!docker){
            mongoTemplate.dropCollection("cachedimage");
            mongoTemplate.dropCollection("comment");
            mongoTemplate.dropCollection("post");
        }

        MappingContext mappingContext = mongoTemplate.getConverter().getMappingContext();

        if (mappingContext instanceof MongoMappingContext) {
            var mongoMappingContext = (MongoMappingContext) mappingContext;

            for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                var clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    var indexOps = mongoTemplate.indexOps(clazz);
                    var resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
                    resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                }
            }
        }
    }
}
