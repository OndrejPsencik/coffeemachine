package cz.psencik.coffeemachine.mvc.controller.publ;

import cz.psencik.coffeemachine.domain.entities.Registration;
import cz.psencik.coffeemachine.domain.entities.User;
import cz.psencik.coffeemachine.domain.repositories.RegistrationReposistory;
import cz.psencik.coffeemachine.domain.repositories.UserRepository;
import cz.psencik.coffeemachine.email.EmailSender;
import cz.psencik.coffeemachine.mvc.controller.common.CommonController;
import cz.psencik.coffeemachine.mvc.exceptions.NotFoundException;
import cz.psencik.coffeemachine.mvc.rest.model.RegistrationConfirmationForm;
import cz.psencik.coffeemachine.mvc.rest.model.RegistrationForm;
import cz.psencik.coffeemachine.util.LambdaUtils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/account")
public class RegistrationController {
    @Autowired
    private RegistrationReposistory registrationReposistory;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("register")
    public RegistrationForm register(@Valid @ModelAttribute RegistrationForm registrationForm, Errors errors) {
        CommonController.checkErrors(errors);
        userRepository.findByUsernameIgnoreCase(registrationForm.getEmail()).ifPresent(u -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user already registered");});
        registrationReposistory.findByUsernameIgnoreCase(registrationForm.getEmail()).ifPresentOrElse(LambdaUtils::emptyAction, () -> {
            var registration = new Registration();
            registration.setUsername(registrationForm.getEmail());
            registration.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
            registration.setValidationCode(new RandomString(4).nextString());
            registrationReposistory.save(registration);
            emailSender.sendMail(registrationForm.getEmail(),
                    "Registration confirmation",
                    String.format("Welcome to Coffee Machine, confirmation code for registration is %s", registration.getValidationCode()));
        });
        return registrationForm;
    }

    @Transactional
    @PostMapping("confirm-registration")
    public RegistrationConfirmationForm confirmRegistration(@Valid @ModelAttribute RegistrationConfirmationForm registrationForm, Errors errors) {
        CommonController.checkErrors(errors);
        var registration = registrationReposistory.findByUsernameIgnoreCase(registrationForm.getEmail()).orElseThrow(NotFoundException::new);
        if (registration.getValidationCode().equals(registrationForm.getCode())) {
            var newUser = new User();
            newUser.setEnabled(true);
            newUser.setUsername(registration.getUsername());
            newUser.setPassword(registration.getPassword());
            newUser.setAuthorities(Collections.singletonList("ROLE_USER"));
            userRepository.save(newUser);
            registrationReposistory.delete(registration);
            emailSender.sendMail(registrationForm.getEmail(), "Account created", "Welcome to Coffee Machine, you account has been created.");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect verification code");
        }
        return registrationForm;
    }
}
