package pl.zankowski.fixparser.messages.api.dictionary;

import org.immutables.value.Value;

@Value.Immutable
public interface DictionaryDescriptorRequestTO {

    DictionaryDescriptorTO getDictionaryDescriptor();

    String getUsername();

}
