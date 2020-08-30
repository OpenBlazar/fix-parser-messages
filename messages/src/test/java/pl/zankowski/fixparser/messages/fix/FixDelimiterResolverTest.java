package pl.zankowski.fixparser.messages.fix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class FixDelimiterResolverTest {

    private FixDelimiterResolver fixDelimiterResolver;

    @BeforeEach
    void setUp() {
        fixDelimiterResolver = new FixDelimiterResolver();
    }

    @ParameterizedTest
    @ValueSource(strings = {"#", "\01", "|", "^", "\u0001"})
    void shouldSuccessfullyDetectDelimiter(final String delimiter) {
        final String detectedDelimiter = fixDelimiterResolver.resolveMessageDelimiter(prepareMessage(delimiter));
        assertThat(detectedDelimiter).isEqualTo(delimiter);
    }

    private String prepareMessage(final String delimiter) {
        return "8=FIX.4.2%s9=123%s35=D%s49=BlazarQuant".replace("%s", delimiter);
    }

}
