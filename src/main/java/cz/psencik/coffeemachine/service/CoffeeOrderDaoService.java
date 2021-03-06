package cz.psencik.coffeemachine.service;

import cz.psencik.coffeemachine.domain.entities.CoffeeOrder;
import cz.psencik.coffeemachine.domain.entities.CoffeeOrderState;
import cz.psencik.coffeemachine.domain.repositories.CoffeeOrderRepository;
import cz.psencik.coffeemachine.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CoffeeOrderDaoService {
    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    private EmailSender emailSender;

    @Transactional
    public CoffeeOrder findFirstOrder(Long machineId) {
        var p  = coffeeOrderRepository.findByCoffeeMachineMachineIdAndCoffeeOrderStateNot(machineId,  CoffeeOrderState.DONE, PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "orderId")));
        var o = p.hasContent() ? p.getContent().get(0) : null;
        if(o!=null && o.getUser().getAuthorities()!=null)
            o.getUser().getAuthorities().size();
        return o;
    }

    @Transactional
    public void saveCoffeeOrder(CoffeeOrder coffeeOrder) {
        coffeeOrderRepository.save(coffeeOrder);
        if(coffeeOrder.getCoffeeOrderState()==CoffeeOrderState.DONE) {
            emailSender.sendMail(coffeeOrder.getUser().getUsername(),
                    "Your coffee is ready",
                    String.format("Your coffee order is ready, coffee type %s, coffee machine: %s, floor: %d, kitchen: %s",
                            coffeeOrder.getCoffeeType(),
                            coffeeOrder.getCoffeeMachine().getDescription(),
                            coffeeOrder.getCoffeeMachine().getFloor(),
                            coffeeOrder.getCoffeeMachine().getKitchen())
            );
        }
    }
}
