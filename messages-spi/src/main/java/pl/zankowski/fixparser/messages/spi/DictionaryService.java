package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryInsertRequestTO;

import java.util.Set;

public interface DictionaryService {

    Set<DictionaryDescriptorTO> getDictionaryDescriptors(final Long userId);

    void saveDictionary(final DictionaryInsertRequestTO dictionaryInsertRequest)
            throws FixParserBusinessException;

    void removeDictionary(final DictionaryDescriptorRequestTO providerDescriptorRequest);

}
