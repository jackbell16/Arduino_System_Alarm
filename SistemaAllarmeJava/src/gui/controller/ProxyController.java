/**
 * Questa classe è il Proxy che gestisce a run time i vari {@link Controller} che sono presenti
 * @author Giacomo
 */
package gui.controller;

import java.util.Observable;

import javax.swing.JPanel;

public class ProxyController extends Observable implements Controller {
	
	private Controller controller;
	
	@Override
	public JPanel creaInterfaccia() {
		return controller.creaInterfaccia();		
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
		update();
		
	}
	public void update(){
		setChanged();
		notifyObservers();
	}
}
