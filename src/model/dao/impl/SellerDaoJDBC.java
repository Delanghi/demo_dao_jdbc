package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	 // ATRIBUTOS
		private Connection conn;
		
	 // CONSTRUTOR
		public SellerDaoJDBC(Connection conn) {					// temos a disposi��o Objeto "conn" p/ qualquer M�TODO desta CLASSE
			this.conn = conn;
		}
		
	 // IMPLEMENTA��O DOS M�TODOS
		@Override
		public void insert(Seller obj) {			
			PreparedStatement st = null;
			try {				
				st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " 
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)" ,
					Statement.RETURN_GENERATED_KEYS);							// p/ acrescentar novo vendedor
				
				st.setString(1, obj.getName());
				st.setString(2, obj.getEmail());
				st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));		//INSTANCIAR uma data do SQL
				st.setDouble(4, obj.getBaseSalary());
				st.setInt(5, obj.getDepartment().getId());						// 1� acessa o depto; depois pega o ID
				
				int rowsAffected = st.executeUpdate();
					if(rowsAffected > 0) {
						ResultSet rs = st.getGeneratedKeys();
							if(rs.next()) {										// "If" pq vamos inserir apenas um dado
								int id = rs.getInt(1);							// vai inserir na 1� coluna das chaves
								obj.setId(id);									
							}
							DB.closeResultset(rs); 								// vamos fech�-lo por aqui
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
		public void update(Seller obj) {	
			PreparedStatement st = null;
			try {				
				st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? " );
				
				st.setString(1, obj.getName());
				st.setString(2, obj.getEmail());
				st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));		//INSTANCIAR uma data do SQL
				st.setDouble(4, obj.getBaseSalary());
				st.setInt(5, obj.getDepartment().getId());					// 1� acessa o depto; depois pega o ID
				st.setInt(6, obj.getId()); 									// para apanhar n� ID
				
				st.executeUpdate();											// "int rowsAffected" foi considerado acima
					
			} catch (SQLException e) {
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
				st = conn.prepareStatement("DELETE FROM seller WHERE Id = ? ");
				
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

	 // DECLARA��ES
		@Override
		public Seller findByid(Integer id) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
					st = conn.prepareStatement(
						"SELECT seller.*,department.Name as DepName " 
						+ "FROM seller INNER JOIN department "  
						+ "ON seller.DepartmentId = department.Id "
						+ "WHERE seller.Id = ? " );
					
					st.setInt(1, id); 										// o primeiro "?" recebe o "id" da linha 36
					rs = st.executeQuery();									// depois de executado o resultado vai p/ o "ResultSet"
					if(rs.next()) {
						Department dep = instantiateDepartment(rs);			// chamada de fun��o
						Seller obj = instantiateSeller(rs, dep);
						return obj;
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

	 // FUN��ES 
		private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {	// deste modo pq a exce��o j� foi tra�ada acima
			Seller obj = new Seller();
			obj.setId(rs.getInt("Id"));
			obj.setName(rs.getString("Name")); 
			obj.setEmail(rs.getString("Email"));
			obj.setBaseSalary(rs.getDouble("BaseSalary"));
			obj.setBirthDate(rs.getDate("BirthDate"));  
			obj.setDepartment(dep); 
			return obj;
		}
		private Department instantiateDepartment(ResultSet rs) throws SQLException {			// deste modo pq a exce��o j� foi tra�ada acima
			Department dep = new Department();
			dep.setId(rs.getInt("DepartmentId"));
			dep.setName(rs.getString("DepName"));
			return dep;
		}

	 // M�TODOS
		@Override
		public List<Seller> findAll() {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
					st = conn.prepareStatement(
							"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "ORDER BY Name" );
					
					rs = st.executeQuery();									// depois de executado o resultado vai p/ o "ResultSet"

					List<Seller> list = new ArrayList<>();
					Map<Integer, Department> map = new HashMap<>();
					
					while(rs.next()) {										// p/ percorrer as linhas da LISTA enquanto tiver conte�do
						
						Department dep = map.get(rs.getInt("DepartmentId"));
						
						if(dep == null) {
							dep = instantiateDepartment(rs); 
							map.put(rs.getInt("DepartmentId"), dep); 
						}
						
						Seller obj = instantiateSeller(rs, dep);			// INSTANCIAR vendedor
						list.add(obj);										// acrescentar na lista "seller"
					}
					return list;
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
		public List<Seller> findByDepartment(Department department) {		// a assinatura do M�TODO retorna uma lista: "List<Seller>"
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
					st = conn.prepareStatement(
							"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "WHERE DepartmentId = ? "
							+ "ORDER BY Name" );
					
					st.setInt(1, department.getId()); 						// o "?" recebe o "department" da linha 93
					rs = st.executeQuery();									// depois de executado o resultado vai p/ o "ResultSet"

					List<Seller> list = new ArrayList<>();
					Map<Integer, Department> map = new HashMap<>();
					
					while(rs.next()) {										// p/ percorrer as linhas da LISTA enquanto tiver conte�do
						
						Department dep = map.get(rs.getInt("DepartmentId"));
						
						if(dep == null) {
							dep = instantiateDepartment(rs); 
							map.put(rs.getInt("DepartmentId"), dep); 
						}
						
						Seller obj = instantiateSeller(rs, dep);			// INSTANCIAR vendedor
						list.add(obj);										// acrescentar na lista "seller"
					}
					return list;
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally {
				DB.closeStatement(st);
				DB.closeResultset(rs);
			}
		}	
}
