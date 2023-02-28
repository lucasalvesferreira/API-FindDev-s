package projetopi.finddevservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import projetopi.finddevservice.dtos.v1.request.DevelopRequestDto;
import projetopi.finddevservice.dtos.v1.request.NovaAvaliacaoRequestDto;
import projetopi.finddevservice.dtos.v1.response.AvaliacaoResponseDto;
import projetopi.finddevservice.services.AvaliacaoService;
import projetopi.finddevservice.util.MediaType;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/avaliacoes")
public class AvaliacaoController {

    @Autowired
    AvaliacaoService service;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Adds a new assessments",
            description = "Adds a new assessments by passing in a JSON, XML or YML representation of the Users!",
            tags = {"Assessments"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AvaliacaoResponseDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AvaliacaoResponseDto> post(@RequestBody @Valid
                                                     NovaAvaliacaoRequestDto novaAvaliacaoRequest) {
        AvaliacaoResponseDto resposta = service.post(novaAvaliacaoRequest);
//        novaAvaliacaoRequest.isCompany() ? colocar o metodo que vai trocar a vaga para encerrada

        return ResponseEntity.status(201).body(resposta);
    }

    @GetMapping(value = "/media/{idUser}")
    @Operation(
            summary = "Media search for reviews ", description = "Media search for reviews ",
            tags = {"Assessments"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DevelopRequestDto.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Double> getMedia(
            @PathVariable UUID idUser) {
        return ResponseEntity.of(service.getMediaAvaliacoes(idUser));
    }

    @GetMapping
    @Operation(
            summary = "Search all assessment ", description = "Search all assessment",
            tags = {"Assessments"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DevelopRequestDto.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<AvaliacaoResponseDto>> findAllAvaliacoes() {
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping(value = "/{idUser}")
    @Operation(
            summary = "Search all assessment by id ", description = "Search all assessment by id ",
            tags = {"Assessments"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DevelopRequestDto.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public List<AvaliacaoResponseDto> getAssessmentByid(
            @PathVariable UUID idUser) {
        return ResponseEntity.ok(service.getAssessmentByid(idUser)).getBody();
    }

}
