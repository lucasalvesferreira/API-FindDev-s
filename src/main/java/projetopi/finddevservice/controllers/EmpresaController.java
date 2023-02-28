package projetopi.finddevservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import projetopi.finddevservice.dtos.v1.request.EmpresaRequestDto;
import projetopi.finddevservice.dtos.v1.request.DevelopRequestDto;
import projetopi.finddevservice.dtos.v1.response.EmpresaResponseDto;
import projetopi.finddevservice.services.EmpresaService;
import projetopi.finddevservice.services.ManipulacaoArquivo;
import projetopi.finddevservice.util.MediaType;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/empresa")
@Tag(name = "Company", description = "Endpoints for Managing Companys")
public class EmpresaController {

    @Autowired
    private EmpresaService service;
    @Autowired
    private ManipulacaoArquivo manipulacaoArquivo;

    @GetMapping(value = "/validation")
    @Operation(
            summary = "Find cnpj", description = "Check if cnpj already exists  ",
            tags = {"Validation"},
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
    public ResponseEntity<Boolean> existsCnpj(@RequestBody String cnpj) {
        return ResponseEntity.ok(service.existByCnpj(cnpj));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Find all Companys ", description = "Find all Companys ",
            tags = {"Company"},
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
    public ResponseEntity<List<EmpresaResponseDto>> findAllDevs() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Finds a Company ", description = "Finds a Company ",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = DevelopRequestDto.class))
                    ),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<EmpresaResponseDto> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
            summary = "Adds a new Company",
            description = "Adds a new Company by passing in a JSON, XML or YML representation of the Company!",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = DevelopRequestDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EmpresaResponseDto> post(@RequestBody @Valid EmpresaRequestDto dev) throws Exception {
        return ResponseEntity.ok(service.create(dev));
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
            summary = "Updates a Company",
            description = "Updates a Company by passing in a JSON, XML or YML representation of the Company!",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = DevelopRequestDto.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<EmpresaResponseDto> update(@RequestBody EmpresaRequestDto person) {
        return ResponseEntity.ok(service.update(person));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Deletes a Company",
            description = "Deletes a Company by passing in a JSON, XML or YML representation of the Company!",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping(value = "/relatorio-txt/{id}", produces = "text/csv")
    @Operation(
            summary = "Generate txt file", description = "Generate txt file",
            tags = {"Txt"},
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
    public ResponseEntity<String> geraTxt(@PathVariable(value = "id") UUID id) {

        String relatorio = manipulacaoArquivo.gravaArquivoTxt(id);

        return ResponseEntity.status(200).header("content-disposition", "attachment; filename=\"vaga.txt\"").body(relatorio);

    }


}
