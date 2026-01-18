package com.curso.react.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.curso.react.entity.ExposureEntity;

@Repository
public interface ExposureRepository extends CrudRepository<ExposureEntity, Long>{
    
}
