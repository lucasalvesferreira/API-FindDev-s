package projetopi.finddevservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import projetopi.finddevservice.models.ArquivosModel;

public interface ArquivoRepository extends JpaRepository<ArquivosModel, Integer> {

    @Modifying
    @Transactional
    @Query("update ArquivosModel a set a.foto = ?2 where a.id = ?1")
    void setFoto(Integer id, byte[] foto);

    @Query("select p.foto from ArquivosModel p where p.id = ?1")
    byte[] getFoto(Integer id);

    @Modifying
    @Transactional
    @Query("update ArquivosModel p set p.relatorioExcel = ?2 where p.id = ?1")
    void setRelatorio(Integer id, byte[] foto);

    @Query("select p.relatorioExcel from ArquivosModel p where p.id = ?1")
    byte[] getRelatorio(Integer id);
}
