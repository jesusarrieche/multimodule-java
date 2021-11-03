package com.api.persistence.domain.repositories;

import com.api.persistence.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IUserRepository extends JpaRepository<User, Long> {
	
	@Query(
			value = "SELECT u FROM User u "
			+ "WHERE (LOWER(u.name) LIKE LOWER(CONCAT('%', ?1, '%')) or ?1 IS NULL)",
			countQuery = "SELECT COUNT(u) FROM User u "
					+ "WHERE (LOWER(u.name) LIKE LOWER(CONCAT('%', ?1, '%')) or ?1 IS NULL)"
			
			)
	Page<User> findAll(String name, Pageable page);
}
