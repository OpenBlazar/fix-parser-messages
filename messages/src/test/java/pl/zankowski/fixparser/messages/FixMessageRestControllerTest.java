package pl.zankowski.fixparser.messages;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserRequestTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixParserRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.spi.MessageService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FixMessageRestControllerTest {

    @Mock
    private MessageService messageService;

    private FixMessageRestController fixMessageRestController;

    @BeforeEach
    void setUp() {
        fixMessageRestController = new FixMessageRestController(messageService);
    }

    @Test
    void shouldSuccessfullyReturnResponseEntity() {
        final FixParserRequestTO request = ImmutableFixParserRequestTO.builder()
                .input("8=FIX.4.2#9=123#35=D#49=BlazarQuant#56=Broker#52=20160325-22:52:30.530#10=013")
                .username("test")
                .dictionaryDescriptor(ImmutableDictionaryDescriptorTO.builder()
                        .providerName("FIX4.2")
                        .loaderType(DictionaryLoaderType.QUICKFIX_LOADER)
                        .build())
                .build();
        when(messageService.parseInput(eq(request))).thenReturn(Lists.newArrayList());

        final ResponseEntity<List<FixMessageTO>> response = fixMessageRestController.parse(request);

        assertThat(response.getBody()).isEmpty();
    }

}
