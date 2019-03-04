package cc.koreaEgg.handler;

import cc.koreaEgg.entity.User;
import cc.koreaEgg.service.AppService;
import cc.koreaEgg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mink721 on 2019-03-03.
 */
@Slf4j
@Component
public class LoginSuccessHandler  implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private AppService appService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        userService.countUpVisit( ((User)authentication.getPrincipal()).getId() );
        HttpSession session = request.getSession();
        session.setAttribute("priceCast", appService.selectPriceCast());
        response.sendRedirect("/");

    }

}
