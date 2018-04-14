package buczaq.spaceteam.bean.event;

import java.util.EventListener;

public interface SliderChangedListener extends EventListener{
	public abstract void sliderChanged(SliderChanged ev);
}
