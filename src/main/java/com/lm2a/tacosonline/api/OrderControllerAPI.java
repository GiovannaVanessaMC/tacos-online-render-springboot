package com.lm2a.tacosonline.api;


import com.lm2a.tacosonline.data.OrderRepository;
import com.lm2a.tacosonline.model.Order;
import com.lm2a.tacosonline.model.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(path = "/rest/order", produces="application/json")
@RestController
public class OrderControllerAPI {

    OrderRepository orderRepository;

    public OrderControllerAPI(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> orderById(@PathVariable Long id){
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()){
            return ResponseEntity.ok(orderOptional.get());
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    //traer todos
    @GetMapping()
    public Iterable<Order> allOrders(){
        return orderRepository.findAll();
    }

    //crear
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Order postOrder(@RequestBody Order order){
        return orderRepository.save(order);
    }


    //Borrado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        if(orderRepository.existsById(id)){
            orderRepository.deleteById(id);
            //devuelve 204
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //actualizar
    @PutMapping
    public Order putOrder(@RequestBody Order order) {
      return orderRepository.save(order);
    }
    //paginado
    @GetMapping("/recent/page/{id}")
    public Iterable<Order> recentOrder(@PathVariable int id) {
        PageRequest page = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        return orderRepository.findAll(page);

    }

}
