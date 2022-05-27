package platform;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface CodeRepository extends CrudRepository<Code, UUID> {
    Code findByCodeId(UUID id);
    List<Code> findAll();
    List<Code> findByIsRestrictedFalse();
}
