/**
 * Questa classe permette di creare un file
 * @author Giacomo
 */
package utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ScritturaFile {
	/**
	 * Questo metodo permette di caricare una stringa di testo, all'interno di un file
	 * @param testo
	 * @param nomeFile
	 */
	public void creaFile(String testo,String nomeFile){
		try {
			Writer writer = new FileWriter(new File(nomeFile));
			writer.write(testo);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
