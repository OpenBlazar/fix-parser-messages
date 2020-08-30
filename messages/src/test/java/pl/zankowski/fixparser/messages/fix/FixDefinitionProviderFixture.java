package pl.zankowski.fixparser.messages.fix;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.dictionary.DefaultFixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.entity.DictionaryDescriptor;
import pl.zankowski.fixparser.messages.dictionary.entity.FixDictionary;
import pl.zankowski.fixparser.messages.dictionary.entity.FixField;
import pl.zankowski.fixparser.messages.dictionary.entity.FixFieldDefinition;

public class FixDefinitionProviderFixture {

    public static final FixDefinitionProvider TEST_DEFINITION_PROVIDER = new DefaultFixDefinitionProvider(FixDictionary.builder()
            .withDictionaryDescriptorEntity(DictionaryDescriptor.builder()
                    .withLoaderType(DictionaryLoaderType.QUICKFIX_LOADER)
                    .withProviderName("FIX50")
                    .withUserId(null)
                    .build())
            .withDictionaryMap(ImmutableMap.<Integer, FixFieldDefinition>builder()
                    .put(8, FixFieldDefinition.builder()
                            .withField(FixField.builder()
                                    .withTag(8)
                                    .withName("BeginString")
                                    .build())
                            .withValues(Maps.newHashMap())
                            .build())
                    .put(9, FixFieldDefinition.builder()
                            .withField(FixField.builder()
                                    .withTag(9)
                                    .withName("BodyLength")
                                    .build())
                            .withValues(Maps.newHashMap())
                            .build())
                    .put(35, FixFieldDefinition.builder()
                            .withField(FixField.builder()
                                    .withTag(35)
                                    .withName("MsgType")
                                    .build())
                            .withValue("D", "NEWORDERSINGLE")
                            .build())
                    .put(49, FixFieldDefinition.builder()
                            .withField(FixField.builder()
                                    .withTag(49)
                                    .withName("SenderCompID")
                                    .build())
                            .withValues(Maps.newHashMap())
                            .build())
                    .put(52, FixFieldDefinition.builder()
                            .withField(FixField.builder()
                                    .withTag(52)
                                    .withName("SendingTime")
                                    .build())
                            .withValues(Maps.newHashMap())
                            .build())
                    .put(56, FixFieldDefinition.builder()
                            .withField(FixField.builder()
                                    .withTag(56)
                                    .withName("TargetCompID")
                                    .build())
                            .withValues(Maps.newHashMap())
                            .build())
                    .put(10, FixFieldDefinition.builder()
                            .withField(FixField.builder()
                                    .withTag(10)
                                    .withName("CheckSum")
                                    .build())
                            .withValues(Maps.newHashMap())
                            .build())
                    .build()
            )
            .build());

}
