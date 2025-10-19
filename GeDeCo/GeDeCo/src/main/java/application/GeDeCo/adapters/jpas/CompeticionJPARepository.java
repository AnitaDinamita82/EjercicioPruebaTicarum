package application.GeDeCo.adapters.jpas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.GeDeCo.adapters.entitys.CompeticionEntity;

/* Competicion JPA Repository */
@Repository
public interface CompeticionJPARepository extends JpaRepository<CompeticionEntity, Long> {

    Optional<CompeticionEntity> findByNombre(String nombre); // Nos devlverá la competicion asociada al nombre de la
                                                             // competicion que se le pase si existe

    Optional<CompeticionEntity> findByDeporte(String deporte); // Nos devolverá la competicion asociada al deporte que
                                                               // se le pase si existe

}
