package pl.zankowski.fixparser.messages;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.zankowski.fixparser.messages.dictionary.loader.DictionaryLoaderFactory;
import pl.zankowski.fixparser.messages.dictionary.loader.QuickFixDictionaryLoader;

@Configuration
public class FixParserConfiguration {

    @Bean
    public DictionaryLoaderFactory dictionaryLoaderFactory() {
        return new DictionaryLoaderFactory(Lists.newArrayList(
                new QuickFixDictionaryLoader()
        ));
    }

}
