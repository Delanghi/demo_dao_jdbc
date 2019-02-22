package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
 // M�TODO PARA CONECTAR COM O BANCO DE DADOS
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if(conn == null) {										// tenho um "if" caso a VARI�VEL "conn" seja igual nulo
			try {
				Properties props = loadProperties();			// caso seja nulo, pegaremos as propriedades atrav�s do "loadProperties" l� de baixo
				String url = props.getProperty("dburl");		  // da� temos que pegar a "url" dentro de "db.properties"
				conn = DriverManager.getConnection(url, props);	  // foi feita conex�o do bco dados, atribu�do a VARI�VEL conn
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());			// para caso de ERRO; nossa exce��o personalizada
			}
		}
		return conn;											// tem que retornar o Objeto "conn" declarado acima
	}
	
 // M�TODO P/ FECHAR A CONEX�O
	public static void closeConnection() {
		if(conn != null) {										// aqui testamos se a conex�o est� INSTANCIADA
			try {
				conn.close(); 									// se ainda estiver INSTANCIADA, temos que fech�-la
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());			// para caso de ERRO; nossa exce��o personalizada
			}
		}
	}
	
 // M�TODO P/ CONNECTAR AS PROPRIEDADES
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;			
		} 
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
 // M�TODO P/ FECHAR CONEX�O COM BANCO DE DADOS
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultset(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}