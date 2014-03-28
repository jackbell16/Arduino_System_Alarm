/**
 * Questa classe verifica il corretto ritorno di tuple 
 */
package tests;

import model.sistemaAllarme.MySQLModel;

public class Tests01 {
	public static void main(String[] args) {
		MySQLModel mySQLModel = new MySQLModel("ip", "Sistema_allarme", "nomeUtente", "password");
		mySQLModel.setQuery("SELECT * FROM Rilevamenti");
		for (int i = 0; i < mySQLModel.getList().size(); i++) {
			System.out.println(mySQLModel.getList().get(i));
		}
	}
}
