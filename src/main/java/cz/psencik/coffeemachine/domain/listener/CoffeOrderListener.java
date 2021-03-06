package cz.psencik.coffeemachine.domain.listener;

import cz.psencik.coffeemachine.domain.entities.CoffeeOrder;
import cz.psencik.coffeemachine.domain.entities.CoffeeOrderState;
import cz.psencik.coffeemachine.process.CoffeeProcessorService;
import cz.psencik.coffeemachine.service.ApplicationContextService;
import lombok.Getter;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.PrePersist;

public class CoffeOrderListener {
    @Getter(lazy = true)
    private final CoffeeProcessorService coffeeProcessorService = ApplicationContextService.getBean(CoffeeProcessorService.class);

    @PrePersist
    public void prePersist(CoffeeOrder coffeeOrder) {
        coffeeOrder.setCoffeeOrderState(CoffeeOrderState.NEW);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                getCoffeeProcessorService().notifyNewOrder(coffeeOrder);
            }
        });
    }
}
