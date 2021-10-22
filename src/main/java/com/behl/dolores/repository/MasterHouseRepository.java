package com.behl.dolores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.behl.dolores.entity.MasterHouse;

@Repository
public interface MasterHouseRepository
        extends JpaRepository<MasterHouse, Integer>, JpaSpecificationExecutor<MasterHouse> {

}
