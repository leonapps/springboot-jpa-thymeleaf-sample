package com.leonapps.sample.springboot.thymeleaf.repository;

import com.leonapps.sample.springboot.thymeleaf.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {}
