package pl.zankowski.fixparser.messages.fix;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FixDelimiterResolver {

    private static final Pattern TAG_9_PATTERN = Pattern.compile("[^0-9]9=[0-9]+");
    private static final Pattern TAG_35_PATTERN = Pattern.compile("[^0-9]35=[0-9A-Za-z]+");

    protected String resolveMessageDelimiter(final String input) {
        final int tag9Start = getTag9Index(input);
        final int tag9Length = getTag9Length(input);
        final int tag35Start = getTag35Index(input);

        return input.substring(tag9Start + tag9Length, tag35Start + 1);
    }

    private int getTag9Index(final String input) {
        final Matcher matcher = TAG_9_PATTERN.matcher(input);
        if (matcher.find()) {
            return matcher.start();
        }
        throw new IllegalArgumentException("Failed to find body length value.");
    }

    private int getTag9Length(final String input) {
        final Matcher matcher = TAG_9_PATTERN.matcher(input);
        if (matcher.find()) {
            return matcher.group().length();
        }
        throw new IllegalArgumentException("Failed to find body length value.");
    }

    private int getTag35Index(final String input) {
        final Matcher matcher = TAG_35_PATTERN.matcher(input);
        if (matcher.find()) {
            return matcher.start();
        }
        throw new IllegalArgumentException("Failed to find message type value.");
    }

}
