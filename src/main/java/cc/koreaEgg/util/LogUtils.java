package cc.koreaEgg.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Slf4j
public class LogUtils {

	public static boolean bindingResult(BindingResult result){
		if(result.hasErrors()){

			StackTraceElement e = Thread.currentThread().getStackTrace()[2];
			String className = e.getClassName();
			String methodName = e.getMethodName();

			log.warn( className +"."+ methodName + " : "+result.getObjectName() + " validation error");
			for (FieldError error : result.getFieldErrors()) {
				log.debug(error.getField() + " : " +  error.getCode());
			}
			return true;
		}else {
			return false;
		}
	}

}