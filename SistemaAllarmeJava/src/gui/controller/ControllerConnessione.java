/**
 * Questa classe permette di impostare le credenziali d'accesso al server MySQL, attraverso
 * la schermata dell'applicazione
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
import javax.swing.JPasswordField;

import model.sistemaAllarme.MySQLModel;
import utils.file.ScritturaFile;

public class ControllerConnessione extends JPanel implements Controller {

	private TextField fieldIP;
	private TextField fieldUser;
	private JPasswordField jPasswordField;
	private TextField fieldSchema;
	private MySQLModel mySQLModel;
	public ControllerConnessione(MySQLModel mySQLModel) {
		this.mySQLModel=mySQLModel;
	}
	public JPanel creaInterfaccia() {
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(5, 1));
		fieldIP = new TextField("IP");
		fieldUser = new TextField("Username");
		jPasswordField= new JPasswordField("Password");
		fieldSchema = new TextField("DB");
		fieldPanel.add(fieldIP);
		fieldPanel.add(fieldUser);
		fieldPanel.add(jPasswordField);
		fieldPanel.add(fieldSchema);
		JButton button = new JButton("Invio");
		fieldPanel.add(button,BorderLayout.SOUTH);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScritturaFile scritturaFile = new ScritturaFile();
					String credenziali = fieldIP.getText()+" "+fieldUser.getText()+" "+jPasswordField.getText()+" "+fieldSchema.getText();
					scritturaFile.creaFile(credenziali, "configMySQL.txt");
					mySQLModel.setIndirizzoServer(fieldIP.getText());
					mySQLModel.setUser(fieldUser.getText());
					mySQLModel.setPassword(jPasswordField.getText());
					mySQLModel.setPassword(fieldSchema.getText());
				}
		});
			return fieldPanel;
	}
}
