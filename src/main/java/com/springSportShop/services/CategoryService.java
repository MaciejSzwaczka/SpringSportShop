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
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.Persistence;
import org.hibernate.Criteria;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

import com.springSportShop.entities.*;
/**
 *
 * @author maciejszwaczka
 */
@Service
public class CategoryService {
    @Autowired
    final EntityManager entityManager;
    
    @Autowired
    final PlatformTransactionManager transactionManager;
    
    @Autowired
    public CategoryService(EntityManager manager,PlatformTransactionManager transactionManager)
    {
        System.setProperty("file.encoding","UTF-8");
        System.out.println("Category service");
        this.entityManager=manager;
        this.transactionManager=transactionManager;
    }
    
    public List<Category> findAll()
    {
        return entityManager.createNamedQuery(Category.FIND_ALL, Category.class).getResultList();
    }
    
    @PostConstruct
    @Transactional
    public void init()
    {
        List<Category> cats=entityManager.createNamedQuery(Category.FIND_ALL, Category.class).getResultList();
        if(cats.isEmpty())
        {
            TransactionTemplate templ=new TransactionTemplate(transactionManager);
            templ.execute(new TransactionCallbackWithoutResult(){
                @Override
                public void doInTransactionWithoutResult(TransactionStatus transStat){
                    Category superCategoryMen=new Category(null,"Dla mezczyzn",null,new ArrayList<>(),new ArrayList<>());
                    Category superCategoryWomen=new Category(null,"Dla kobiet",null,new ArrayList<>(),new ArrayList<>());
                    ArrayList<Category> subCategoriesForMen=new ArrayList<>();
                    ArrayList<Category> subCategoriesForWomen=new ArrayList<>();
                    subCategoriesForMen.add(new Category(null,"Buty Męskie",superCategoryMen,new ArrayList<>(),new ArrayList<>()));
                    subCategoriesForMen.add(new Category(null,"Odziez meska",superCategoryMen,new ArrayList<>(),new ArrayList<>()));
                    subCategoriesForWomen.add(new Category(null,"Odziez damska",superCategoryWomen,new ArrayList<>(),new ArrayList<>()));
                    subCategoriesForWomen.add(new Category(null,"Buty Damskie",superCategoryWomen,new ArrayList<>(),new ArrayList<>()));
                    superCategoryMen.setSubCategories(subCategoriesForMen);
                    superCategoryWomen.setSubCategories(subCategoriesForWomen);
                    entityManager.persist(superCategoryMen);
                    entityManager.persist(superCategoryWomen);
                }
            });
        }
        else{
            System.out.println("no update");
        }
    }
    public Category mapNameOfCatToCat(String name)
    {
        CriteriaBuilder crit=entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query=crit.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        ParameterExpression<String> nameParam=crit.parameter(String.class);
        query.select(root).where(crit.like(root.get("name"),nameParam));
        TypedQuery<Category> typedQuery=entityManager.createQuery(query);
        return typedQuery.setParameter(nameParam,name).getSingleResult();
        /*List<Category> cats=findAll();
        for(Category cat: cats)
        {
            if(cat.getName().equals(name))
            {
                return cat;
            }
        }*/
    }
    @Transactional
    public Boolean addCategory(Category cat)
    {
        entityManager.persist(cat);
        return true;
    }
}
