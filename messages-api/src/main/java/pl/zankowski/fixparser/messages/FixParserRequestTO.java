package pl.zankowski.fixparser.messages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import pl.zankowski.fixparser.messages.dictionary.DictionaryDescriptorTO;

@Value.Immutable
@JsonSerialize(as = ImmutableFixParserRequestTO.class)
@JsonDeserialize(builder = ImmutableFixParserRequestTO.Builder.class)
public interface FixParserRequestTO {

    DictionaryDescriptorTO getDictionaryDescriptor();

    String getInput();

}
