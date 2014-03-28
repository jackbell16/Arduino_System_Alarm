/**
 * Questa classe ha la reponsabilità di gestire il Menu dell'applicazione
 * @author Giacomo
 */
package gui.menu;

import gui.controller.ControllerConnessione;
import gui.controller.ControllerRegistrazione;
import gui.controller.ControllerRilevamenti;
import gui.controller.ProxyController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.sistemaAllarme.MySQLModel;

public class MenuSistemaAllarme extends JMenuBar {
	
	private ProxyController controller;
	private MySQLModel mySQLModel;
	private ControllerRilevamenti rilevamentiC = new ControllerRilevamenti(this.mySQLModel);
	private ControllerRegistrazione registrazioneC = new ControllerRegistrazione(this.mySQLModel);
	private ControllerConnessione controllerConnessione = new ControllerConnessione(this.mySQLModel);
	
	public MenuSistemaAllarme(final ProxyController proxyController,MySQLModel mySQLModel) {
		this.controller = proxyController;
		JMenu connessione = new JMenu("Connessione DB");
		JMenuItem connetti = new JMenuItem("Connetti");
		connessione.add(connetti);
		connetti.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setController(controllerConnessione);
				
			}
		});
		JMenu strumenti = new JMenu("Strumenti");
		JMenuItem registrazione = new JMenuItem("Registrazione");
		registrazione.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setController(registrazioneC);
				
				
			}
		});
		JMenuItem rilevamenti = new JMenuItem("Rilevamenti");
		rilevamenti.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setController(rilevamentiC);
			}
		});
		strumenti.add(registrazione);
		strumenti.add(rilevamenti);
		add(connessione);
		add(strumenti);
	}
}
