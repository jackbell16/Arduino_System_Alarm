/**
 * Questa interfaccia astrae sulla creazione di una interfaccia grafica/pannello per tutti i tipi di controller,
 * che rappresentano le diverse operazioni che vengono eseguite dall'utente
 * @author Giacomo
 */
package gui.controller;

import javax.swing.JPanel;

public interface Controller {
	/**
	 * Questo metodo permette di realizzare un'interfaccia grafica, per tutti i tipi di controller,
	 * che servono all'utente per poter effettuare le varie e diverse operazioni
	 * @return
	 */
	public JPanel creaInterfaccia();

}
