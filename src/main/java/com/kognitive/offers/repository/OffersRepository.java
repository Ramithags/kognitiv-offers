package com.kognitive.offers.repository;

import com.kognitive.offers.model.Offers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffersRepository extends JpaRepository<Offers, Long> {
    List<Offers> findByNameContaining(String name,Pageable pageable);
}
