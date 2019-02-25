package model.dao;

import java.util.List;
import model.entities.Department;

public interface DepartmentDao {
	
	void insert(Department obj);					// operação responsável por inserir o banco de dados
	void update(Department obj);
	void deleteById(Integer id);
	Department findByid(Integer id);				// operação é responsável por pegar o ID e procurá-lo no banco de dados; se existir irá imprimi-lo. caso contrário, retorna nulo
	List<Department> findAll();						// aqui retornará todos os departamentos; em formato de lista

}