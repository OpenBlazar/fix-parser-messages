package pl.zankowski.fixparser.messages.fix;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixVersion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.zankowski.fixparser.messages.fix.FixDefinitionProviderFixture.TEST_DEFINITION_PROVIDER;

@ExtendWith(MockitoExtension.class)
class FixParserTest {

    @Mock
    private FixDelimiterResolver fixDelimiterResolver;

    @Mock
    private FixMessageConverter fixMessageConverter;

    private FixParser fixParser;

    @BeforeEach
    void setUp() {
        fixParser = new FixParser(fixDelimiterResolver, fixMessageConverter);
    }

    @Test
    void shouldReturnEmptyCollectionForEmptyString() {
        assertThat(fixParser.parseInput("", TEST_DEFINITION_PROVIDER)).isEmpty();
    }

    @Test
    void shouldSuccessfullyParseFixMessage() {
        final String message = "8=FIX.4.2#9=123#35=D#49=BlazarQuant#56=Broker#52=20160325-22:52:30.530#10=013";
        when(fixDelimiterResolver.resolveMessageDelimiter(anyString())).thenReturn("#");

        fixParser.parseInput(message, TEST_DEFINITION_PROVIDER);

        verify(fixDelimiterResolver).resolveMessageDelimiter(eq(message));
        verify(fixMessageConverter).convertToFixMessages(eq(Lists.newArrayList(message)),
                eq("#"), eq(TEST_DEFINITION_PROVIDER));
    }


}
