package cz.psencik.coffeemachine.lifecycle;

import cz.psencik.coffeemachine.domain.entities.User;
import cz.psencik.coffeemachine.domain.repositories.UserRepository;
import cz.psencik.coffeemachine.util.LambdaUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Arrays;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("admin")
public class InitialUserCheck {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Setter private String username;

    @Setter private String password;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void checkInitialUser() {
        userRepository.findByUsernameIgnoreCase(username).ifPresentOrElse(LambdaUtils::emptyAction,
                () -> {
                    var u = new User();
                    u.setUsername(username);
                    u.setPassword(passwordEncoder.encode(password));
                    u.setAuthorities(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
                    u.setEnabled(true);
                    userRepository.save(u);
                }
        );
    }
}
