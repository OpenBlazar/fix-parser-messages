package pl.zankowski.fixparser.messages;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserRequestTO;
import pl.zankowski.fixparser.messages.api.FixVersion;
import pl.zankowski.fixparser.messages.api.ImmutableFixFieldTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixMessageTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixPairTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixParserRequestTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixValueTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.dictionary.FixDictionaryService;
import pl.zankowski.fixparser.messages.fix.FixMessageConverter;
import pl.zankowski.fixparser.messages.fix.FixParser;
import pl.zankowski.fixparser.messages.spi.MessageService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static pl.zankowski.fixparser.messages.fix.FixDefinitionProviderFixture.TEST_DEFINITION_PROVIDER;

@ExtendWith(MockitoExtension.class)
public class DefaultMessageServiceTest {

    @Mock
    private FixParser fixParser;
    @Mock
    private FixMessageConverter fixMessageConverter;
    @Mock
    private FixDictionaryService fixDictionaryService;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new DefaultMessageService(fixDictionaryService, fixParser, fixMessageConverter);
    }

    @Test
    void shouldSuccessfullyGoThroughParsingFlow() throws FixParserBusinessException {
        final String input = "8=FIX.4.2#9=123#35=D#49=BlazarQuant#56=Broker#52=20160325-22:52:30.530#10=013";
        final DictionaryDescriptorTO descriptor = ImmutableDictionaryDescriptorTO.builder()
                .providerName("FIX4.2")
                .loaderType(DictionaryLoaderType.QUICKFIX_LOADER)
                .build();
        final FixParserRequestTO request = ImmutableFixParserRequestTO.builder()
                .input(input)
                .username("test")
                .dictionaryDescriptor(descriptor)
                .build();
        final List<FixMessageTO> response = Lists.newArrayList();

        when(fixDictionaryService.getDefinitionProvider(eq(ImmutableDictionaryDescriptorRequestTO.builder()
                .username(request.getUsername())
                .dictionaryDescriptor(descriptor)
                .build()))).thenReturn(Optional.of(TEST_DEFINITION_PROVIDER));
        when(fixParser.parseInput(eq(input), eq(TEST_DEFINITION_PROVIDER))).thenReturn(response);

        final List<FixMessageTO> messages = messageService.parseInput(request);

        assertThat(messages).isEqualTo(response);
    }

    @Test
    void shouldSuccessfullyConvertFixMessageIntoString() {
        final FixMessageTO message = ImmutableFixMessageTO.builder()
                .messageId(1L)
                .messageType(ImmutableFixPairTO.builder()
                        .fixField(ImmutableFixFieldTO.builder()
                                .tag(8)
                                .name("BeginString")
                                .build())
                        .fixValue(ImmutableFixValueTO.builder()
                                .description("")
                                .value("")
                                .build())
                        .build())
                .version(FixVersion.FIX_42)
                .messageFields(Lists.newArrayList())
                .build();
        final String output = "output";
        when(fixMessageConverter.convertToString(eq(message))).thenReturn(output);

        final String methodOutput = messageService.parseInput(message);

        assertThat(methodOutput).isEqualTo(output);
    }

}
