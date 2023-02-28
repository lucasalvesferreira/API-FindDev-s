package projetopi.finddevservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projetopi.finddevservice.models.EmpresaModel;

import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository <EmpresaModel, UUID> {


    Boolean existsByEmailIgnoreCase(String email);
    Boolean existsByNomeIgnoreCase(String nome);
    Boolean existsByCnpjIgnoreCase(String cnpj);
    @Transactional
    @Modifying
    void deleteById (UUID uuid);

}
