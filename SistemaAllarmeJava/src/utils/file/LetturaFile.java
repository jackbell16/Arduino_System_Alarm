/**
 * Questa classe permette di poter leggere un file
 * @author Giacomo
 */
package utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LetturaFile {
	private String testo;
	private BufferedReader reader;
	/**
	 * Questo metodo imposta il percorso del file e lo trasforma in una stringa di testo
	 * @param percorso
	 */
	public void setNomeFile(String percorso){
		try{
		reader = new BufferedReader(new FileReader(new File(percorso)));
		testo = reader.readLine();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}
	/**
	 * Questo metodo permette di verificare se un file è presente, in un determinato percorso
	 * @param percorso
	 * @return
	 */
	public boolean filePresente(String percorso){
		File file = new File(percorso);
		return file.canRead();
	}
}
