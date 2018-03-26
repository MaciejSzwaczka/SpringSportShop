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
import java.util.stream.Stream;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
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


    public List<Product> getAllInPriceRange(Integer lowestPrice, Integer maxPrice) {
        CriteriaBuilder crit=entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query=crit.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        ParameterExpression<Integer> minPriceParam=crit.parameter(Integer.class);
        ParameterExpression<Integer> maxPriceParam=crit.parameter(Integer.class);
        query.select(root).where(crit.gt(root.get("price"),maxPriceParam));
        query.select(root).where(crit.lt(root.get("price"), maxPriceParam));
        TypedQuery<Product> typedQuery=entityManager.createQuery(query);
        return typedQuery.setParameter(minPriceParam,lowestPrice).
                setParameter(maxPriceParam, maxPrice).getResultList();
    }
}
