package pl.zankowski.fixparser.messages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfiguration {

    @Bean
    public MappingMongoConverter mongoConverter(final MongoDatabaseFactory mongoFactory,
                                                final MongoMappingContext mongoMappingContext) {
        final DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
        final MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mongoConverter.setMapKeyDotReplacement("-DOT");
        return mongoConverter;
    }

}
