package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller obj);								// opera��o respons�vel por inserir o banco de dados
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findByid(Integer id);							// opera��o � respons�vel por pegar o ID e procur�-lo no banco de dados; se existir ir� imprimi-lo. caso contr�rio, retorna nulo
	List<Seller> findAll();									// aqui retornar� todos os departamentos; em formato de lista
	List<Seller> findByDepartment(Department department);	// aqui retornar� um departamento solicitado

}