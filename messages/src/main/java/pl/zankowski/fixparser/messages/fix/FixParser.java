package pl.zankowski.fixparser.messages.fix;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FixParser {

    private static final Pattern FIX_PATTERN = Pattern.compile("[^0-9a-zA-Z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?\\s]*8=FIX(.*?)[^0-9]10=\\d{3}.?");

    private final FixDelimiterResolver fixDelimiterResolver;
    private final FixMessageConverter fixMessageConverter;

    @Autowired
    public FixParser(final FixDelimiterResolver fixDelimiterResolver, final FixMessageConverter fixMessageConverter) {
        this.fixDelimiterResolver = fixDelimiterResolver;
        this.fixMessageConverter = fixMessageConverter;
    }

    public List<FixMessageTO> parseInput(final String input, final FixDefinitionProvider definitionProvider) {
        final List<String> textMessages = extractFixMessages(input);
        if (textMessages.isEmpty()) {
            return Collections.emptyList();
        }

        final String delimiter = fixDelimiterResolver.resolveMessageDelimiter(textMessages.get(0));
        return fixMessageConverter.convertToFixMessages(textMessages, delimiter, definitionProvider);
    }

    private List<String> extractFixMessages(final String input) {
        final List<String> messages = Lists.newArrayList();
        final Matcher matcher = FIX_PATTERN.matcher(input);

        while (matcher.find()) {
            messages.add(matcher.group());
        }

        return messages;
    }

}
