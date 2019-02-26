package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDao createSellerDao(){						// isso � um macete p/ � precisar expor a implementa��o
		return new SellerDaoJDBC(DB.getConnection());
	}
}