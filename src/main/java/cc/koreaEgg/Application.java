package cc.koreaEgg;

import cc.koreaEgg.dao.UserDetailsServiceDAO;
import lombok.extern.slf4j.Slf4j;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "cc.koreaEgg")
public class Application implements WebMvcConfigurer {

  @Autowired
  private DataSource dataSource;

  @Value("${property.hello}")
  private String propertyHello;

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceDAO();
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home");
    registry.addViewController("/index").setViewName("home");
    registry.addViewController("/error").setViewName("error");
    registry.addViewController("/cast").setViewName("cast");
    registry.addViewController("/board").setViewName("board");
    registry.addViewController("/product").setViewName("product");
    registry.addViewController("/agent").setViewName("agent");
    registry.addViewController("/agentList").setViewName("agentList");
    registry.addViewController("/class").setViewName("class");
    registry.addViewController("/test").setViewName("test");
  }

  @Bean
  public CommandLineRunner runner() {

    return (a) -> {
      log.info(propertyHello);
    };
  }

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

  // TODO * Configuring this bean should not be needed once Spring Boot's Thymeleaf starter includes configuration
  // TODO   for thymeleaf-extras-springsecurity5 (instead of thymeleaf-extras-springsecurity4)
  @Bean
  public SpringSecurityDialect securityDialect() {
    return new SpringSecurityDialect();
  }

  // Used when launching as an executable jar or war
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
