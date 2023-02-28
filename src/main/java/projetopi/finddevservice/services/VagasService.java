package projetopi.finddevservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetopi.finddevservice.controllers.VagasController;
import projetopi.finddevservice.dtos.v1.request.ContratacaoRequest;
import projetopi.finddevservice.dtos.v1.request.FiltroRequest;
import projetopi.finddevservice.dtos.v1.request.VagaRequestDto;
import projetopi.finddevservice.dtos.v1.response.CandidaturaResponseDto;
import projetopi.finddevservice.dtos.v1.response.DevelopResponseDto;
import projetopi.finddevservice.dtos.v1.response.VagaResponseDto;
import projetopi.finddevservice.enums.FuncaoDev;
import projetopi.finddevservice.enums.SenioridadeDev;
import projetopi.finddevservice.exceptions.RequiredExistingObjectException;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.mapper.DozerMapper;
import projetopi.finddevservice.models.DesenvolvedorModel;
import projetopi.finddevservice.models.Vaga;
import projetopi.finddevservice.repositories.DesenvolvedorRepository;
import projetopi.finddevservice.repositories.VagasRepository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class VagasService {

    @Autowired
    private VagasRepository repository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CandidaturasService candidaturasService;

    @Autowired
    private DesenvolvedorRepository desenvolvedorRepository;

    private final Logger logger = Logger.getLogger(VagasService.class.getName());

    public VagaResponseDto create(VagaRequestDto vagaRequest) {
        UUID idEmpresa = vagaRequest.getIdEmpresa();
        logger.info("Buscando por empresa com id " + idEmpresa);

        empresaService.findById(idEmpresa);

        logger.info("Criando vaga para a empresa");

        Vaga vaga = DozerMapper.parseObject(vagaRequest, Vaga.class);

        VagaResponseDto vagaResponseDto = DozerMapper.parseObject(repository.save(vaga), VagaResponseDto.class);
        vagaResponseDto.add(
            linkTo(
                methodOn(VagasController.class)
                    .findById(vagaResponseDto.getKey())
            ).withSelfRel()
        );
        return vagaResponseDto;
    }

    public VagaResponseDto findById(int id) {
        logger.info("Consultando Vaga com id " + id);

        Vaga vagaEncontrada = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Id " + id + " não encontrado!")
        );

        VagaResponseDto vagaResponseDto = DozerMapper.parseObject(vagaEncontrada, VagaResponseDto.class);
        vagaResponseDto.setDesenvolvedor(vagaEncontrada.getDesenvolvedorContratado());
        vagaResponseDto.add(
            linkTo(
                methodOn(VagasController.class)
                    .findById(id)
            ).withSelfRel()
        );

        return vagaResponseDto;
    }

    public List<VagaResponseDto> findAll() {
        List<Vaga> allVagas = repository.findAll();
        List<VagaResponseDto> vagaResponseDto = vagaListToDtoList(repository.findAll());

        for (int i = 0; i < allVagas.size(); i++) {
            vagaResponseDto.get(i).setDesenvolvedor(allVagas.get(i).getDesenvolvedorContratado());
        }

        addLinkToList(vagaResponseDto);

        return vagaResponseDto;
    }

    public List<VagaResponseDto> findAllByIdEmpresa(UUID idEmpresa) {
        empresaService.findById(idEmpresa);

        logger.info("Buscando vagas da empresa");

        List<Vaga> allVagas = repository.findByIdEmpresa(idEmpresa);
        List<VagaResponseDto> vagaResponseDto = vagaListToDtoList(allVagas);

        for (int i = 0; i < allVagas.size(); i++) {
            vagaResponseDto.get(i).setDesenvolvedor(allVagas.get(i).getDesenvolvedorContratado());
        }

        addLinkToList(vagaResponseDto);

        return vagaResponseDto;
    }

    public List<VagaResponseDto> findAllByIdDesenvolvedor(UUID idDesenvolvedor) {
        desenvolvedorRepository.findById(idDesenvolvedor).orElseThrow(
            () -> new ResourceNotFoundException("Nenhum desenvolvedor encontrado")
        );

        logger.info("Buscando vagas do desenvolvedor");

        List<VagaResponseDto> vagaResponseDto = vagaListToDtoList(repository.findByDesenvolvedorContratado(idDesenvolvedor));

        addLinkToList(vagaResponseDto);

        return vagaResponseDto;
    }

    public List<VagaResponseDto> findAllByFiltros(String funcaoRequest, String senioridadeRequest) {
        logger.info("Buscando vagas filtradas por " + funcaoRequest + " e " + senioridadeRequest);

        List<Vaga> allVagas = repository.findByFuncaoAndSenioridade(
            FuncaoDev.valueOf(funcaoRequest),
            SenioridadeDev.valueOf(senioridadeRequest)
        );

        List<VagaResponseDto> vagaResponseDto = vagaListToDtoList(allVagas);

        addLinkToList(vagaResponseDto);

        return vagaResponseDto;
    }

    public VagaResponseDto contratar(ContratacaoRequest contratacaoRequest) {
        int idVaga = contratacaoRequest.getIdVaga();
        UUID idDesenvolvedor = contratacaoRequest.getIdDesenvolvedor();

        DesenvolvedorModel desenvolvedor = desenvolvedorRepository.findById(idDesenvolvedor).orElseThrow(
            () -> new ResourceNotFoundException("Nenhum desenvolvedor encontrado")
        );

        Vaga vaga = DozerMapper.parseObject(findById(idVaga), Vaga.class);

        logger.info("Contratando desenvolvedor!");

        vaga.setDesenvolvedorContratado(desenvolvedor);

        VagaResponseDto vagaResponseDto = DozerMapper.parseObject(repository.save(vaga), VagaResponseDto.class);
        vagaResponseDto.setDesenvolvedor(desenvolvedor);
        vagaResponseDto.add(
            linkTo(
                methodOn(VagasController.class)
                    .findById(vagaResponseDto.getKey())
            ).withSelfRel()
        );

        return vagaResponseDto;
    }

    public void deletarVaga(int idVaga) {
        logger.info("Deletando vaga com id " + idVaga);

        Vaga vaga = repository.findById(idVaga).orElseThrow(
            () -> new ResourceNotFoundException("Vaga com id " + idVaga + " não encontrada")
        );

        repository.delete(vaga);
    }

    private List<CandidaturaResponseDto> findAllCandidaturas(int idVaga) {
        return candidaturasService.findAllByIdVaga(idVaga);
    }

    private List<VagaResponseDto> vagaListToDtoList(List<Vaga> vagasEntity) {
        return vagasEntity
            .stream()
            .map(v -> DozerMapper.parseObject(v, VagaResponseDto.class))
            .collect(Collectors.toList());
    }

    private void addLinkToList(List<VagaResponseDto> vagaResponseDto) {
        vagaResponseDto.forEach(v -> {
                try {
                    v.setCandidaturas(findAllCandidaturas(v.getKey()));
                    v.add(
                        linkTo(
                            methodOn(VagasController.class)
                                .findById(v.getKey())
                        ).withSelfRel()
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }
}
