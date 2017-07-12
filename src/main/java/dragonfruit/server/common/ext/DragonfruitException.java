package dragonfruit.server.common.ext;

/**
 * 统一异常对象
 * 
 * Created by xuyh at 2017年7月12日 下午3:42:00.
 */
public class DragonfruitException extends Exception {
	private static final long serialVersionUID = 1L;

	private Object[] params;

	public DragonfruitException(String message, String... msgParam) {
		super(message);
		this.params = msgParam;
	}

	public DragonfruitException(String message, Throwable cause) {
		super(message, cause);
	}

	public DragonfruitException(Throwable cause) {
		super(cause);
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
