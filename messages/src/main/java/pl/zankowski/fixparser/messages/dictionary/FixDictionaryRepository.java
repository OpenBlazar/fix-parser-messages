package pl.zankowski.fixparser.messages.dictionary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import pl.zankowski.fixparser.messages.dictionary.entity.DictionaryDescriptor;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;

import java.util.List;

public interface FixDictionaryRepository extends MongoRepository<FixDictionary, DictionaryDescriptor> {

    List<FixDictionary> findByDictionaryDescriptorUserId(final Long userId);

}
