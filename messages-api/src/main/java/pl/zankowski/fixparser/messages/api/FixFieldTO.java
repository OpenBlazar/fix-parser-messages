package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableFixFieldTO.class)
@JsonDeserialize(builder = ImmutableFixFieldTO.Builder.class)
public interface FixFieldTO {

    int getTag();

    String getName();

}
