package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();					// repare que ñ foi preciso "new" p/ INSTANCIAR

		System.out.println(" === Test 1: Seller findById === ");
		Seller seller = sellerDao.findByid(3);
		
			System.out.println(seller);
						
	}
}