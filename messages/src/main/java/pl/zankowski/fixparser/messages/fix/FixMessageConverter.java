package pl.zankowski.fixparser.messages.fix;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.messages.api.FixVersion;
import pl.zankowski.fixparser.messages.api.ImmutableFixMessageTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixPairTO;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FixMessageConverter {

    public static final String FIELD_DELIMITER = "=";
    public static final int FIX_KEY = 0;
    public static final int FIX_VALUE = 1;
    public static final char ENTRY_DELIMITER = '\u0001';

    public List<FixMessageTO> convertToFixMessages(final List<String> textMessages, final String delimiter,
                                                   final FixDefinitionProvider definitionProvider) {
        final List<FixMessageTO> messages = Lists.newArrayList();
        long counter = 0;
        for (final String textMessage : textMessages) {
            messages.add(convertToFixMessage(textMessage, delimiter, counter++, definitionProvider));
        }
        return messages;
    }

    private FixMessageTO convertToFixMessage(final String textMessage, final String delimiter,
                                            final long counter, final FixDefinitionProvider definitionProvider) {
        final String[] fields = textMessage.split("\\" + delimiter);
        final List<FixPairTO> messageFields = Lists.newArrayList();

        for (String field : fields) {
            String[] values = field.split(FIELD_DELIMITER);
            if (values.length != 2) {
                continue;
            }
            final int fixFieldTag = Integer.parseInt(values[FIX_KEY]);
            final FixFieldTO fieldKey = definitionProvider.getFixField(fixFieldTag);
            final FixValueTO fieldValue = definitionProvider.getFixValue(fixFieldTag, values[FIX_VALUE]);
            messageFields.add(ImmutableFixPairTO.builder()
                    .fixField(fieldKey)
                    .fixValue(fieldValue)
                    .build());
        }
        return toFixMessage(messageFields, counter);
    }

    private FixMessageTO toFixMessage(final List<FixPairTO> messageFields, final long counter) {
        final ImmutableFixMessageTO.Builder messageBuilder = ImmutableFixMessageTO.builder();
        final FixPairTO version = getField(messageFields, 8);
        if (version != null) {
            messageBuilder.version(FixVersion.getFixVersionFromCode(version.getFixValue().getValue()));
        }
        final FixPairTO messageType = getField(messageFields, 35);
        if (messageType != null) {
            messageBuilder.messageType(messageType);
        }
        messageBuilder.messageId(counter);
        messageBuilder.messageFields(messageFields);
        return messageBuilder.build();
    }

    private FixPairTO getField(final List<FixPairTO> fixPairs, final int tag) {
        final List<FixPairTO> fields = fixPairs.stream()
                .filter(pair -> pair.getFixField().getTag() == tag)
                .collect(Collectors.toList());
        if (fields.isEmpty()) {
            return null;
        } else {
            return fields.get(0);
        }
    }

    public String convertToString(final FixMessageTO fixMessage) {
        return convertToString(fixMessage, ENTRY_DELIMITER);
    }

    private String convertToString(final FixMessageTO fixMessage, final char entryDelimiter) {
        final StringBuilder builder = new StringBuilder();
        fixMessage.getMessageFields().forEach((pair) -> {
            builder.append(pair.getFixField().getTag());
            builder.append(FIELD_DELIMITER);
            builder.append(pair.getFixValue().getValue());
            builder.append(entryDelimiter);
        });
        return builder.toString();
    }

}
