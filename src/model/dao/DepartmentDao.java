package model.dao;

import java.util.List;
import model.entities.Department;

public interface DepartmentDao {
	
	void insert(Department obj);					// opera��o respons�vel por inserir o banco de dados
	void update(Department obj);
	void deleteById(Integer id);
	Department findByid(Integer id);				// opera��o � respons�vel por pegar o ID e procur�-lo no banco de dados; se existir ir� imprimi-lo. caso contr�rio, retorna nulo
	List<Department> findAll();						// aqui retornar� todos os departamentos; em formato de lista

}