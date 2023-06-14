package com.glo.app.service;

import com.glo.app.exception.CustomerNotFoundException;
import com.glo.app.exception.RequestFieldNullException;
import com.glo.app.model.Customer;
import com.glo.app.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerService {
    Customer add(Customer customer) throws RequestFieldNullException;

    Customer addProducts(int customerId, List<Product> products) throws CustomerNotFoundException;

    String delete(int customerId) throws CustomerNotFoundException;

    List<Customer> getAll();
}
