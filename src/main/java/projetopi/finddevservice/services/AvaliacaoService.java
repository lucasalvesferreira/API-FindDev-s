package projetopi.finddevservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetopi.finddevservice.controllers.DesenvolvedorController;
import projetopi.finddevservice.dtos.v1.request.NovaAvaliacaoRequestDto;
import projetopi.finddevservice.dtos.v1.response.AvaliacaoResponseDto;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.mapper.DozerMapper;
import projetopi.finddevservice.models.AvaliacaoModel;
import projetopi.finddevservice.models.UsuarioModel;
import projetopi.finddevservice.repositories.AvaliacaoRepository;
import projetopi.finddevservice.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AvaliacaoService {

    @Autowired
    AvaliacaoRepository avaliacaoRepository;
    @Autowired
    UsuarioRepository userRepository;
    private final Logger logger = Logger.getLogger(DesenvolvedorService.class.getName());

    public AvaliacaoResponseDto post(NovaAvaliacaoRequestDto avaliacao) {
        logger.info("Checking if Ids exist!");
        if (!userRepository.existsById(avaliacao.getIdAvaliado()))
            throw new ResourceNotFoundException("Avaliado not Found!");
        if (!userRepository.existsById(avaliacao.getIdAvaliador()))
            throw new ResourceNotFoundException("Avaliador not Found!");

        logger.info("creating assessment!");

        AvaliacaoModel novaAvaliacao = new AvaliacaoModel();
        UsuarioModel avaliado = userRepository.findById(avaliacao.getIdAvaliado()).get();
        UsuarioModel avaliador = userRepository.findById(avaliacao.getIdAvaliador()).get();

        novaAvaliacao.setIdAvaliado(avaliado.getId());
        novaAvaliacao.setIdAvaliador(avaliador.getId());
        novaAvaliacao.setNota(avaliacao.getNota());
        novaAvaliacao.setComentario(avaliacao.getComentario());
        avaliacaoRepository.save(novaAvaliacao);

        AvaliacaoResponseDto entity = new AvaliacaoResponseDto();
        entity.setIdAvaliacao(novaAvaliacao.getIdAvaliacao());
        entity.setIdAvaliado(avaliado.getId());
        entity.setIdAvaliador(avaliador.getId());
        entity.setNota(avaliacao.getNota());
        entity.setDataHoraAvaliacao(novaAvaliacao.getDataHoraAvaliacao());
        entity.setComentario(avaliacao.getComentario());
        return entity;
    }

    public List<AvaliacaoResponseDto> findAll() {

        logger.info("Finding all assessments!");
        return DozerMapper.parseListObjects(
                avaliacaoRepository.findAll(), AvaliacaoResponseDto.class);
    }

    public Optional<Double> getMediaAvaliacoes(UUID idUser) {
        if (!userRepository.existsById(idUser))
            throw new ResourceNotFoundException("User not Found!");

        // aqui criamos um LocalDateTime com o valor de 6 meses atrás
        LocalDateTime ha6Meses = LocalDateTime.now().minusMonths(6);

        return avaliacaoRepository.getMediaAvaliacoes(idUser, ha6Meses).isEmpty()
                ? Optional.of(0.0): avaliacaoRepository.getMediaAvaliacoes(idUser, ha6Meses);
    }

    public List<AvaliacaoResponseDto> getAssessmentByid(UUID idUser) {
        if (!userRepository.existsById(idUser))
            throw new ResourceNotFoundException("User not Found!");

        return DozerMapper.parseListObjects(
                avaliacaoRepository.findByIdAvaliado(idUser), AvaliacaoResponseDto.class);
    }
}
