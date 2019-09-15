package pl.zankowski.fixparser.messages.dictionary.loader;

import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.dictionary.entity.FixFieldDefinition;

import java.io.InputStream;
import java.util.Map;

public interface DictionaryLoader {

    Map<Integer, FixFieldDefinition> parseDocument(final InputStream documentFile) throws
            FixParserBusinessException;

    DictionaryLoaderType getType();

}
