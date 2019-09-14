package pl.zankowski.fixparser.messages.dictionary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableDictionaryDescriptorTO.class)
@JsonDeserialize(builder = ImmutableDictionaryDescriptorTO.Builder.class)
public interface DictionaryDescriptorTO {

    String getProviderName();

    DictionaryLoaderType getLoaderType();

}
