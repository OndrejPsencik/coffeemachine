package cz.psencik.coffeemachine.mvc.controller.user;

import cz.psencik.coffeemachine.domain.entities.CoffeeMachine;
import cz.psencik.coffeemachine.mvc.controller.common.CommonCrudController;
import cz.psencik.coffeemachine.mvc.rest.model.CoffeeMachineRestObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user/coffeemachine")
public class CoffeeMachinesUserCrudController extends CommonCrudController<CoffeeMachine, Long, CoffeeMachineRestObject, CoffeeMachineRestObject> {
    public CoffeeMachinesUserCrudController() {
        super(CoffeeMachine.class, CoffeeMachineRestObject.class, new String[] {"machineId"});
    }

    @Override
    public CoffeeMachineRestObject update(Long entityId, @Valid CoffeeMachineRestObject entity, Errors errors) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public CoffeeMachineRestObject create(@Valid CoffeeMachineRestObject entity, Errors errors, HttpServletRequest request) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
