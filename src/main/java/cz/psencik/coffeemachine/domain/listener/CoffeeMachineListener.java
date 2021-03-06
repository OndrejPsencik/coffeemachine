package cz.psencik.coffeemachine.domain.listener;

import cz.psencik.coffeemachine.domain.entities.CoffeeMachine;
import cz.psencik.coffeemachine.process.CoffeeProcessorService;
import cz.psencik.coffeemachine.service.ApplicationContextService;
import lombok.Getter;

import javax.persistence.PostPersist;

public class CoffeeMachineListener {
    @Getter(lazy = true)
    private final CoffeeProcessorService coffeeProcessorService = ApplicationContextService.getBean(CoffeeProcessorService.class);

    @PostPersist
    public void postPersist(CoffeeMachine coffeeMachine) {
        getCoffeeProcessorService().notifyNewCoffeeMachine(coffeeMachine);
    }
}
