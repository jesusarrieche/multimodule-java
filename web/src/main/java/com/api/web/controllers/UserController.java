package com.api.web.controllers;

import com.api.persistence.domain.dtos.UserDTO;
import com.api.services.UserService;
import com.api.services.filters.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(value = "/all")
	public Page<UserDTO> getAll(
			int pageNumber,
			int pageSize,
			UserFilter filter
	) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return this.userService.getAll(pageable, filter);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
		return this.userService.getById(id)
				.map(res -> new ResponseEntity<>(res, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> newUser(@RequestBody UserDTO user) {
		return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
		
		return this.userService.update(id, user)
			.map(res -> new ResponseEntity<>(res, HttpStatus.OK))
			.orElse(new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if(Boolean.TRUE.equals(this.userService.delete(id)))
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
