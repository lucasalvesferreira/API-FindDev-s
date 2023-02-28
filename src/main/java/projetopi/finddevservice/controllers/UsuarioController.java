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
import projetopi.finddevservice.dtos.v1.PerfilDto;
import projetopi.finddevservice.dtos.v1.request.DevelopRequestDto;
import projetopi.finddevservice.dtos.v1.request.DevelopStatusRequest;
import projetopi.finddevservice.dtos.v1.request.LoginDto;
import projetopi.finddevservice.dtos.v1.request.UsuarioProfileRequest;
import projetopi.finddevservice.exceptions.RequiredObjectIsNullException;
import projetopi.finddevservice.models.UsuarioModel;
import projetopi.finddevservice.services.UsuarioService;
import projetopi.finddevservice.util.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Users", description = "Endpoints for Managing users")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping(value = "/login")
    @Operation(
            summary = "Login by user", description = "Login by user  ",
            tags = {"Login"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioModel.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<UsuarioModel> login(@RequestBody LoginDto loginDto) {

        if (loginDto == null) throw new RequiredObjectIsNullException();
        return ResponseEntity.ok(service.login(loginDto));
    }

    @PutMapping(value = "/status-dev",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Updates a status ",
            description = "Updates a Developer by passing in a JSON, XML or YML representation of the Developer!",
            tags = {"Profile"},
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
    public ResponseEntity<String> changeStatusPerfil(@RequestBody DevelopStatusRequest statusRequest){

        return ResponseEntity.ok(service.changeStatus(statusRequest));
    }



    @GetMapping(value = "/verify-email")
    @Operation(
            summary = "Find Email", description = "Check if email already exists  ",
            tags = {"Validation"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Boolean.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Boolean> verifyEmail(@RequestBody String email) {
        if (email == null || email.equals("")) throw new RequiredObjectIsNullException();

        return ResponseEntity.ok(service.verifyEmail(email));
    }

    //    @PutMapping("/login/forgot-password")
//    public ResponseEntity<String> forgotPassword(){
//
//    }


    @GetMapping(value = "/all-profile", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Find all users profile ", description = "Find all users profile ",
            tags = {"Profile"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PerfilDto.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<PerfilDto>> findAllUserProfile() {
        return ResponseEntity.ok(service.findAllUserProfile());
    }

    @GetMapping(value = "/profile/{id}", produces = MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Find a Profile ", description = "Find a profile ",
            tags = {"Profile"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PerfilDto.class))
                    ),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PerfilDto> findProfileById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(service.findProfileById(id));
    }

    @PutMapping(value = "/profile-update",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Updates a user profile",
            description = "Updates a user profile by passing in a JSON, XML or YML representation of the Developer!",
            tags = {"Profile"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UsuarioProfileRequest.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<UsuarioModel> updateProfile(@RequestBody UsuarioProfileRequest profile) throws Exception {
        return ResponseEntity.ok(service.updateProfile(profile));
    }

}
