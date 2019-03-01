package cc.koreaEgg;

import lombok.extern.slf4j.Slf4j;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "cc.koreaEgg")
public class Application implements WebMvcConfigurer{

  @Value("${property.hello}")
  private String propertyHello;


  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home");
    registry.addViewController("/index").setViewName("home");
    registry.addViewController("/error").setViewName("error");
    registry.addViewController("/cast").setViewName("cast");
    registry.addViewController("/boardList").setViewName("boardList");
    registry.addViewController("/agent").setViewName("agent");
    registry.addViewController("/agentList").setViewName("agentList");
    registry.addViewController("/class").setViewName("class");
    registry.addViewController("/test").setViewName("test");
    registry.addViewController("/myPage").setViewName("myPage");
    registry.addViewController("/registCast").setViewName("registCast");
    registry.addViewController("/egg").setViewName("egg");
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages");
    messageSource.setCacheSeconds(10); //reload messages every 10 seconds
    return messageSource;
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

  @Bean
  public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(redirectConnector());
    return tomcat;
  }

  private Connector redirectConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(7777);
    connector.setSecure(false);
    connector.setRedirectPort(7776);

    return connector;
  }

  // Used when launching as an executable jar or war
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
