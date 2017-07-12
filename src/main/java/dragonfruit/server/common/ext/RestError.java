package dragonfruit.server.common.ext;

/**
 * 异常返回前端对象
 * 
 * Created by xuyh at 2017年7月12日 下午3:29:18.
 */
public class RestError {
	private String errCode;//错误码
	private String message;//错误消息

	public RestError() {
		super();
	}

	public RestError(String errCode, String message) {
		super();
		this.errCode = errCode;
		this.message = message;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
