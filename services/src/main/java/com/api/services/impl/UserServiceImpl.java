package com.api.services.impl;

import java.util.Optional;

import com.api.persistence.domain.dtos.UserDTO;
import com.api.persistence.domain.entities.User;
import com.api.persistence.domain.repositories.IUserRepository;
import com.api.services.UserService;
import com.api.services.filters.UserFilter;
import com.api.services.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

	private final IUserRepository userRepository;

	@Autowired
	public UserServiceImpl(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Page<UserDTO> getAll(Pageable pageable, UserFilter userFilter) {
		return this.userRepository.findAll(userFilter.getName(), pageable)
				.map(e -> ObjectMapperUtils.map(e, new UserDTO()));
	}

	@Override
	public UserDTO save(UserDTO user) {

		User newObj = ObjectMapperUtils.map(user, new User());

		return ObjectMapperUtils.map(this.userRepository.save(newObj), new UserDTO());
	}

	@Override
	public Optional<UserDTO> update(Long id, UserDTO user) {
		return this.userRepository.findById(id)
				.map(oi -> {
					User updated = ObjectMapperUtils.map(user, new User());
					updated.setId(oi.getId());

					return ObjectMapperUtils.map(this.userRepository.save(updated), new UserDTO());
				});
	}

	@Override
	public Boolean delete(Long id) {
		return this.userRepository.findById(id).map(e -> {
			this.userRepository.deleteById(id);
			return true;
		}).orElse(false);

	}

	@Override
	public Optional<UserDTO> getById(Long id) {
		return this.userRepository.findById(id).map(e -> ObjectMapperUtils.map(e, new UserDTO()));
	}

}

