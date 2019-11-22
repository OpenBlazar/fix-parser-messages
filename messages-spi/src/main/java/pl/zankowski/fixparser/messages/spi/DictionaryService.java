package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryTO;

import java.util.Set;

public interface DictionaryService {

    Set<DictionaryDescriptorTO> getDictionaryDescriptors(final Long userId);

    void saveDictionary(final DictionaryTO dictionary) throws FixParserBusinessException;

    boolean removeDictionary(final DictionaryDescriptorTO descriptor);

}
