package cz.psencik.coffeemachine.domain.repositories;

import cz.psencik.coffeemachine.domain.entities.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {
    Long countByFloorAndKitchen(int floor, String kitchen);
}
