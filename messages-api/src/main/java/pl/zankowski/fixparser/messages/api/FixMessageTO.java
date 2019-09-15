package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableFixMessageTO.class)
@JsonDeserialize(builder = ImmutableFixMessageTO.Builder.class)
public interface FixMessageTO {

    Long getMessageId();

    FixVersion getVersion();

    FixPairTO getMessageType();

    List<FixPairTO> getMessageFields();

}
