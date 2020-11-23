package centene.github.io.centene_enrollee.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import centene.github.io.centene_enrollee.domain.Enrollee;


public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
    // add custom queries here
}
