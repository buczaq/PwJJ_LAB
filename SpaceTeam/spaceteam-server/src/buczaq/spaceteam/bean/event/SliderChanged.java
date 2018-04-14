package buczaq.spaceteam.bean.event;

import java.util.EventObject;

public class SliderChanged extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer newValue;

	public SliderChanged(Object source, Integer newValue) {
		super(source);
		this.newValue = newValue;
	}

	public Integer getNewValue() {
		return newValue;
	}
}
