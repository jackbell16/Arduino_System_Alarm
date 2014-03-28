/**
 * Questa classe contiene il main per poter eseguire il programma 
 * @author Giacomo
 */
package gui.SistemaAllarme;

import gui.controller.ControllerConnessione;
import gui.controller.PanelController;
import gui.controller.ProxyController;
import gui.menu.MenuSistemaAllarme;
import gui.view.ViewSistemaAllarme;

import java.awt.GridLayout;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.file.LetturaFile;
import model.sistemaAllarme.MySQLModel;

public class UserPanelMain {
	public static void main(String[] args) {
		ProxyController controller = new ProxyController();
		MySQLModel mySQLModel = new MySQLModel();
		MenuSistemaAllarme menuSistemaAllarme = new MenuSistemaAllarme(controller, mySQLModel);
		ViewSistemaAllarme queryViewSistemaAllarme = new ViewSistemaAllarme(mySQLModel);
		LetturaFile letturaFile = new LetturaFile();
		if(letturaFile.filePresente("configMySQL.txt")){
			letturaFile.setNomeFile("configMySQL.txt");
			String credenziali = letturaFile.getTesto();
			StringTokenizer stringTokenizer = new StringTokenizer(credenziali);
			String indirizzo = stringTokenizer.nextToken();
			String user = stringTokenizer.nextToken();
			String password = stringTokenizer.nextToken();
			String schema = stringTokenizer.nextToken();
			mySQLModel.setIndirizzoServer(indirizzo);
			mySQLModel.setUser(user);
			mySQLModel.setPassword(password);
			mySQLModel.setNomeSchema(schema);
			mySQLModel.connessione();
		}else{
			controller.setController(new ControllerConnessione(mySQLModel));
			controller.update();
		}
		PanelController panelController = new PanelController(controller);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(panelController);
		panel.add(queryViewSistemaAllarme);
		JFrame frame = new JFrame("Sistema Allarme");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setJMenuBar(menuSistemaAllarme);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
}
