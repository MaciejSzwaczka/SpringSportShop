/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springSportShop.services;
import com.springSportShop.entities.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import org.json.*;
import java.util.stream.Stream;
import javax.persistence.EntityTransaction;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author maciejszwaczka
 */
@Service
public class ExchangeService {
    
    private Client client;
    
    private WebTarget target;
    
    public ExchangeService()
    {
        client=ClientBuilder.newClient();
        target=client.target("http://api.nbp.pl/api/exchangerates/tables/A?format=json");
    }
    public void productPriceExchange(Product prod, String currency)
    {
        String requestStr=target.request(MediaType.TEXT_PLAIN).get(String.class);
        int x = requestStr.indexOf("{");
        requestStr = requestStr.substring(x);
        JSONObject json = new JSONObject(requestStr.trim()); 
        JSONObject request=new JSONObject(requestStr);
        JSONArray rateArray=request.getJSONArray("rates");
        for(int i=0;rateArray.length()>i;i++)
        {
            JSONObject obj=rateArray.getJSONObject(i);
            if(obj.getString("code").equals(currency))
            {
                System.out.print(obj.getString("code"));
                prod.setPrice(prod.getPrice()/obj.getDouble("mid"));
                break;
            }
        }
    }
    public void listOfProductsPriceExchange(List<Product> prods, String currency)
    {
        for(Product prod:prods)
        {
            productPriceExchange(prod,currency);
        }
    }
}
