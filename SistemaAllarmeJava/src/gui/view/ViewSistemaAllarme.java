package gui.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.sistemaAllarme.MySQLModel;

public class ViewSistemaAllarme extends JPanel implements Observer {
	
	private JTextArea area = new JTextArea();
	private MySQLModel mySQLModel;
	
	public ViewSistemaAllarme(MySQLModel mySQLModel){
		this.mySQLModel=mySQLModel;
		mySQLModel.addObserver(this);
		add(area);
		
	}
	@Override
	public void update(Observable o, Object arg) {
		area.setText("");
		System.out.println("Invio");
		for (int i = 0; i < mySQLModel.getList().size(); i++) {
			area.append(mySQLModel.getList().get(i));
			area.append("\n");
		}
	}
}
