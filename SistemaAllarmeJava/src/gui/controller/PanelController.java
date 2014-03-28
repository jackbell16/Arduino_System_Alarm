/**
 * Questa classe contiene l'astrazione e i metodi per gestire il pannello dei {@link Controller}
 * @author Giacomo
 */
package gui.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class PanelController extends JPanel implements Observer {
	
	private ProxyController proxyController;

	public PanelController(ProxyController proxyController) {
		super();
		this.proxyController = proxyController;
		proxyController.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		removeAll();
		add(proxyController.creaInterfaccia());
		updateUI();
	}
}
