package projetopi.finddevservice.repositories;

import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projetopi.finddevservice.models.AvaliacaoModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel,Integer> {


    @Query("select avg(am.nota) from AvaliacaoModel am where " +
            " am.idAvaliado = ?1 and am.dataHoraAvaliacao >= ?2 ")
    Optional<Double> getMediaAvaliacoes(UUID idUser, LocalDateTime aPartirDe);

    List<AvaliacaoModel> findByIdAvaliado (UUID uuid);


}
