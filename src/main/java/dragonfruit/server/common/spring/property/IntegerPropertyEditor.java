package dragonfruit.server.common.spring.property;

import java.beans.PropertyEditorSupport;

/**
 * 用来为Spring读取XML配置文件中的参数值提供类型转换
 *
 * Created by Xuyh at 2017/03/03 下午 03:26.
 */
public class IntegerPropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		this.setValue(Integer.parseInt(text.trim()));
	}
}
