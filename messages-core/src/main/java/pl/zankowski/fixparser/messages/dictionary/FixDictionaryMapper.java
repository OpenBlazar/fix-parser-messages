package pl.zankowski.fixparser.messages.dictionary;

import org.springframework.stereotype.Component;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryTO;
import pl.zankowski.fixparser.messages.dictionary.entity.DictionaryDescriptor;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;
import pl.zankowski.fixparser.messages.dictionary.entity.FixFieldDefinition;

import java.util.Map;

@Component
public class FixDictionaryMapper {

    public FixDictionary map(final DictionaryTO dictionary, final Long userId,
            final Map<Integer, FixFieldDefinition> parsedDictionary) {
        return FixDictionary.builder()
                .withDictionaryDescriptorEntity(map(dictionary.getDictionaryDescriptor(), userId))
                .withDictionaryMap(parsedDictionary)
                .build();
    }

    private DictionaryDescriptor map(final DictionaryDescriptorTO dictionaryDescriptor,
            final Long userId) {
        return DictionaryDescriptor.builder()
                .withUserId(userId)
                .withProviderName(dictionaryDescriptor.getProviderName())
                .withLoaderType(dictionaryDescriptor.getLoaderType())
                .build();
    }

}
