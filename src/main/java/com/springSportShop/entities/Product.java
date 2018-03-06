/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.entities;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.CascadeType.PERSIST;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author maciejszwaczka
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(
        {
            @NamedQuery(name=Product.FIND_ALL,query="from Product b")
        }
)
public class Product implements Serializable {

    public static final String FIND_ALL="FIND_ALL_PRODUCTS";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    private Double price;
    
    private String colour;
    
    private String season;
    
    @JsonIgnore
    @OneToMany(mappedBy="product",cascade={PERSIST})
    private List<Photo> images=new ArrayList<>();
    
    @OneToMany(mappedBy="product",cascade={PERSIST})
    private List<Size> sizes=new ArrayList<>(); 
    
    @ManyToOne
    @JoinColumn(name="categoryID")
    private Category category;
    
    @Transient 
    private String categoryName;
    
    private Integer quantity;
    
    @JsonIgnore
    @ManyToMany
    private List<Order> orders;
    
}
