package com.tcs.e_commerce_back_end.repository;

import com.tcs.e_commerce_back_end.model.entity.OneTimeCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeCodeRepository extends JpaRepository<OneTimeCodeModel,String> {}
