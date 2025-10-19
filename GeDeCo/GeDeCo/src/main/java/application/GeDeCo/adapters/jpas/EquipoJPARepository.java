package application.GeDeCo.adapters.jpas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.GeDeCo.adapters.entitys.EquipoEntity;

/* Equipo JPA Repository */

@Repository
public interface EquipoJPARepository extends JpaRepository<EquipoEntity, Long> {

     Optional<EquipoEntity> findByNombre(String nombre); // Nos devolverá el equipo asociado al nombre que se le pase si
                                                         // exite.

     List<EquipoEntity> findEquiposByCompeticiones_Id(Long idCompeticion);

}
