package cz.psencik.coffeemachine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .headers()
                    .frameOptions()
                        .sameOrigin();
        http.httpBasic();
        http.authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/account/profile").hasRole("USER")
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
    }

    @Autowired
    public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) throws Exception {
        try(var c = dataSource.getConnection();
            var rs = c.getMetaData().getTables(null, "", "USERS", null);
            var rs2 = c.getMetaData().getTables(null, "", "users", null)) {
            if (rs.first() || rs2.first()) {
                builder.jdbcAuthentication().dataSource(dataSource).passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
            } else {
                builder.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
            }
        }
    }
}
