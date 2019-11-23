package pl.zankowski.fixparser.messages.api.dictionary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
public interface DictionaryInsertRequestTO {

    DictionaryTO getDictionary();

    String getUsername();

}
