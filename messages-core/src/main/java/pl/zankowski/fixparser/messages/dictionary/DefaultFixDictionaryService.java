package pl.zankowski.fixparser.messages.dictionary;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryInsertRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;
import pl.zankowski.fixparser.messages.dictionary.entity.FixFieldDefinition;
import pl.zankowski.fixparser.messages.dictionary.loader.DictionaryLoader;
import pl.zankowski.fixparser.messages.dictionary.loader.DictionaryLoaderFactory;
import pl.zankowski.fixparser.user.spi.UserService;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class DefaultFixDictionaryService implements FixDictionaryService {

    private final DictionaryLoaderFactory dictionaryLoaderFactory;
    private final MongoFixDictionaryRepository fixDictionaryRepository;
    private final FixDictionaryMapper fixDictionaryMapper;
    private final UserService userService;

    @Autowired
    public DefaultFixDictionaryService(final MongoFixDictionaryRepository fixDictionaryRepository,
            final FixDictionaryMapper fixDictionaryMapper, final UserService userService) {
        this.dictionaryLoaderFactory = new DictionaryLoaderFactory(Lists.newArrayList());
        this.fixDictionaryRepository = fixDictionaryRepository;
        this.fixDictionaryMapper = fixDictionaryMapper;
        this.userService = userService;
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
    public void saveDictionary(final DictionaryInsertRequestTO dictionaryInsertRequest)
            throws FixParserBusinessException {
        final DictionaryLoader dictionaryLoader = dictionaryLoaderFactory.getDictionaryLoader(
                dictionaryInsertRequest.getDictionary().getDictionaryDescriptor().getLoaderType());
        final Map<Integer, FixFieldDefinition> parsedDictionary = dictionaryLoader.parseDocument(
                new ByteArrayInputStream(dictionaryInsertRequest.getDictionary().getContent()));

        userService.findAccountByEmail(dictionaryInsertRequest.getUsername())
                .ifPresent(account -> {
                    final FixDictionary dictionary = fixDictionaryMapper
                            .map(dictionaryInsertRequest.getDictionary(), account.getId(),
                                    parsedDictionary);
                    fixDictionaryRepository.save(dictionary);
                });
    }

    @Override
    public FixDefinitionProvider getDefinitionProvider(
            final DictionaryDescriptorRequestTO providerDescriptorRequest)
            throws FixParserBusinessException {
        return userService.findAccountByEmail(providerDescriptorRequest.getUsername())
                .map(account -> getDefinitionProvider(
                        providerDescriptorRequest.getDictionaryDescriptor(), account.getId()))
                .orElseThrow(FixParserBusinessException::new)
                .get();
    }

    private Optional<FixDefinitionProvider> getDefinitionProvider(
            final DictionaryDescriptorTO dictionaryDescriptor,
            final Long userId) {
        return fixDictionaryRepository
                .findById(fixDictionaryMapper.map(dictionaryDescriptor, userId))
                .map(DefaultFixDefinitionProvider::new);
    }

    @Override
    public void removeDictionary(final DictionaryDescriptorRequestTO providerDescriptorRequest) {
        userService.findAccountByEmail(providerDescriptorRequest.getUsername())
                .ifPresent(account -> {
                    fixDictionaryRepository.deleteById(
                            fixDictionaryMapper
                                    .map(providerDescriptorRequest.getDictionaryDescriptor(),
                                            account.getId()));
                });
    }

}
