package cc.koreaEgg;

import cc.koreaEgg.dao.UserDetailsServiceDAO;
import lombok.extern.slf4j.Slf4j;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAutoConfiguration
@Slf4j
public class Application extends WebMvcConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceDAO();
  }


  @Bean
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"), new ErrorPage(HttpStatus.FORBIDDEN, "/error"));
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home");
    registry.addViewController("/error").setViewName("error");
    registry.addViewController("/cast").setViewName("cast");
    registry.addViewController("/board").setViewName("board");
    registry.addViewController("/product").setViewName("product");
    registry.addViewController("/agent").setViewName("agent");
    registry.addViewController("/agentList").setViewName("agentList");
    registry.addViewController("/class").setViewName("class");
  }

  // Used when launching as an executable jar or war
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
