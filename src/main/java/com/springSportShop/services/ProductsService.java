/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.services;
import com.springSportShop.entities.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author maciejszwaczka
 */
@Service
public class ProductsService {
    @PersistenceContext
    final EntityManager entityManager;
    
    @Autowired
    final ExcelFilesService excelService;
    
    @Autowired
    public ProductsService(EntityManager entityManager,ExcelFilesService excelService)
    {
        System.out.println("products service");
        this.entityManager=entityManager;
        this.excelService=excelService;
    }
    @Transactional
   public void save(Product product)
   {
       
       entityManager.persist(product);
   }
   @Transactional
   public void addListOfProducts(List<Product> products)
   {   
       for(Product prod:products)
       {
           save(prod);
       }
   }
   public List<Product> getAll()
   {
       return entityManager.createNamedQuery(Product.FIND_ALL, Product.class).getResultList();
   }
   public Product getProductByID(Long id)
   {
       return entityManager.find(Product.class, id);
   }
}
