package pl.zankowski.fixparser.messages.dictionary;

import com.google.common.collect.ImmutableMap;
import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixFieldTO;
import pl.zankowski.fixparser.messages.api.ImmutableFixValueTO;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;
import pl.zankowski.fixparser.messages.dictionary.entity.FixFieldDefinition;

import java.util.Map;
import java.util.Objects;

public class DefaultFixDefinitionProvider implements FixDefinitionProvider {

    private static final String EMPTY_DESCRIPTION = "";

    private final Map<Integer, FixFieldDefinition> dictionaryMap;

    public DefaultFixDefinitionProvider(final FixDictionary fixDictionary) {
        this.dictionaryMap = ImmutableMap.copyOf(fixDictionary.getDictionaryMap());
    }

    @Override
    public FixFieldTO getFixField(final int tag) {
        final FixFieldDefinition fieldDefinition = dictionaryMap.get(tag);
        if (fieldDefinition == null) {
            return ImmutableFixFieldTO.builder()
                    .tag(tag)
                    .name(UNKNOWN)
                    .build();
        }
        return ImmutableFixFieldTO.builder()
                .name(fieldDefinition.getField().getName())
                .tag(fieldDefinition.getField().getTag())
                .build();
    }

    @Override
    public FixValueTO getFixValue(int tag, String valueEnum) {
        final FixFieldDefinition fieldDefinition = dictionaryMap.get(tag);
        if (fieldDefinition == null) {
            return ImmutableFixValueTO.builder()
                    .value(valueEnum)
                    .description(EMPTY_DESCRIPTION)
                    .build();
        }

        final String valueDescription = fieldDefinition.getValues().get(valueEnum);
        return ImmutableFixValueTO.builder()
                .value(valueEnum)
                .description(Objects.requireNonNullElse(valueDescription, EMPTY_DESCRIPTION))
                .build();

    }
}
