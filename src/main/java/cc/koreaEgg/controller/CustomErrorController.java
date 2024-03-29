package cc.koreaEgg.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by mink721 on 2019-03-13.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /*TODO 로그좀 찍기*/
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/error-403";
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/error-500";
            }
        }
        return "error/error-500";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
