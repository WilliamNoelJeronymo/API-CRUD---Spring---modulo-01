package com.bootcamp.apiCRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.apiCRUD.entities.Client;


public interface ClientRepository extends JpaRepository<Client, Long>{

}
