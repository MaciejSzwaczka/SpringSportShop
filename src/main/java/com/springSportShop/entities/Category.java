/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.PERSIST;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@NamedQueries(value = {
    @NamedQuery(name=Category.FIND_ALL,query="FROM Category b"),
    @NamedQuery(name=Category.FIND_CAT_BY_NAME,query="FROM Category where name=:cat")
})
public class Category implements Serializable {
    
    public static final String FIND_ALL="FIND_ALL_CATEGORIES";
    
    public static final String FIND_CAT_BY_NAME="FIND_BY_NAME";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name="superCategoryID")
    private Category superCategory;
    
    @JsonIgnore
    @OneToMany(mappedBy="superCategory",cascade={PERSIST})
    private List<Category> subCategories=new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy="category",cascade={PERSIST})
    private List<Product> products=new ArrayList<>();
}
