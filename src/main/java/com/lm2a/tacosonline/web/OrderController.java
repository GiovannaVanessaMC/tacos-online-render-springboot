package com.lm2a.tacosonline.web;


import com.lm2a.tacosonline.data.OrderRepository;
import com.lm2a.tacosonline.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String showOrderForm(@ModelAttribute("order") Order order) {
      return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus status) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepository.save(order);
        status.setComplete();
        log.info("Processing order: {}", order);
        return "redirect:/";
    }
}
