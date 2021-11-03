package com.api.services;

import java.util.Optional;

import com.api.services.filters.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.persistence.domain.dtos.UserDTO;

public interface UserService {
	Page<UserDTO> getAll(Pageable pageable, UserFilter userFilter);
	Optional<UserDTO> getById(Long id);
	UserDTO save(UserDTO user);
	Optional<UserDTO> update(Long id, UserDTO user);
	Boolean delete(Long id);
}
