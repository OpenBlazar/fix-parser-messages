package pl.zankowski.fixparser.messages.dictionary;

import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.spi.DictionaryService;

import java.util.Optional;

public interface FixDictionaryService extends DictionaryService {

    Optional<FixDefinitionProvider> getDefinitionProvider(final DictionaryDescriptorRequestTO providerDescriptorRequest);

}
