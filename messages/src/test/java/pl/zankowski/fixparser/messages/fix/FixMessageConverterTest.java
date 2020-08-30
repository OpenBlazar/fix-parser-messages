package pl.zankowski.fixparser.messages.fix;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixVersion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.zankowski.fixparser.messages.fix.FixDefinitionProviderFixture.TEST_DEFINITION_PROVIDER;

public class FixMessageConverterTest {

    private FixMessageConverter fixMessageConverter;

    @BeforeEach
    void setUp() {
        fixMessageConverter = new FixMessageConverter();
    }

    @Test
    void shouldSuccessfullyParseFixMessage() {
        final List<FixMessageTO> messages = fixMessageConverter.convertToFixMessages(Lists.newArrayList(
                "8=FIX.4.2#9=123#35=D#49=BlazarQuant#56=Broker#52=20160325-22:52:30.530#10=013"),
                "#",
                TEST_DEFINITION_PROVIDER);

        assertThat(messages).hasSize(1);

        final FixMessageTO fixMessage = messages.get(0);
        assertThat(fixMessage.getMessageId()).isEqualTo(0);
        assertThat(fixMessage.getVersion()).isEqualTo(FixVersion.FIX_42);
        assertThat(fixMessage.getMessageType().getFixField().getTag()).isEqualTo(35);
        assertThat(fixMessage.getMessageType().getFixField().getName()).isEqualTo("MsgType");
        assertThat(fixMessage.getMessageType().getFixValue().getValue()).isEqualTo("D");
        assertThat(fixMessage.getMessageType().getFixValue().getDescription()).isEqualTo("NEWORDERSINGLE");
        assertThat(fixMessage.getMessageFields()).hasSize(7);
    }

}
