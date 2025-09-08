package com.simuladorcredito.gateway.repository;

import com.simuladorcredito.gateway.repository.data.SimulacaoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimulacaoRepository extends MongoRepository<SimulacaoEntity, String> {

}
