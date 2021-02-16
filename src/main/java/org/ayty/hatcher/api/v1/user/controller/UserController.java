package org.ayty.hatcher.api.v1.user.controller;


import javax.validation.Valid;

import org.ayty.hatcher.api.v1.user.dto.LoginDTO;
import org.ayty.hatcher.api.v1.user.dto.OutRegisterDTO;
import org.ayty.hatcher.api.v1.user.dto.RegisterUserDTO;
import org.ayty.hatcher.api.v1.user.dto.TokenDTO;
import org.ayty.hatcher.api.v1.user.dto.UserListDTO;
import org.ayty.hatcher.api.v1.user.service.ListUsersServiceImpl;
import org.ayty.hatcher.api.v1.user.service.RegisterUserServiceImpl;
import org.ayty.hatcher.api.v1.user.service.RemoveUserServiceImpl;
import org.ayty.hatcher.api.v1.user.service.ReturnsLoginAndTokenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hatcher")
@CrossOrigin("*")
public class UserController {
	
	private final RegisterUserServiceImpl registerImpl;	
	private final ListUsersServiceImpl listUserService;	
	private final RemoveUserServiceImpl removeUserService;
	private final ReturnsLoginAndTokenService authenticationReturn;
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public OutRegisterDTO registerUser(@Valid @RequestBody RegisterUserDTO user) {
		return registerImpl.save(user);
		
	}
	
	@PostMapping(value = "/auth",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
    public TokenDTO authenticate(@Valid  @RequestBody LoginDTO credenciais){
		return authenticationReturn.LoginAndToken(credenciais);
        
    }
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/listUsers")
	public Page<UserListDTO> listUsers(@RequestParam(value = "isPaged", defaultValue = "true") boolean isPaged,
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort) {
        if (isPaged) {
        	return UserListDTO.from(this.listUserService.listOfRegisteredUsers(PageRequest.of(
        			pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sort), ord))));
        }
        else {
        	return UserListDTO.from(this.listUserService.listOfRegisteredUsers(Pageable.unpaged()));
        }
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/remove/{id}")
	public void removeUsers(@PathVariable  Long id) {
		removeUserService.removeUser(id);
		
	}
}
