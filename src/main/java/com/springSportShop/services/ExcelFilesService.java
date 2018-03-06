/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.services;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.*;
import com.springSportShop.entities.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.util.Map;
import java.util.TreeMap;
import java.io.FileNotFoundException;
import java.util.Arrays;
/**
 *
 * @author maciejszwaczka
 */
@Service
public class ExcelFilesService {
    private final String path=System.getProperty("user.dir");
    
    @Autowired
    final CategoryService categoryService;
    
    @Autowired
    public ExcelFilesService(CategoryService service)
    {
        System.out.println("excel service");
        this.categoryService=service;
    }
    public List<Product> getProductsFromExcel(MultipartFile file)
    {
        List<Product> addedProducts=new ArrayList<>();
        
        File createdFile=new File(path+"\\"+file.getOriginalFilename());
        
        System.out.println(path+"\\"+file.getOriginalFilename());
        Map<String,Integer> indsOfFields=new TreeMap<>();
        FileInputStream inputStream=null;
        HSSFWorkbook workbook=null;
        try{
            file.transferTo(createdFile);
            createdFile.createNewFile();
            inputStream=new FileInputStream(createdFile);
            workbook=new HSSFWorkbook(inputStream);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        HSSFSheet sheet=workbook.getSheetAt(0);
        indsOfFields=getCellMapping(sheet);
        int rowsNum=sheet.getLastRowNum();
        HSSFRow row;
        for(int i=1;rowsNum>i;i++)
        {
            row=sheet.getRow(i);
            Product createdProduct=new Product();
            int amount=/*(int)row.getCell(indsOfFields.get("Ilość")).getNumericCellValue()*/200;
            String name=row.getCell(indsOfFields.get("Nazwa")).getStringCellValue();
            double price=row.getCell(indsOfFields.get("Cena")).getNumericCellValue();
            String season=row.getCell(indsOfFields.get("Sezon")).getStringCellValue();
            String colour=row.getCell(indsOfFields.get("Kolor")).getStringCellValue();
            String imagesString=row.getCell(indsOfFields.get("Obrazy")).getStringCellValue();
            String sizesString=row.getCell(indsOfFields.get("Rozmiar")).getStringCellValue();
            String sizesStrings[]=sizesString.split(",");
            List<Size> sizes=new ArrayList<>();
            for(String size:sizesStrings)
            {
                System.out.print(size);
                sizes.add(new Size(null,size,createdProduct));
            }
            String[] imagesURLs=imagesString.split(","); 
            ArrayList<Photo> images=new ArrayList<>();
            for(String str:imagesURLs)
            {
                images.add(new Photo(null,str,createdProduct));
            }
            createdProduct.setName(name);
            createdProduct.setPrice(price);
            createdProduct.setSizes(sizes);
            createdProduct.setImages(images);
            createdProduct.setColour(colour);
            createdProduct.setSeason(season);
            createdProduct.setQuantity(200);
            addedProducts.add(createdProduct);
        }
        return addedProducts;
    }
    /*Used only in deprecated version*/
    public static Map<String,Integer> getCellMapping(HSSFSheet sheet)
    {
        Map<String,Integer> indsOfFields=new TreeMap<>();
        HSSFRow titularRow=sheet.getRow(0);
        int cellsNum=titularRow.getLastCellNum();
        for(int i=0;cellsNum>i;i++)
        {
            indsOfFields.put(titularRow.getCell(i).getStringCellValue(), i);
        }
        return indsOfFields;
    }
    public static Map<String,List<String>> splitProductsAttributes(String attributesString)
    {
        Map<String,List<String>> mapOfProducts=new TreeMap<>();
        String[] arrayOfSplittedAttributesParts=attributesString.split(",");
        for(String attribute:arrayOfSplittedAttributesParts)
        {
            List<String> attributes=new ArrayList<>();
            String[] splittedParts=attribute.split(":");
            String[] attributesValues=splittedParts[1].split(" ");
            attributes=new ArrayList<>(Arrays.asList(attributesValues));
            mapOfProducts.put(splittedParts[0], attributes);
        }
        return mapOfProducts;
    }      
}
