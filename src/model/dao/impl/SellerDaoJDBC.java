package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		public SellerDaoJDBC(Connection conn) {					// temos a disposição Objeto "conn" p/ qualquer MÉTODO desta CLASSE
			this.conn = conn;
		}
		
	 // IMPLEMENTAÇÃO DOS MÉTODOS
		@Override
		public void insert(Seller obj) {
		}

		@Override
		public void update(Seller obj) {		
		}

		@Override
		public void deleteById(Integer id) {		
		}

	 // DECLARAÇÕES
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
						Department dep = instantiateDepartment(rs);			// chamada de função
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

	 // FUNÇÕES 
		private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {	// deste modo pq a exceção já foi traçada acima
			Seller obj = new Seller();
			obj.setId(rs.getInt("Id"));
			obj.setName(rs.getString("Name")); 
			obj.setEmail(rs.getString("Email"));
			obj.setBaseSalary(rs.getDouble("BaseSalary"));
			obj.setBirthDate(rs.getDate("BirthDate"));  
			obj.setDepartment(dep); 
			return obj;
		}
		private Department instantiateDepartment(ResultSet rs) throws SQLException {			// deste modo pq a exceção já foi traçada acima
			Department dep = new Department();
			dep.setId(rs.getInt("DepartmentId"));
			dep.setName(rs.getString("DepName"));
			return dep;
		}

	 // MÉTODOS
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
					
					while(rs.next()) {										// p/ percorrer as linhas da LISTA enquanto tiver conteúdo
						
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
		public List<Seller> findByDepartment(Department department) {		// a assinatura do MÉTODO retorna uma lista: "List<Seller>"
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
					
					while(rs.next()) {										// p/ percorrer as linhas da LISTA enquanto tiver conteúdo
						
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
