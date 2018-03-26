/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.controllers;
import org.springframework.web.bind.annotation.*;
import com.springSportShop.services.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.springSportShop.services.ProductsService;
import com.springSportShop.entities.*;
import com.springSportShop.services.CategoryService;
import com.springSportShop.services.ExcelFilesService;
import java.util.ArrayList;
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
    
    @Autowired 
    private ExchangeService exchangeService;
    
    public ProductsController(CategoryService service,ExcelFilesService excelService,
        ProductsService prodService, ExchangeService exchService)
    {
        this.excelService=excelService;
        this.categoryService=service;
        this.productsService=prodService;
        this.exchangeService=exchService;
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
    public List<Product> getAllProducts(@RequestParam(value="currency", required=false) String currency,
            @RequestParam(value="lowestPrice", required=false, defaultValue="0") Integer lowestPrice,
            @RequestParam(value="maxPrice", required=false, defaultValue="5000") Integer maxPrice
    )
    {
        List<Product> prods=new ArrayList<>();
        if(lowestPrice==0 && maxPrice==Integer.MAX_VALUE)
        {
            prods=productsService.getAll();
        }
        else{
            prods=productsService.getAllInPriceRange(lowestPrice,maxPrice);
        }
        if(currency.equals("PLN"))
        {
            return prods;
        }
        else{
            exchangeService.listOfProductsPriceExchange(prods, currency);
            return prods;
        }
    }
    @GetMapping("/get/{id}")
    public Product getProductByID(@PathVariable Long id)
    {
        return productsService.getProductByID(id);
    }
}
