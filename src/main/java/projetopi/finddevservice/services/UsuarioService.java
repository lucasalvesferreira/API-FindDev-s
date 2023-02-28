package projetopi.finddevservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetopi.finddevservice.controllers.UsuarioController;
import projetopi.finddevservice.dtos.v1.PerfilDto;
import projetopi.finddevservice.dtos.v1.request.DevelopStatusRequest;
import projetopi.finddevservice.dtos.v1.request.LoginDto;
import projetopi.finddevservice.dtos.v1.request.UsuarioProfileRequest;
import projetopi.finddevservice.exceptions.RequiredExistingObjectException;
import projetopi.finddevservice.exceptions.RequiredObjectIsNullException;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.mapper.DozerMapper;
import projetopi.finddevservice.models.DesenvolvedorModel;
import projetopi.finddevservice.models.PerfilModel;
import projetopi.finddevservice.models.UsuarioModel;
import projetopi.finddevservice.repositories.PerfilRepository;
import projetopi.finddevservice.repositories.UsuarioRepository;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    private final Logger logger = Logger.getLogger(DesenvolvedorService.class.getName());

    public UsuarioModel login(LoginDto loginDto) {
        if (loginDto == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Finding a User!");

        boolean email = verifyEmail(loginDto.getEmail());

        if (!email) {
            throw new RequiredExistingObjectException("Username Not Found!");
        }

        UsuarioModel user = userRepository.findByEmailIgnoreCase(loginDto.getEmail());

        if (!loginDto.getSenha().equals(user.recuperaSenha())) {
            throw new RequiredExistingObjectException("Invalid Password!");
        }

        return user;
    }

    public Boolean verifyEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public PerfilDto findProfileById(Integer id) {
        logger.info("Finding a profile!");

        PerfilModel entity = perfilRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id!")
        );

        PerfilDto dto = DozerMapper.parseObject(entity, PerfilDto.class);
        dto.add(
            linkTo(
                methodOn(UsuarioController.class)
                    .findProfileById(id)
            ).withSelfRel()
        );

        return dto;
    }

    public List<PerfilDto> findAllUserProfile() {
        logger.info("Finding all Devs!");

        List<PerfilDto> entity = DozerMapper.parseListObjects(perfilRepository.findAll(), PerfilDto.class);

        entity.forEach(p -> {
                try {
                    p.add(
                        linkTo(methodOn(UsuarioController.class).findProfileById(p.getIdPerfil())
                    ).withSelfRel());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );

        return entity;
    }

    public UsuarioModel updateProfile(UsuarioProfileRequest profile) {
        logger.info("Checking profile existence!");

        UsuarioModel entity = userRepository.findById(profile.getIdUsuario()).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id!")
        );

        entity.getPerfil().setTitulo(profile.getTitulo().isEmpty() ? entity.getPerfil().getTitulo() : profile.getTitulo() );
        entity.getPerfil().setDescricao(profile.getDescricao().isEmpty() ? entity.getPerfil().getDescricao() : profile.getDescricao());
        userRepository.save(entity);
        return entity;
    }

    public String changeStatus(DevelopStatusRequest statusRequest) {
        if (statusRequest == null){
            throw new RequiredObjectIsNullException();
        }

        logger.info("updating Status!");
        UsuarioModel entity = userRepository.findById(statusRequest.getIdUsuaio()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id!")
        );
        logger.info("updating Status!");
        entity.getPerfil().setStatus(statusRequest.getStatus());

        logger.info("Staus update!");
        return "Staus update!";
    }
}
