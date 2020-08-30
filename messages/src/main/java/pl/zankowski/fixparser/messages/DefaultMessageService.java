package pl.zankowski.fixparser.messages;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.dictionary.FixDictionaryService;
import pl.zankowski.fixparser.messages.fix.FixMessageConverter;
import pl.zankowski.fixparser.messages.fix.FixParser;
import pl.zankowski.fixparser.messages.spi.MessageService;

import java.util.List;

@Service
public class DefaultMessageService implements MessageService {

    private final FixParser fixParser;
    private final FixMessageConverter messageConverter;
    private final FixDictionaryService dictionaryService;

    @Autowired
    public DefaultMessageService(final FixDictionaryService dictionaryService,
                                 final FixParser fixParser,
                                 final FixMessageConverter fixMessageConverter) {
        this.dictionaryService = dictionaryService;
        this.fixParser = fixParser;
        this.messageConverter = fixMessageConverter;
    }

    @Override
    public List<FixMessageTO> parseInput(final FixParserRequestTO input) {
        return dictionaryService.getDefinitionProvider(
                ImmutableDictionaryDescriptorRequestTO.builder()
                        .username(input.getUsername())
                        .dictionaryDescriptor(input.getDictionaryDescriptor())
                        .build())
                .map(fixDefinitionProvider -> fixParser
                        .parseInput(input.getInput(), fixDefinitionProvider))
                .orElseGet(Lists::newArrayList);
    }

    @Override
    public String parseInput(final FixMessageTO message) {
        return messageConverter.convertToString(message);
    }

}
