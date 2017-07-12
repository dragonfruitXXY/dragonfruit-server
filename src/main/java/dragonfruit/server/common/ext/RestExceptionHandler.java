package dragonfruit.server.common.ext;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import xuyihao.i18n.I18nContext;
import xuyihao.i18n.I18nUtils;

/**
 * 统一处理接口抛出的异常
 * 
 * Created by xuyh at 2017年7月12日 下午3:05:38.
 */
public class RestExceptionHandler implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(
						new RestError(String.valueOf(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()), getMessage(exception)))
				.type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * 从异常中获取对应语言的消息
	 * 
	 * @return
	 */
	private String getMessage(Throwable throwable) {
		String message;
		String lang = I18nContext.getLanguage();
		if (lang == null || !I18nUtils.hasLanguage(lang)) {
			lang = I18nUtils.getDefaultLanguage(); // 默认语言
		}
		if (throwable instanceof DragonfruitException) {
			DragonfruitException dragonfruitException = (DragonfruitException) throwable;
			String errorCode = dragonfruitException.getMessage();
			message = I18nUtils.getMessage(lang, errorCode, dragonfruitException.getParams());
			if (message == null || message.equals(""))
				message = errorCode;
		} else {
			message = throwable.getMessage();
		}
		return message;
	}
}
