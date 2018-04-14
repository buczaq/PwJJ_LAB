package app;

import java.util.List;

import javax.swing.SwingWorker;

public class IntervalPooling extends SwingWorker<Void, String> {

	private CaptainController captainController;
	
	public IntervalPooling(CaptainController captainController) {
		this.captainController = captainController;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
	
		while(true) {
			
			captainController.poolFromServer();
			
			Thread.sleep(1000);
		}
		
	}

	@Override
	protected void process(List<String> chunks) {
		
	}
}
