package cz.psencik.coffeemachine.mvc.controller.admin;

import cz.psencik.coffeemachine.domain.entities.CoffeeMachine;
import cz.psencik.coffeemachine.mvc.controller.common.CommonCrudController;
import cz.psencik.coffeemachine.mvc.rest.model.CoffeeMachineRestObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/coffeemachine")
public class CoffeeMachinesCrudController extends CommonCrudController<CoffeeMachine, Long, CoffeeMachineRestObject, CoffeeMachineRestObject> {
    public CoffeeMachinesCrudController() {
        super(CoffeeMachine.class, CoffeeMachineRestObject.class, new String[] {"machineId"});
    }
}
