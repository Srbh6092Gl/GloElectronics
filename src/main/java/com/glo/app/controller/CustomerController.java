package com.glo.app.controller;

import com.glo.app.exception.CustomerNotFoundException;
import com.glo.app.exception.RequestFieldNullException;
import com.glo.app.model.Customer;
import com.glo.app.model.Product;
import com.glo.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAll();
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) throws RequestFieldNullException {
        return customerService.add(customer);
    }

//    @PostMapping
//    public Customer addCustomer(@RequestBody CustomerRequestBody customerRequestBody) throws RequestFieldNullException {
//        Customer customer = Customer.builder().customerName(customerRequestBody.getCustomerName()).productList(new ArrayList<Product>()).build();
//        return customerService.add(customer);
//    }

    @PutMapping("/{customerId}")
    public Customer addProductsToCustomer(@PathVariable int customerId, @RequestBody List<Product> products) throws CustomerNotFoundException, RequestFieldNullException {
        if (ObjectUtils.isEmpty(customerId))
            throw new RequestFieldNullException("Customer id cannot be empty. Check endpoint");
        if (ObjectUtils.isEmpty(products))
            throw new RequestFieldNullException("Product list cannot be empty");
        for(Product product: products)
            if (ObjectUtils.isEmpty(product.getProductName()) || ObjectUtils.isEmpty(product.getProductPrice()))
                throw new RequestFieldNullException("Product details cannot be empty. Check: " + product);
        return customerService.addProducts(customerId, products);
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) throws RequestFieldNullException, CustomerNotFoundException {
        if (ObjectUtils.isEmpty(customerId))
            throw new RequestFieldNullException("Customer id cannot be empty. Check endpoint");
        return customerService.delete(customerId);
    }

}
