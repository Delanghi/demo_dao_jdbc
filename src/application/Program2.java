package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);	
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();					// repare que ñ foi preciso "new" p/ INSTANCIAR
		
		System.out.println("=== Test 1: Department findById === ");
		Department obj = departmentDao.findByid(2);
			System.out.println(obj);		
			
		System.out.println(" \n=== Test 2: Department findAll === ");
		List<Department> list = departmentDao.findAll(); 								// usamos a mesma lista acima 
			for(Department d : list) {
				System.out.println(d);
			}	
			
		System.out.println(" \n=== Test 3: Department insert === ");
		Department newDepartment = new Department(null, "Food's");
		departmentDao.insert(newDepartment);
			System.out.println("Inserted! New ID = " + newDepartment.getId());	
			
		System.out.println(" \n=== Test 4: Department update === ");	
		obj = departmentDao.findByid(7);
		obj.setName("Garage"); 
		departmentDao.update(obj);
			System.out.println("Update completed!" );	
			
		System.out.println(" \n=== Test 6: Department delete === ");	
		System.out.print("Enter ID for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
			System.out.println("Delete completed");		
			
		sc.close();
	}
}
