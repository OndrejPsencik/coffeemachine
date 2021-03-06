package cz.psencik.coffeemachine.mvc.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.psencik.coffeemachine.domain.entities.CoffeeOrderState;
import cz.psencik.coffeemachine.domain.entities.CoffeeType;
import lombok.Data;

@Data
public class CoffeeOrderRestObject implements CommonRestObject<Long>{
    private Long orderId;

    private CoffeeMachineRestObject coffeeMachine;

    private CoffeeOrderState coffeeOrderState;

    private CoffeeType coffeeType;

    @Override
    @JsonIgnore
    public Long getId() {
        return orderId;
    }
}
