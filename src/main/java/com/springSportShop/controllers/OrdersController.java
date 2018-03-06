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
import java.util.List;


/**
 *
 * @author maciejszwaczka
 */
@RestController
@RequestMapping("/order")
public class OrdersController {
    
    @Autowired
    private OrdersService ordersService;
    
    public OrdersController(OrdersService service, CategoryService categoryService)
    {
        this.ordersService=service;    
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
    public void addOrder(@RequestBody Order order)
    {
        ordersService.save(order);
    }
}
