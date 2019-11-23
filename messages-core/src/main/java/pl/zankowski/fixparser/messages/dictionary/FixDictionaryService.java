package pl.zankowski.fixparser.messages.dictionary;

import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.spi.DictionaryService;

public interface FixDictionaryService extends DictionaryService {

    FixDefinitionProvider getDefinitionProvider(final DictionaryDescriptorTO providerDescriptor);

}
