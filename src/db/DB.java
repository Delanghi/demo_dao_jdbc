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
	
 // MÉTODO PARA CONECTAR COM O BANCO DE DADOS
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if(conn == null) {										// tenho um "if" caso a VARIÁVEL "conn" seja igual nulo
			try {
				Properties props = loadProperties();			// caso seja nulo, pegaremos as propriedades através do "loadProperties" lá de baixo
				String url = props.getProperty("dburl");		  // daí temos que pegar a "url" dentro de "db.properties"
				conn = DriverManager.getConnection(url, props);	  // foi feita conexão do bco dados, atribuído a VARIÁVEL conn
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());			// para caso de ERRO; nossa exceção personalizada
			}
		}
		return conn;											// tem que retornar o Objeto "conn" declarado acima
	}
	
 // MÉTODO P/ FECHAR A CONEXÃO
	public static void closeConnection() {
		if(conn != null) {										// aqui testamos se a conexão está INSTANCIADA
			try {
				conn.close(); 									// se ainda estiver INSTANCIADA, temos que fechá-la
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());			// para caso de ERRO; nossa exceção personalizada
			}
		}
	}
	
 // MÉTODO P/ CONNECTAR AS PROPRIEDADES
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
	
 // MÉTODO P/ FECHAR CONEXÃO COM BANCO DE DADOS
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