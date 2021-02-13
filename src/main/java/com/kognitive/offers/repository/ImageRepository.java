package com.kognitive.offers.repository;

import com.kognitive.offers.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images, Long> {
}
