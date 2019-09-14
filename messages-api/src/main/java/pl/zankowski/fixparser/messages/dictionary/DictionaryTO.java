package pl.zankowski.fixparser.messages.dictionary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableDictionaryTO.class)
@JsonDeserialize(builder = ImmutableDictionaryTO.Builder.class)
public interface DictionaryTO {

    DictionaryDescriptorTO getDictionaryDescriptor();

    byte[] getContent();

}
