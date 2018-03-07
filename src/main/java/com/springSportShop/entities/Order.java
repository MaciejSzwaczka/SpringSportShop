/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;


/**
 *
 * @author maciejszwaczka
 */
@Entity(name="Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Orders")
@NamedQueries(value={
    @NamedQuery(name=Order.FIND_ALL,query="from Orders b")
})
public class Order implements Serializable {

    public static final String FIND_ALL = "FIND_ALL_ORDERS";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade={MERGE})
    private List<Product> products=new ArrayList<>();
    
    private Date dateOfOrder;
    
    /*@ManyToOne
    @JoinColumn(name="userId")
    private User user;*/
    
}