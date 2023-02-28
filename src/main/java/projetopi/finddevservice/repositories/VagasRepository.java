package projetopi.finddevservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projetopi.finddevservice.enums.FuncaoDev;
import projetopi.finddevservice.enums.SenioridadeDev;
import projetopi.finddevservice.models.Vaga;

import java.util.List;
import java.util.UUID;

public interface VagasRepository extends JpaRepository<Vaga, Integer> {


    List<Vaga> findByIdEmpresa(UUID id);

    List<Vaga> findByDesenvolvedorContratado(UUID id);

    List<Vaga> findByFuncaoAndSenioridade(FuncaoDev funcao, SenioridadeDev senioridade);
}

