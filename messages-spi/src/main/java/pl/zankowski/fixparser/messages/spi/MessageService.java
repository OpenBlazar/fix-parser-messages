package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserRequestTO;

import java.util.List;

public interface MessageService {

    List<FixMessageTO> parseInput(FixParserRequestTO input);

    String parseInput(FixMessageTO message);

}
