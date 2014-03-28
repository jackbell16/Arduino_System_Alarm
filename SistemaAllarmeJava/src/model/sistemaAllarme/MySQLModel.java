/**
 * Questa classe contiene i metodi per poter accedere ad un server MySQL
 * @author Giacomo
 */
package model.sistemaAllarme;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;


public class MySQLModel extends Observable {
	
	private String indirizzoServer;
	private String nomeSchema;
	private String user;
	private String password;
	private java.sql.Connection conn;
	private ArrayList<String> list;
	
	public MySQLModel() {
	}

	public MySQLModel(String indirizzoServer, String nomeSchema,
			String user, String password) {
		super();
		this.indirizzoServer = indirizzoServer;
		this.nomeSchema = nomeSchema;
		this.user = user;
		this.password = password;
		connessione();
	}
	/**
	 * Questo metodo permette di connettersi al server MySQL
	 */
	public void connessione() {
		try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception E) {
	        System.err.println("Non trovo il driver da caricare.");
	        E.printStackTrace();
	    }
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+getIndirizzoServer()+":3306/"+getNomeSchema()+"?user="+getUser()+"&password="+getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getIndirizzoServer() {
		return indirizzoServer;
	}
	
	public void setIndirizzoServer(String indirizzoServer) {
		this.indirizzoServer = indirizzoServer;
	}

	public String getNomeSchema() {
		return nomeSchema;
	}

	public void setNomeSchema(String nomeSchema) {
		this.nomeSchema = nomeSchema;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Questo metodo permette di effettuare un insert a un DB
	 * @param query
	 */
	public void setQueryInsert(String query){
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
	        statement.execute();
	        statement.close();
	        conn.close();
		} catch (SQLException E) {
	        System.out.println("SQLException: " + E.getMessage());
	        System.out.println("SQLState:     " + E.getSQLState());
	        System.out.println("VendorError:  " + E.getErrorCode());
	    }
	}
	
	/**
	 * Questo metodo permette di effettuare un query
	 */
	public void setQuery(String query){
		list = new ArrayList<>();
		try{
		PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
        	list.add(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
        }
        rs.close();
        statement.close();
      } catch (SQLException E) {
        System.out.println("SQLException: " + E.getMessage());
        System.out.println("SQLState:     " + E.getSQLState());
        System.out.println("VendorError:  " + E.getErrorCode());
    }
		update();
	}
	public ArrayList<String> getList() {
		return list;
	}
	public void setList(ArrayList<String> list) {
		this.list = list;
	}	
	/**
	 * Questo metodo permette di aggionrare il connettere al server MySQl
	 */
	public void update(){
		setChanged();
		notifyObservers();
	}
}


