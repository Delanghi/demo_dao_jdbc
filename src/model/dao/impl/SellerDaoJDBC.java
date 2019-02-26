package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	}

	@Override
	public void update(Seller obj) {		
	}

	@Override
	public void deleteById(Integer id) {		
	}

	@Override
	public Seller findByid(Integer id) {
		
// DECLARA��ES
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

 // 
	@Override
	public List<Seller> findAll() {
		return null;
	}

}