package cz.psencik.coffeemachine.domain.repositories;

import cz.psencik.coffeemachine.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameIgnoreCase(String username);
}
