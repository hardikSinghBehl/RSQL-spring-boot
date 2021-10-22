package com.behl.dolores.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.behl.dolores.entity.MasterHouse;

@Repository
public interface MasterHouseRepository extends JpaRepository<MasterHouse, Integer> {

    Optional<MasterHouse> findByNameLike(final String name);

}
