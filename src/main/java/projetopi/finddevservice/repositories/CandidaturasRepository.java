package projetopi.finddevservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projetopi.finddevservice.models.Candidatura;

import java.util.List;

public interface CandidaturasRepository extends JpaRepository<Candidatura, Integer> {

    List<Candidatura> findAllByIdVaga(int idVaga);
}
