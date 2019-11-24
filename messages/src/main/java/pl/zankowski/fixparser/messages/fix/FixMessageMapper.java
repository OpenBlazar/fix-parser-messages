package pl.zankowski.fixparser.messages.fix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.fix.entity.FixMessageEntity;

import java.time.Instant;

@Component
public class FixMessageMapper {

    private final FixMessageConverter fixMessageConverter;

    @Autowired
    public FixMessageMapper(final FixMessageConverter fixMessageConverter) {
        this.fixMessageConverter = fixMessageConverter;
    }

    public FixMessageTO map(final FixMessageEntity message) {
        return null;
    }

    public FixMessageEntity map(final FixMessageTO message, final Long userId) {
        return FixMessageEntity.builder()
                .withId(message.getMessageId())
                .withUserId(userId)
                .withTimestamp(Instant.now())
                .withMessage(fixMessageConverter.convertToString(message))
                .build();
    }

}
