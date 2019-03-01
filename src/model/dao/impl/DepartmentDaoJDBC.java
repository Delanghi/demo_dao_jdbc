package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

 // ATRIBUTOS
		private Connection conn;
		
 // CONSTRUTOR
		public DepartmentDaoJDBC(Connection conn) {					// temos a disposi��o Objeto "conn" p/ qualquer M�TODO desta CLASSE
			this.conn = conn;
		}	

 // IMPLEMENTA��O DOS M�TODOS
	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {				
			st = conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES "
					+ "(?)" ,
					Statement.RETURN_GENERATED_KEYS);							
			
			st.setString(1, obj.getName());
						
			int rowsAffected = st.executeUpdate();
				if(rowsAffected > 0) {
					ResultSet rs = st.getGeneratedKeys();
						if(rs.next()) {										// "If" pq vamos inserir apenas um dado
							int id = rs.getInt(1);							// vai inserir na 1� coluna das chaves
							obj.setId(id);									
						}
				} else {
					throw new DbException("Unexpected error! No rows affected. ");
				}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {				
			st = conn.prepareStatement(
				"UPDATE department "
				+ "SET Name = ? "
				+ "WHERE Id = ? " );
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId()); 									// para apanhar n� ID
			
			st.executeUpdate();											// "int rowsAffected" foi considerado acima
				
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}			
	}

	@Override
	public Department findByid(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
					"SELECT department.Name "
					+ "FROM department "
					+ "WHERE department.Id = ? " );
				
				st.setInt(1, id); 										// o primeiro "?" recebe o "id" da linha 36
				rs = st.executeQuery();									// depois de executado o resultado vai p/ o "ResultSet"
				if(rs.next()) {
					Department dep = new Department();					// INSTANCIANDO
						dep.setId(id);
						dep.setName(rs.getString("Name"));
					return dep;
				}
				return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultset(rs);
		}
	}

	
	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department "
					+ "ORDER BY Name" );
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while (rs.next()) {
				Department dep = new Department();									// INSTANCIAMOS
					dep.setId(rs.getInt("Id")); 
					dep.setName(rs.getString("Name"));
				list.add(dep); 				
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultset(rs);
			DB.closeStatement(st);
		}
	}	
}