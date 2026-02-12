package com.tcs.e_commerce_back_end.repository;

import com.tcs.e_commerce_back_end.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {}
