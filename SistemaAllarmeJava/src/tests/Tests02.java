/**
 * Questo test verifica la corretta creazione di un file e la relativa lettura
 * @author Giacomo
 */
package tests;

import utils.file.LetturaFile;
import utils.file.ScritturaFile;

public class Tests02 {
	public static void main(String[] args) {
		LetturaFile file = new LetturaFile();
		System.out.println(file.filePresente("configMySQL.txt"));
		ScritturaFile file2 = new ScritturaFile();
		file2.creaFile("ip user password Sistema_allarme", "configMySQL.txt");
		System.out.println(file.filePresente("configMySQL.txt"));
	}
}
