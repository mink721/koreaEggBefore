package cc.koreaEgg.config;

import cc.koreaEgg.handler.LoginFailureHandler;
import cc.koreaEgg.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private LoginSuccessHandler loginSuccessHandler;
  @Autowired
  private LoginFailureHandler loginFailureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
          .authorizeRequests()
            //static resources configuration
            .antMatchers("/resources/**", "/webjars/**", "/img/**").permitAll()
            //login page and registration end-point
            .antMatchers("/cast/**").hasAnyAuthority("ROLE_ADMIN,CAST_READ")
            .antMatchers("/priceInfo/**").hasAnyAuthority("ROLE_ADMIN,CAST_READ")
            .antMatchers("/contact/**").authenticated()
              //all other requests
            //.anyRequest().authenticated()
            .anyRequest().permitAll()
            .and()
          // login form configuration
          .formLogin().successHandler(loginSuccessHandler)
            .loginPage("/login")
            .failureUrl("/login?error")
            /*.defaultSuccessUrl("/")*/
            .permitAll()
            .and()
          //logout configuration
          .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .and()
              .csrf()
              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
            .passwordEncoder(delegatingPasswordEncoder());
  }

  @Bean
  public PasswordEncoder delegatingPasswordEncoder() {
    PasswordEncoder defaultEncoder = new StandardPasswordEncoder();
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("bcrypt", new BCryptPasswordEncoder());
    encoders.put("scrypt", new SCryptPasswordEncoder());

    DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder(
            "bcrypt", encoders);
    passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

    return passworEncoder;
  }

}
