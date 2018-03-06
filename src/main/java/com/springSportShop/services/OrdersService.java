/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.services;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityTransaction;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;

import com.springSportShop.entities.*;
/**
 *
 * @author maciejszwaczka
 */
@Service
public class OrdersService {
    /*@PersistenceContext*/
    @Autowired
    final EntityManager entityManager;
    
    @Autowired
    public OrdersService(EntityManager entityManager)
    {
        this.entityManager=entityManager;
    }
    @Transactional
   public void save(Order order){
      entityManager.persist(order);
   }
   public List<Order> getAll(){
       return entityManager.createNamedQuery(Order.FIND_ALL, Order.class).getResultList();
   }
   public Order getOrder(Long id)
   {
       List<Product> prods=new ArrayList<Product>();
       prods.add(new Product());
       prods.add(new Product());
       return new Order(null,prods,new Date());
   }
}
