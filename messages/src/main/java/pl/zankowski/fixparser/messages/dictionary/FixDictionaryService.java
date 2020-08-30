package pl.zankowski.fixparser.messages.dictionary;

import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryInsertRequestTO;
import pl.zankowski.fixparser.messages.spi.DictionaryService;

import java.util.Optional;
import java.util.Set;

public interface FixDictionaryService extends DictionaryService {

    Set<DictionaryDescriptorTO> getDictionaryDescriptors(Long userId);

    Optional<FixDefinitionProvider> getDefinitionProvider(final DictionaryDescriptorRequestTO providerDescriptorRequest);

    void saveDictionary(DictionaryInsertRequestTO dictionaryInsertRequest)
            throws FixParserBusinessException;
}
