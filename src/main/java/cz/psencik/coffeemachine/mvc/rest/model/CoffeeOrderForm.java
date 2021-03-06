package cz.psencik.coffeemachine.mvc.rest.model;

import cz.psencik.coffeemachine.domain.entities.CoffeeMachine;
import cz.psencik.coffeemachine.domain.entities.CoffeeType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CoffeeOrderForm {
    @NotNull
    private CoffeeMachine coffeeMachine;

    @NotNull
    private CoffeeType coffeeType;
}
