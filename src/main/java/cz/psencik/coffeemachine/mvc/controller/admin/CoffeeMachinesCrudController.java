package cz.psencik.coffeemachine.mvc.controller.admin;

import cz.psencik.coffeemachine.domain.entities.CoffeeMachine;
import cz.psencik.coffeemachine.domain.repositories.CoffeeMachineRepository;
import cz.psencik.coffeemachine.mvc.controller.common.CommonCrudController;
import cz.psencik.coffeemachine.mvc.rest.model.CoffeeMachineRestObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/coffeemachine")
public class CoffeeMachinesCrudController extends CommonCrudController<CoffeeMachine, Long, CoffeeMachineRestObject, CoffeeMachineRestObject> {
    public CoffeeMachinesCrudController() {
        super(CoffeeMachine.class, CoffeeMachineRestObject.class, new String[] {"machineId"});
    }

    @Value("${limits.max-machines}")
    private Long maxMachines;

    @Override
    protected void onCreate(CoffeeMachine entity, HttpServletRequest request) {
        if(repository.count() >= maxMachines) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("you cannot create additional coffee machine, limit reached, limit: %d", maxMachines));
        }
        if(((CoffeeMachineRepository)repository).countByFloorAndKitchen(entity.getFloor(), entity.getKitchen()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "duplicate coffee machine for floor: " + entity.getFloor() + " and kitchen: " + entity.getKitchen());
        }
    }
}
