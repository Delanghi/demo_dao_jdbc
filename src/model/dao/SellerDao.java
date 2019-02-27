package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller obj);								// operação responsável por inserir o banco de dados
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findByid(Integer id);							// operação é responsável por pegar o ID e procurá-lo no banco de dados; se existir irá imprimi-lo. caso contrário, retorna nulo
	List<Seller> findAll();									// aqui retornará todos os departamentos; em formato de lista
	List<Seller> findByDepartment(Department department);	// aqui retornará um departamento solicitado

}