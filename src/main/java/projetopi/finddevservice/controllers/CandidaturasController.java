package projetopi.finddevservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetopi.finddevservice.dtos.v1.request.CandidaturaRequestDto;
import projetopi.finddevservice.dtos.v1.response.CandidaturaResponseDto;
import projetopi.finddevservice.services.CandidaturasService;
import projetopi.finddevservice.util.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidaturas")
@Tag(name = "Candidaturas",description = "Endpoints for managing Candidaturas")
public class CandidaturasController {

    @Autowired
    CandidaturasService service;

    @PostMapping(
        consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
        produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
        summary = "Adds a new Candidatura",
        description = "Adds a new Candidatura by passing in a JSON, XML or YML representation of the Candidatura!",
        tags = {"Candidaturas"},
        responses = {
            @ApiResponse(
                description = "Success", responseCode = "200",
                content = @Content(schema = @Schema(implementation = CandidaturaRequestDto.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<CandidaturaResponseDto> save(@RequestBody CandidaturaRequestDto candidatura) {
        return ResponseEntity.ok(service.create(candidatura));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Finds a Candidatura", description = "Finds a Candidatura by its id",
            tags = {"Candidaturas"},
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CandidaturaRequestDto.class))
                    ),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<CandidaturaResponseDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Finds all Candidaturas ", description = "Finds all Candidaturas ",
            tags = {"Candidaturas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CandidaturaRequestDto.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<CandidaturaResponseDto>> findAll() {
        List<CandidaturaResponseDto> candidaturas = service.findAll();

        return candidaturas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(candidaturas);
    }

    @GetMapping(
        value = "/idVaga/{idVaga}",
        produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
        summary = "Finds all Candidaturas from a Vaga", description = "Finds all Candidaturas from a Vaga",
        tags = {"Candidaturas"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CandidaturaRequestDto.class))
                )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        }
    )
    public ResponseEntity<List<CandidaturaResponseDto>> findAllByIdVaga(@PathVariable int idVaga) {
        List<CandidaturaResponseDto> candidaturas = service.findAllByIdVaga(idVaga);

        return candidaturas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(candidaturas);
    }
}
