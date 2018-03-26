/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.controllers;
import com.springSportShop.entities.Order;
import com.springSportShop.services.CategoryService;
import com.springSportShop.services.OrdersService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.springSportShop.Exceptions.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

/**
 *
 * @author maciejszwaczka
 */
@RestController
@RequestMapping("/order")
public class OrdersController {
    
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private UriComponentsBuilder builder;
    
    public OrdersController(OrdersService service,UriComponentsBuilder builder)
    {
        this.ordersService=service;    
        this.builder=builder;
    }
    
    @GetMapping("/get")
    public List<Order> getOrders()
    {
        return ordersService.getAll(); 
    }
    @GetMapping("/get/{id}")
    public Order getOrderById(@PathVariable Long id)
    {
        return ordersService.getOrder(id);
    }
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order)
    {
        try{
            ordersService.save(order);
            URI location=builder.path("order/"+order.getId()).build().toUri(); 
            return ResponseEntity.created(location).body(order);
        }
        catch(OutOfProductsException e)
        {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
