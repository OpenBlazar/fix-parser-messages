package pl.zankowski.fixparser.messages;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixParserRequestTO;
import pl.zankowski.fixparser.messages.spi.MessageService;

import java.util.List;

@RequestMapping("/messages")
@RestController
public class FixMessageRestController {

    private final MessageService messageService;

    @Autowired
    public FixMessageRestController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Parse FIX messages", description = "Returns parsed FIX messages from string input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping
    public ResponseEntity<List<FixMessageTO>> parse(@RequestBody final FixParserRequestTO input) {
        return ResponseEntity.ok(messageService.parseInput(input));
    }

}
