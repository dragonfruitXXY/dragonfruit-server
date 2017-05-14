package dragonfruit.server.common.spring.property;

import java.beans.PropertyEditorSupport;

/**
 * Created by Xuyh at 2017/03/03 下午 03:43.
 */
public class BooleanPropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		this.setValue(Boolean.parseBoolean(text.trim()));
	}
}
