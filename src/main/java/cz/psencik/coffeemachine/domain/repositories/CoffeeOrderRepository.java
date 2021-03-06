package cz.psencik.coffeemachine.domain.repositories;

import cz.psencik.coffeemachine.domain.entities.CoffeeOrder;
import cz.psencik.coffeemachine.domain.entities.CoffeeOrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
    Page<CoffeeOrder> findByCoffeeMachineMachineIdAndCoffeeOrderStateNot(Long machineId, CoffeeOrderState coffeeOrderState, Pageable p);
    Page<CoffeeOrder> findByUserUsername(String username, Pageable p);
}
