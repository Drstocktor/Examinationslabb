package com.example.examinationslabb.repository;

import com.example.examinationslabb.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
