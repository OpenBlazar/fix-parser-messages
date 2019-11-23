package pl.zankowski.fixparser.messages.dictionary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;

import java.util.List;

@Repository
public interface MongoFixDictionaryRepository extends MongoRepository {

    List<FixDictionary> findByDictionaryDescriptorUserId(final Long userId);

}
