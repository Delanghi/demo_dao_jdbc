package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {						// isso é um macete p/ ñ precisar expor a implementação
		return new SellerDaoJDBC(DB.getConnection());
	}	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC();
	}
}