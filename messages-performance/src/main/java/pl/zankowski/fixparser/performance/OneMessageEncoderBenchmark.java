package pl.zankowski.fixparser.performance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.dictionary.DefaultFixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.entity.DictionaryDescriptor;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;
import pl.zankowski.fixparser.messages.dictionary.entity.FixFieldDefinition;
import pl.zankowski.fixparser.messages.dictionary.loader.QuickFixDictionaryLoader;
import pl.zankowski.fixparser.messages.fix.FixMessageConverter;
import pl.zankowski.fixparser.messages.fix.FixParser;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@State(Scope.Thread)
public class OneMessageEncoderBenchmark {

    private FixMessageConverter converter;
    private FixMessageTO message;

    @Setup
    public void init() throws Exception {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("FIX50SP2.xml");
        final QuickFixDictionaryLoader fixXMLLoader = new QuickFixDictionaryLoader();

        final Map<Integer, FixFieldDefinition> values = fixXMLLoader.parseDocument(inputStream);

        final FixDictionary dictionary = FixDictionary.builder()
                .withDictionaryDescriptorEntity(DictionaryDescriptor.builder()
                        .build())
                .withDictionaryMap(values)
                .build();
        FixDefinitionProvider definitionProvider = new DefaultFixDefinitionProvider(dictionary);


        converter = new FixMessageConverter();
        FixParser parser = new FixParser();
        message = parser.parseInput(BenchmarkConstants.FIX_MESSAGE, definitionProvider).get(0);
        System.out.println(message);
    }

    @Benchmark
    public void oneMessageEncoderBenchmark() {
        converter.convertToString(message);
    }

}
