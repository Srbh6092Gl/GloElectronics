package com.glo.app.controller;

import com.glo.app.exception.CustomerNotFoundException;
import com.glo.app.exception.RequestFieldNullException;
import com.glo.app.model.Customer;
import com.glo.app.model.Product;
import com.glo.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    //Returning all customers
    @GetMapping
    public Page<Customer> getAllCustomers(){
//        return customerService.getAll();
        return customerService.getAllSorted();
    }

    //Adding customers
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) throws RequestFieldNullException {
        return customerService.add(customer);
    }

    @PutMapping("/{customerId}")
    public Customer addProductsToCustomer(@PathVariable int customerId, @RequestBody List<Product> products) throws CustomerNotFoundException, RequestFieldNullException {
        //checking if customerId is empty to throw exception
        if (ObjectUtils.isEmpty(customerId))
            throw new RequestFieldNullException("Customer id cannot be empty. Check endpoint");
        //checking if list of products is empty to throw exception
        if (ObjectUtils.isEmpty(products))
            throw new RequestFieldNullException("Product list cannot be empty");
        //checking if any product from the list has missing attributes to throw exception
        for(Product product: products)
            if (ObjectUtils.isEmpty(product.getProductName()) || ObjectUtils.isEmpty(product.getProductPrice()))
                throw new RequestFieldNullException("Product details cannot be empty. Check: " + product);

        //No error
        //return response
        return customerService.addProducts(customerId, products);
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) throws RequestFieldNullException, CustomerNotFoundException {
        //checking if customerId is empty to throw exception
        if (ObjectUtils.isEmpty(customerId))
            throw new RequestFieldNullException("Customer id cannot be empty. Check endpoint");
        //No error
        //Delete from service
        //return response
        return customerService.delete(customerId);
    }

}
