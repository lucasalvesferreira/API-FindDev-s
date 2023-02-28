package projetopi.finddevservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetopi.finddevservice.controllers.EmpresaController;
import projetopi.finddevservice.dtos.v1.request.EmpresaRequestDto;
import projetopi.finddevservice.dtos.v1.response.EmpresaResponseDto;
import projetopi.finddevservice.exceptions.RequiredExistingObjectException;
import projetopi.finddevservice.exceptions.RequiredObjectIsNullException;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.mapper.DozerMapper;
import projetopi.finddevservice.models.EmpresaModel;
import projetopi.finddevservice.models.PerfilModel;
import projetopi.finddevservice.repositories.EmpresaRepository;
import projetopi.finddevservice.repositories.PerfilRepository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;
    @Autowired
    private PerfilRepository perfilRepository;

    private final Logger logger = Logger.getLogger(EmpresaService.class.getName());

    public List<EmpresaResponseDto> findAll() {
        logger.info("Finding all Companys!");

        List<EmpresaResponseDto> person = DozerMapper.parseListObjects(
            repository.findAll(),
            EmpresaResponseDto.class
        );

        person.forEach(p -> {
                try {
                    p.add(linkTo(methodOn(EmpresaController.class).findById(p.getKey())).withSelfRel());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );

        return person;
    }

    public EmpresaResponseDto findById(UUID id) {
        logger.info("Finding a Company!");

        EmpresaModel entity = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id!")
        );

        EmpresaResponseDto dto = DozerMapper.parseObject(entity, EmpresaResponseDto.class);
        dto.add(
            linkTo(
                methodOn(EmpresaController.class)
                    .findById(id)
            ).withSelfRel()
        );

        return dto;
    }

    public EmpresaResponseDto create(EmpresaRequestDto person) throws Exception {
        logger.info("Checking existence!");

        if (existByEmail(person.getEmail())){
            logger.info("Email already in use!!");
            throw new RequiredExistingObjectException("Email already in use!");
        }
        if (existByCnpj(person.getCnpj())){
            logger.info("Cpf already in use!!");
            throw new RequiredExistingObjectException("Cnpj already in use!");
        }

        PerfilModel perfilModel = new PerfilModel();
        perfilModel.setTitulo("");
        perfilModel.setDescricao("");

        perfilRepository.save(perfilModel);

        logger.info("Create a Company!");

        EmpresaModel entity = DozerMapper.parseObject(person, EmpresaModel.class);
        entity.setPerfil(perfilModel);
        EmpresaResponseDto dto = DozerMapper.parseObject(repository.save(entity), EmpresaResponseDto.class);
        dto.add(
            linkTo(
                methodOn(EmpresaController.class)
                    .findById(dto.getKey())
            ).withSelfRel()
        );
        return dto;

    }

    public Boolean existByCnpj(String cnpj) {
        return repository.existsByCnpjIgnoreCase(cnpj);
    }

    public Boolean existByEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    public EmpresaResponseDto update(EmpresaRequestDto person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("updating a Company!");

        EmpresaModel entity = repository.findById(person.getKey()).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id!")
        );

        if (!entity.getEmail().equalsIgnoreCase(person.getEmail())) {
            if(existByEmail(person.getEmail())) throw new RequiredExistingObjectException("Email already in use!");

            entity.setEmail(person.getEmail().isEmpty() ? entity.getEmail() : person.getEmail());
        }

        if (!entity.getCnpj().equalsIgnoreCase(person.getCnpj())) {
            if(existByCnpj(person.getCnpj())) throw new RequiredExistingObjectException("Cnpj already in use!");

            entity.setCnpj(person.getCnpj().isEmpty() ? entity.getCnpj() : person.getCnpj());
        }

        entity.setNome(person.getNome().isEmpty() ? entity.getNome() : person.getNome());
        entity.setEstado(person.getEstado().isEmpty() ? entity.getEstado() : person.getEstado());
        entity.setCidade(person.getCidade().isEmpty() ? entity.getCidade() : person.getCidade());
        entity.setTelefone(person.getTelefone().isEmpty() ? entity.getTelefone() : person.getTelefone());
        entity.setBairro(person.getBairro().isEmpty() ? entity.getBairro() : person.getBairro());
        entity.setEndereco(person.getEndereco().isEmpty() ? entity.getEndereco() : person.getEndereco());

        EmpresaResponseDto dto = DozerMapper.parseObject(repository.save(entity), EmpresaResponseDto.class);
        dto.add(
            linkTo(
                methodOn(EmpresaController.class)
                    .findById(dto.getKey())
            ).withSelfRel()
        );

        return dto;
    }

    public void delete(UUID id) {
        logger.info("Deleting one Company!");
        EmpresaModel entity = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id!")
        );

        repository.delete(entity);
    }


}
