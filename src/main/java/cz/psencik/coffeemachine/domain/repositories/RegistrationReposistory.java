package cz.psencik.coffeemachine.domain.repositories;

import cz.psencik.coffeemachine.domain.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationReposistory extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUsernameIgnoreCase(String username);
}
