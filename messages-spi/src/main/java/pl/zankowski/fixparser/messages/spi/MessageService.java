package pl.zankowski.fixparser.messages.spi;

import org.springframework.data.domain.Pageable;
import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.FixMessageRequestTO;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserRequestTO;

import java.util.List;

public interface MessageService {

    List<FixMessageTO> getMessages(FixMessageRequestTO input, Pageable pageable) throws FixParserBusinessException;

    int countUserMessages(String username) throws FixParserBusinessException;

    List<FixMessageTO> parseInput(FixParserRequestTO input) throws FixParserBusinessException;

    String parseInput(FixMessageTO message);

    void clearHistory(String username) throws FixParserBusinessException;

}
