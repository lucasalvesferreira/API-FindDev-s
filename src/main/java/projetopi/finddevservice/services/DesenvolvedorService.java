package projetopi.finddevservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetopi.finddevservice.controllers.DesenvolvedorController;
import projetopi.finddevservice.dtos.v1.request.DevelopRequestDto;
import projetopi.finddevservice.dtos.v1.request.DevelopStatusRequest;
import projetopi.finddevservice.dtos.v1.response.DevelopResponseDto;
import projetopi.finddevservice.exceptions.RequiredExistingObjectException;
import projetopi.finddevservice.exceptions.RequiredObjectIsNullException;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.mapper.DozerMapper;
import projetopi.finddevservice.models.DesenvolvedorModel;
import projetopi.finddevservice.models.PerfilModel;
import projetopi.finddevservice.repositories.DesenvolvedorRepository;
import projetopi.finddevservice.repositories.PerfilRepository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class DesenvolvedorService {

    @Autowired
    private DesenvolvedorRepository repository;
    @Autowired
    private PerfilRepository perfilRepository;

    private final Logger logger = Logger.getLogger(DesenvolvedorService.class.getName());

    public List<DevelopResponseDto> findAll() {
        logger.info("Finding all Devs!");

        List<DevelopResponseDto> person = DozerMapper.parseListObjects(
            repository.findAll(),
            DevelopResponseDto.class
        );

        person.forEach(p -> {
                try {
                    p.add(
                        linkTo(methodOn(DesenvolvedorController.class).findById(p.getKey())).withSelfRel()
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );

        return person;
    }

    public DevelopResponseDto findById(UUID id) {
        logger.info("Finding a Dev!");
        DesenvolvedorModel entity = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id!")
        );
        DevelopResponseDto dto = DozerMapper.parseObject(entity, DevelopResponseDto.class);
        dto.add(
            linkTo(
                methodOn(DesenvolvedorController.class)
                    .findById(id)
            ).withSelfRel()
        );

        return dto;
    }

    public DevelopResponseDto create(DevelopRequestDto person) {
        logger.info("Checking existence!");

        if (existByEmail(person.getEmail())){
            logger.info("Email already in use!!");
            throw new RequiredExistingObjectException("Email already in use!");
        }
        if (existByCpf(person.getCpf())){
            logger.info("Cpf already in use!!");
            throw new RequiredExistingObjectException("Cpf already in use!");
        }

        PerfilModel perfilModel = new PerfilModel();
        perfilModel.setTitulo("");
        perfilModel.setDescricao("");
        perfilRepository.save(perfilModel);

        logger.info("Create a Dev!");

        DesenvolvedorModel entity = DozerMapper.parseObject(person, DesenvolvedorModel.class);
        entity.setPerfil(perfilModel);

        DevelopResponseDto dto = DozerMapper.parseObject(repository.save(entity), DevelopResponseDto.class);
        dto.add(
            linkTo(
                methodOn(DesenvolvedorController.class)
                    .findById(dto.getKey())
            ).withSelfRel()
        );
        logger.info("Dev successfully created!!");
        return dto;
    }

    public Boolean existByCpf(String cnpj) {
        return repository.existsByCpf(cnpj);
    }

    public Boolean existByEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    public DevelopResponseDto update(DevelopRequestDto person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("updating a Dev!");
        DesenvolvedorModel entity = repository.findById(person.getKey()).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id!")
        );

        if (!entity.getEmail().equalsIgnoreCase(person.getEmail())) {
            if (existByEmail(person.getEmail())) throw new RequiredExistingObjectException("Email already in use!");
            entity.setEmail(person.getEmail().isEmpty() ? entity.getEmail() : person.getEmail());
        }
        if (!entity.getCpf().equalsIgnoreCase(person.getCpf())) {
            if (existByCpf(person.getCpf())) throw new RequiredExistingObjectException("Cpf already in use!");
            entity.setCpf(person.getCpf().isEmpty() ? entity.getCpf() : person.getCpf());
        }

        entity.setNome(person.getNome().isEmpty() ? entity.getNome() : person.getNome());
        entity.setEstado(person.getEstado().isEmpty() ? entity.getEstado() : person.getEstado());
        entity.setCidade(person.getCidade().isEmpty() ? entity.getCidade() : person.getCidade());
        entity.setTelefone(person.getTelefone().isEmpty() ? entity.getTelefone() : person.getTelefone());

        DevelopResponseDto dto = DozerMapper.parseObject(repository.save(entity), DevelopResponseDto.class);
        dto.add(
            linkTo(
                methodOn(DesenvolvedorController.class)
                    .findById(dto.getKey())
            ).withSelfRel()
        );

        return dto;
    }

    public void delete(UUID id) {
        logger.info("Deleting one dev!");

        DesenvolvedorModel entity = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id!")
        );

        repository.delete(entity);
    }


}
