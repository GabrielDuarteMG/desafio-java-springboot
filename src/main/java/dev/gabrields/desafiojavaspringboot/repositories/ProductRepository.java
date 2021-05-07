package dev.gabrields.desafiojavaspringboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.gabrields.desafiojavaspringboot.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	@Query(" FROM Product prd WHERE prd.name LIKE :nameDescription OR prd.description LIKE :nameDescription")
	List<Product> findTitleByNameDescription(@Param("nameDescription") String nameDescription);

}
