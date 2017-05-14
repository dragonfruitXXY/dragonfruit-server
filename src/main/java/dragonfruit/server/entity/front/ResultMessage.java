package dragonfruit.server.entity.front;

/**
 * 返回客户端的封装数据结构
 * 
 * Created by Xuyh at 2017年3月22日 下午9:34:59.
 */
public class ResultMessage {
	private boolean result;// 返回结果
	private Object data;// 返回数据
	private String message;// 返回消息

	public ResultMessage(boolean result, Object data, String message) {
		super();
		this.result = result;
		this.data = data;
		this.message = message;
	}

	public static ResultMessage wrapSuccessResult(String message) {
		return new ResultMessage(true, null, message);
	}

	public static ResultMessage wrapSuccessResult(Object data, String message) {
		return new ResultMessage(true, data, message);
	}

	public static ResultMessage wrapFailureResult(String message) {
		return new ResultMessage(false, null, message);
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
