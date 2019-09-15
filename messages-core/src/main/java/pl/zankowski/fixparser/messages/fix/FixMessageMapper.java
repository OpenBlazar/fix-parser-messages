package pl.zankowski.fixparser.messages.fix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.fix.entity.FixMessage;

import java.time.Instant;

@Component
public class FixMessageMapper {

    private final FixMessageConverter fixMessageConverter;

    @Autowired
    public FixMessageMapper(final FixMessageConverter fixMessageConverter) {
        this.fixMessageConverter = fixMessageConverter;
    }

    public FixMessageTO map(final FixMessage message) {
        return null;
    }

    public FixMessage map(final FixMessageTO message, final Long userId) {
        return FixMessage.builder()
                .withId(message.getMessageId())
                .withUserId(userId)
                .withTimestamp(Instant.now())
                .withInput(fixMessageConverter.convertToString(message))
                .build();
    }

}
