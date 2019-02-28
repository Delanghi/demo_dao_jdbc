package application;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);	

		Department obj = new Department(1, "Food's");		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();					// repare que ñ foi preciso "new" p/ INSTANCIAR
			System.out.println(obj);		
			
		
				
			
	sc.close();
	}
}
