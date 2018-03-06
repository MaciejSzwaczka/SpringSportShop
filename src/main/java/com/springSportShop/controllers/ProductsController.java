/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.springSportShop.services.ProductsService;
import com.springSportShop.entities.*;
import com.springSportShop.services.CategoryService;
import com.springSportShop.services.ExcelFilesService;
import java.util.List;
/**
 *
 * @author maciejszwaczka
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;
    
    @Autowired
    private ExcelFilesService excelService;
    
    @Autowired
    private CategoryService categoryService;
    
    
    public ProductsController(CategoryService service,ExcelFilesService excelService,
        ProductsService prodService)
    {
        this.excelService=excelService;
        this.categoryService=service;
        this.productsService=prodService;
    }
    
    @PostMapping("/upload")
    public void uploadExcelFileWithProducts(@RequestParam("file") MultipartFile file) 
    {
        System.out.println(file.getOriginalFilename());
        List<Product> products=excelService.getProductsFromExcel(file);
        productsService.addListOfProducts(products);
    }
    
    @PostMapping("/add")
    public void addProduct(@RequestBody Product prod)
    {
        productsService.save(prod);
    }
    @GetMapping("/get")
    public List<Product> getAllProducts()
    {
        return productsService.getAll();
    }
    @GetMapping("/get/{id}")
    public Product getProductByID(@PathVariable Long id)
    {
        return productsService.getProductByID(id);
    }
}
