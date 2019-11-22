package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;

@Value.Immutable
@JsonSerialize(as = ImmutableFixParserRequestTO.class)
@JsonDeserialize(builder = ImmutableFixParserRequestTO.Builder.class)
public interface FixMessageRequestTO {

    String getUsername();

    DictionaryDescriptorTO getDictionaryDescriptor();

}
