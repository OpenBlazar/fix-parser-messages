package pl.zankowski.fixparser.messages.dictionary;

import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.spi.DictionaryService;

public interface FixDictionaryService extends DictionaryService {

    FixDefinitionProvider getDefinitionProvider(final DictionaryDescriptorRequestTO providerDescriptorRequest)
            throws FixParserBusinessException;

}
