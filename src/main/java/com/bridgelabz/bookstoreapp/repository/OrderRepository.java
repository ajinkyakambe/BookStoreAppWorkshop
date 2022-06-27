package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderData,Integer> {

}

