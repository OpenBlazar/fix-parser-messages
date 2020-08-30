package pl.zankowski.fixparser.messages.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryInsertRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;
import pl.zankowski.fixparser.messages.dictionary.entity.FixFieldDefinition;
import pl.zankowski.fixparser.messages.dictionary.loader.DictionaryLoader;
import pl.zankowski.fixparser.messages.dictionary.loader.DictionaryLoaderFactory;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class DefaultFixDictionaryService implements FixDictionaryService {

    private final DictionaryLoaderFactory dictionaryLoaderFactory;
    private final FixDictionaryRepository fixDictionaryRepository;
    private final FixDictionaryMapper fixDictionaryMapper;

    @Autowired
    public DefaultFixDictionaryService(final DictionaryLoaderFactory dictionaryLoaderFactory,
                                       final FixDictionaryRepository fixDictionaryRepository,
                                       final FixDictionaryMapper fixDictionaryMapper) {
        this.dictionaryLoaderFactory = dictionaryLoaderFactory;
        this.fixDictionaryRepository = fixDictionaryRepository;
        this.fixDictionaryMapper = fixDictionaryMapper;
    }

    @Override
    public Set<DictionaryDescriptorTO> getDictionaryDescriptors(final Long userId) {
        return fixDictionaryRepository.findByDictionaryDescriptorUserId(userId).stream()
                .map(this::toDictionaryDescriptor)
                .collect(toSet());
    }

    private DictionaryDescriptorTO toDictionaryDescriptor(final FixDictionary fixDictionary) {
        return ImmutableDictionaryDescriptorTO.builder()
                .loaderType(fixDictionary.getDictionaryDescriptor().getLoaderType())
                .providerName(fixDictionary.getDictionaryDescriptor().getProviderName())
                .build();
    }

    @Override
    public Optional<FixDefinitionProvider> getDefinitionProvider(
            final DictionaryDescriptorRequestTO providerDescriptorRequest) {
        return getDefinitionProvider(
                providerDescriptorRequest.getDictionaryDescriptor(), null);
    }

    private Optional<FixDefinitionProvider> getDefinitionProvider(
            final DictionaryDescriptorTO dictionaryDescriptor,
            final Long userId) {
        return fixDictionaryRepository
                .findById(fixDictionaryMapper.map(dictionaryDescriptor, userId))
                .map(DefaultFixDefinitionProvider::new);
    }

    @Override
    public void saveDictionary(final DictionaryInsertRequestTO dictionaryInsertRequest)
            throws FixParserBusinessException {
        final DictionaryLoader dictionaryLoader = dictionaryLoaderFactory.getDictionaryLoader(
                dictionaryInsertRequest.getDictionary().getDictionaryDescriptor().getLoaderType());
        final Map<Integer, FixFieldDefinition> parsedDictionary = dictionaryLoader.parseDocument(
                new ByteArrayInputStream(dictionaryInsertRequest.getDictionary().getContent()));

        final FixDictionary dictionary = fixDictionaryMapper
                .map(dictionaryInsertRequest.getDictionary(), null, parsedDictionary);
        fixDictionaryRepository.save(dictionary);
    }

}
