package cz.psencik.coffeemachine.mvc.controller.admin;

import cz.psencik.coffeemachine.domain.entities.User;
import cz.psencik.coffeemachine.mvc.controller.common.CommonCrudController;
import cz.psencik.coffeemachine.mvc.rest.model.UserRest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/user")
public class UsersController extends CommonCrudController<User, String, UserRest, UserRest> {
    public UsersController() {
        super(User.class, UserRest.class, new String[] {"username"});
    }
}
