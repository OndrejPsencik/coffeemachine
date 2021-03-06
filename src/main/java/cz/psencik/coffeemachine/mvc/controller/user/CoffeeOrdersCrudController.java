package cz.psencik.coffeemachine.mvc.controller.user;

import cz.psencik.coffeemachine.domain.entities.CoffeeOrder;
import cz.psencik.coffeemachine.domain.repositories.CoffeeOrderRepository;
import cz.psencik.coffeemachine.domain.repositories.UserRepository;
import cz.psencik.coffeemachine.mvc.controller.common.CommonCrudController;
import cz.psencik.coffeemachine.mvc.exceptions.NotFoundException;
import cz.psencik.coffeemachine.mvc.rest.model.CoffeeOrderForm;
import cz.psencik.coffeemachine.mvc.rest.model.CoffeeOrderRestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/coffeeorders")
public class CoffeeOrdersCrudController extends CommonCrudController<CoffeeOrder, Long, CoffeeOrderRestObject, CoffeeOrderForm> {
    @Autowired
    private UserRepository userRepository;

    public CoffeeOrdersCrudController() {
        super(CoffeeOrder.class, CoffeeOrderRestObject.class, new String[]{"orderId"});
    }

    @Override
    protected void onCreate(CoffeeOrder entity, HttpServletRequest request) {
        entity.setUser(userRepository.findByUsernameIgnoreCase(request.getRemoteUser()).orElseThrow(NotFoundException::new));
    }

    @Override
    @GetMapping
    public List<CoffeeOrderRestObject> list(Pageable p, HttpServletRequest request) {
        return ((CoffeeOrderRepository)repository)
                .findByUserUsername(request.getRemoteUser(), p)
                .stream()
                .map(e -> mapper.convertValue(e, CoffeeOrderRestObject.class))
                .collect(Collectors.toList());
    }
}
