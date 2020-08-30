package pl.zankowski.fixparser.messages.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.zankowski.fixparser.core.api.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryInsertRequestTO;
import pl.zankowski.fixparser.messages.api.dictionary.ImmutableDictionaryTO;

import java.io.IOException;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RequestMapping("/dictionary")
@RestController
public class FixDictionaryRestController {

    private final FixDictionaryService dictionaryService;

    @Autowired
    public FixDictionaryRestController(final FixDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDictionary(@RequestPart("file") final MultipartFile file,
                                              final String providerName)
            throws FixParserBusinessException, IOException {
        dictionaryService.saveDictionary(ImmutableDictionaryInsertRequestTO.builder()
                .dictionary(ImmutableDictionaryTO.builder()
                        .dictionaryDescriptor(ImmutableDictionaryDescriptorTO.builder()
                                .loaderType(DictionaryLoaderType.QUICKFIX_LOADER)
                                .providerName(providerName)
                                .build())
                        .content(file.getBytes())
                        .build())
                .username("test")
                .build());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getDictionaryDescriptors() {
        return ResponseEntity.ok(dictionaryService.getDictionaryDescriptors(null));
    }

}
