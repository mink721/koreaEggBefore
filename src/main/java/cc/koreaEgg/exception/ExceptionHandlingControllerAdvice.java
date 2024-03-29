package cc.koreaEgg.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;

/**

 * @author Paul Chapman
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlingControllerAdvice {

	/**
	 * Convert a predefined exception to an HTTP Status code
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")// 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		log.error("Request raised a DataIntegrityViolationException");
		// Nothing to do
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access denied")
	@ExceptionHandler(AccessDeniedException.class)
	public void FORBIDDEN() {
		log.error("Request raised a AccessDeniedException");
	}

	/**
	 * Convert a predefined exception to an HTTP Status code and specify the
	 * name of a specific view that will be used to display the error.
	 * 
	 * @return Exception view.
	 */
	//@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public String databaseError(Exception exception) {
		// Nothing to do. Return value 'databaseError' used as logical view name
		// of an error page, passed to view-resolver(s) in usual way.
		log.error("Request raised " + exception.getClass().getSimpleName());
		return "databaseError";
	}

	/**
	 * Demonstrates how to take total control - setup a model, add useful
	 * information and return the "support" view name. This method explicitly
	 * creates and returns
	 * 
	 * @param req
	 *            Current HTTP request.
	 * @param exception
	 *            The exception thrown - always {@link SupportInfoException}.
	 * @return The model and view used by the DispatcherServlet to generate
	 *         output.
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception exception)
			throws Exception {

		// Rethrow annotated exceptions or they will be processed here instead.
		if (AnnotationUtils.findAnnotation(exception.getClass(),
				ResponseStatus.class) != null)
			throw exception;

		log.error("Request: " + req.getRequestURI() + " raised " + exception.getCause().toString());

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("status", 500);

		mav.setViewName("error");
		return mav;
	}
}