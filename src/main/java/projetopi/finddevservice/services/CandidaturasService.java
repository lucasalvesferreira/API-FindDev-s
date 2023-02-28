package projetopi.finddevservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetopi.finddevservice.controllers.CandidaturasController;
import projetopi.finddevservice.dtos.v1.request.CandidaturaRequestDto;
import projetopi.finddevservice.dtos.v1.response.CandidaturaResponseDto;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.mapper.DozerMapper;
import projetopi.finddevservice.models.Candidatura;
import projetopi.finddevservice.models.DesenvolvedorModel;
import projetopi.finddevservice.models.Vaga;
import projetopi.finddevservice.repositories.CandidaturasRepository;
import projetopi.finddevservice.repositories.DesenvolvedorRepository;
import projetopi.finddevservice.repositories.VagasRepository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CandidaturasService {

    @Autowired
    CandidaturasRepository repository;

    @Autowired
    VagasRepository vagasRepository;

    @Autowired
    DesenvolvedorRepository desenvolvedorRepository;

    private final Logger logger = Logger.getLogger(VagasService.class.getName());

    public CandidaturaResponseDto create(CandidaturaRequestDto candidaturaRequest) {
        int idVaga = candidaturaRequest.getIdVaga();
        UUID idDesenvolvedor = candidaturaRequest.getIdDesenvolvedor();

        logger.info("Buscando vaga com id " + idVaga);
        Vaga vaga = vagasRepository.findById(idVaga).orElseThrow(
            () -> new ResourceNotFoundException("Nenhuma vaga com id " + idVaga + " encontrada!")
        );

        logger.info("Buscando desenvolvedor com id " + idDesenvolvedor);
        DesenvolvedorModel desenvolvedor = desenvolvedorRepository.findById(idDesenvolvedor).orElseThrow(
            () -> new ResourceNotFoundException("Nenhum desenvolvedor com id " + idDesenvolvedor + " encontrado!")
        );

        logger.info("Criando Candidatura!");

        Candidatura candidaturaEntity = DozerMapper.parseObject(candidaturaRequest, Candidatura.class);

        CandidaturaResponseDto candidaturaResponse = DozerMapper.parseObject(
            repository.save(candidaturaEntity),
            CandidaturaResponseDto.class
        );
        candidaturaResponse.setDesenvolvedor(desenvolvedor);
        candidaturaResponse.add(
            linkTo(
                methodOn(CandidaturasController.class)
                    .findById(candidaturaResponse.getKey())
            ).withSelfRel()
        );

        return candidaturaResponse;
    }

    public CandidaturaResponseDto findById(int idCandidatura) {
        logger.info("Consultando candidatura com id " + idCandidatura);

        Candidatura candidaturaEncontrada = repository.findById(idCandidatura).orElseThrow(
            () -> new ResourceNotFoundException("Candidatura com id " + idCandidatura + " não encontrada!")
        );

        CandidaturaResponseDto candidaturaResponse = DozerMapper.parseObject(
            candidaturaEncontrada,
            CandidaturaResponseDto.class
        );
        candidaturaResponse.add(
            linkTo(
                methodOn(CandidaturasController.class)
                    .findById(candidaturaResponse.getKey())
            ).withSelfRel()
        );

        return candidaturaResponse;
    }

    public List<CandidaturaResponseDto> findAll() {
        logger.info("Consultando todas as candidaturas");

        List<CandidaturaResponseDto> candidaturas = repository.findAll()
            .stream()
            .map(c -> DozerMapper.parseObject(c, CandidaturaResponseDto.class))
            .collect(Collectors.toList());

        candidaturas.forEach(c -> {
            try {
                c.add(
                    linkTo(
                        methodOn(CandidaturasController.class)
                            .findById(c.getKey())
                    ).withSelfRel()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return candidaturas;
    }

    public List<CandidaturaResponseDto> findAllByIdVaga(int idVaga) {
        logger.info("Consultando candidaturas da vaga");

        List<CandidaturaResponseDto> candidaturas = repository.findAllByIdVaga(idVaga)
            .stream()
            .map(c -> DozerMapper.parseObject(c, CandidaturaResponseDto.class))
            .collect(Collectors.toList());

        candidaturas.forEach(c -> {
            try {
                c.add(
                    linkTo(
                        methodOn(CandidaturasController.class)
                            .findById(c.getKey())
                    ).withSelfRel()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return candidaturas;
    }
}
