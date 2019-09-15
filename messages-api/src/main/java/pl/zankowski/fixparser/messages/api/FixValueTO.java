package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableFixValueTO.class)
@JsonDeserialize(builder = ImmutableFixValueTO.Builder.class)
public interface FixValueTO {

    String getValue();

    String getDescription();

}
