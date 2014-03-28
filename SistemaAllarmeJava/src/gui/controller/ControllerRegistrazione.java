/**
 * Questa classe permette di inviare una query al server MySQL, per regsitare un nuovo dispositivo client
 * @author Giacomo
 */
package gui.controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.sistemaAllarme.MySQLModel;

public class ControllerRegistrazione extends JPanel implements Controller {

	private TextField fieldID;
	private TextField fieldLuogo;
	private TextField fieldCasa;
	private MySQLModel mySQLModel;
	public ControllerRegistrazione(MySQLModel mySQLModel) {
		this.mySQLModel=mySQLModel;
	}
	public JPanel creaInterfaccia() {
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(4, 1));
		fieldID = new TextField("ID");
		fieldLuogo = new TextField("Luogo");
		fieldCasa = new TextField("Citta");
		fieldPanel.add(fieldID);
		fieldPanel.add(fieldLuogo);
		fieldPanel.add(fieldCasa);
		JButton button = new JButton("Invio");
		fieldPanel.add(button,BorderLayout.SOUTH);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mySQLModel.setQuery("Select * From Rilevamenti");
				}
		});
			return fieldPanel;
	}
}
