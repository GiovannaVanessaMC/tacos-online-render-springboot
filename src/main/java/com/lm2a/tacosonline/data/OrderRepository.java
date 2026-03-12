package com.lm2a.tacosonline.data;

import com.lm2a.tacosonline.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
