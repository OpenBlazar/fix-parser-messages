package pl.zankowski.fixparser.messages;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zankowski.fixparser.common.Base64Util;
import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.FixMessageRequestTO;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserRequestTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixParserRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.dictionary.FixDictionaryService;
import pl.zankowski.fixparser.messages.fix.FixMessageConverter;
import pl.zankowski.fixparser.messages.fix.FixMessageMapper;
import pl.zankowski.fixparser.messages.fix.FixParser;
import pl.zankowski.fixparser.messages.fix.entity.FixMessageEntity;
import pl.zankowski.fixparser.messages.spi.MessageService;
import pl.zankowski.fixparser.user.api.UserNotFoundException;
import pl.zankowski.fixparser.user.spi.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultMessageService implements MessageService {

    private final FixParser fixParser;
    private final FixMessageConverter messageConverter;
    private final FixMessageMapper mapper;
    private final FixDictionaryService dictionaryService;

    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public DefaultMessageService(final MessageRepository messageRepository,
            final FixMessageMapper mapper,
            final FixDictionaryService dictionaryService, final UserService userService) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
        this.dictionaryService = dictionaryService;
        this.fixParser = new FixParser();
        this.messageConverter = new FixMessageConverter();
        this.userService = userService;
    }

    @Override
    public List<FixMessageTO> getMessages(final FixMessageRequestTO input, final Pageable pageable)
            throws FixParserBusinessException {
        return userService.findAccountByEmail(input.getUsername())
                .map(account -> getMessages(account.getId(), pageable))
                .orElseThrow(UserNotFoundException::new);
    }

    private List<FixMessageTO> getMessages(final Long userId, final Pageable pageable) {
        return messageRepository.findByUserId(userId, pageable)
                .stream()
                .map(FixMessageEntity::getMessage)
                .map(Base64Util::decode)
                .map(this::toParserRequest)
                .map(this::parseInput)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private FixParserRequestTO toParserRequest(final String input) {
        return ImmutableFixParserRequestTO.builder()
                .input(input)
                .build();
    }

    @Override
    public int countUserMessages(final String username) throws UserNotFoundException {
        return userService.findAccountByEmail(username)
                .map(account -> countUserMessages(account.getId()))
                .orElseThrow(UserNotFoundException::new);
    }

    private int countUserMessages(final Long userId) {
        return messageRepository.countByUserId(userId);
    }

    @Override
    public List<FixMessageTO> parseInput(final FixParserRequestTO input) {
        final List<FixMessageTO> messages = dictionaryService.getDefinitionProvider(
                ImmutableDictionaryDescriptorRequestTO.builder()
                        .username(input.getUsername())
                        .dictionaryDescriptor(input.getDictionaryDescriptor())
                        .build())
                .map(fixDefinitionProvider -> fixParser
                        .parseInput(input.getInput(), fixDefinitionProvider))
                .orElseGet(Lists::newArrayList);

        final boolean shouldSaveMessages = false;
        if (shouldSaveMessages) {
            userService.findAccountByEmail(input.getUsername())
                    .ifPresent(account -> saveMessages(account.getId(), messages));
        }

        return messages;
    }

    private void saveMessages(final Long userId, final List<FixMessageTO> messages) {
        messages.forEach(message -> messageRepository.save(mapper.map(message, userId)));
    }

    @Override
    public String parseInput(final FixMessageTO message) {
        return messageConverter.convertToString(message);
    }

    @Override
    public void clearHistory(final String username) {
        userService.findAccountByEmail(username)
                .ifPresent(account -> messageRepository.deleteAllByUserId(account.getId()));
    }

}
