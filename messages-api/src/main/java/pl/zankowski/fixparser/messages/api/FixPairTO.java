package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableFixPairTO.class)
@JsonDeserialize(builder = ImmutableFixPairTO.Builder.class)
public interface FixPairTO {

    FixFieldTO getFixField();

    FixValueTO getFixValue();

}
