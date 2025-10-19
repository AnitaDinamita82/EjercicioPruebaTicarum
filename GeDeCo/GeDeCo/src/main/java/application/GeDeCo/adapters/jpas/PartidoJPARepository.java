package application.GeDeCo.adapters.jpas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.GeDeCo.adapters.entitys.PartidoEntity;

@Repository
public interface PartidoJPARepository extends JpaRepository<PartidoEntity, Long> {

    List<PartidoEntity> findByCompeticion_Id(Long competicionId);

    void deleteByCompeticion_Id(Long competicionID);

}
