package cz.psencik.coffeemachine.process;

import cz.psencik.coffeemachine.domain.entities.CoffeeMachine;
import cz.psencik.coffeemachine.domain.entities.CoffeeOrder;
import cz.psencik.coffeemachine.domain.repositories.CoffeeMachineRepository;
import cz.psencik.coffeemachine.service.CoffeeOrderDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CoffeeProcessorService implements ApplicationListener<ContextClosedEvent> {
    private static final Logger log = LoggerFactory.getLogger(CoffeeProcessorService.class);

    @Autowired
    private CoffeeMachineRepository coffeeMachineRepository;

    @Autowired
    private CoffeeOrderDaoService coffeeOrderDaoService;

    private final ConcurrentHashMap<Long, CoffeeProcessor> coffeeProcessorsMap = new ConcurrentHashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void loadCoffeeMachines() {
        coffeeMachineRepository.findAll().forEach(m -> {
            var p = new CoffeeProcessor(m.getMachineId(), coffeeOrderDaoService);
            coffeeProcessorsMap.put(m.getMachineId(), p);
            p.start();
        });
    }

    public void notifyNewOrder(CoffeeOrder order) {
        Optional.ofNullable(coffeeProcessorsMap.get(order.getCoffeeMachine().getMachineId())).ifPresent(CoffeeProcessor::notifyNewOrder);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        for (CoffeeProcessor p : coffeeProcessorsMap.values()) {
            p.stop();
        }
        coffeeProcessorsMap.clear();
    }

    public void notifyNewCoffeeMachine(CoffeeMachine coffeeMachine) {
        var p = new CoffeeProcessor(coffeeMachine.getMachineId(), coffeeOrderDaoService);
        coffeeProcessorsMap.put(coffeeMachine.getMachineId(), p);
        p.start();
        log.info(String.format("started new coffee machine %s", coffeeMachine));
    }
}
