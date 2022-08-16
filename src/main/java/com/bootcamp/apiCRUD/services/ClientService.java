package com.bootcamp.apiCRUD.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.apiCRUD.dto.ClientDto;
import com.bootcamp.apiCRUD.entities.Client;
import com.bootcamp.apiCRUD.repositories.ClientRepository;
import com.bootcamp.apiCRUD.services.exceptions.DatabaseException;
import com.bootcamp.apiCRUD.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDto> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDto(x));
	}

	@Transactional(readOnly = true)
	public ClientDto findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado!"));
		return new ClientDto(entity);
	}
	
	@Transactional
	public ClientDto insert(ClientDto dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		entity.setCpf(dto.getCpf());
		entity.setChildren(dto.getChildren());
		entity.setIncome(dto.getIncome());
		entity = repository.save(entity);
		return new ClientDto(entity);
	}
	
	@Transactional
	public ClientDto uptdate(Long id, ClientDto dto) {
		try {
		Client entity = repository.getOne(id);
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClientDto(entity);
		}catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("ID not found " + id);
		}
	}
	
	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("ID not found" + id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	
}
