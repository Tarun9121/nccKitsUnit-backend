package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
