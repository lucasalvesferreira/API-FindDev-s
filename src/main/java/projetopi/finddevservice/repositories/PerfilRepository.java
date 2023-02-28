package projetopi.finddevservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projetopi.finddevservice.models.PerfilModel;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PerfilRepository extends JpaRepository<PerfilModel,Integer> {


}
