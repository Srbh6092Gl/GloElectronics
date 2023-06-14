package com.glo.app.service;

import com.glo.app.exception.CustomerNotFoundException;
import com.glo.app.exception.RequestFieldNullException;
import com.glo.app.model.Customer;
import com.glo.app.model.Product;
import com.glo.app.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @Override
    public Customer add(Customer customer) throws RequestFieldNullException
    {
        if (customer.getCustomerName()==null || customer.getCustomerName() == "")
            throw new RequestFieldNullException("Name field cannot be null");
        return customerRepo.save(customer);
    }

    @Override
    public Customer addProducts(int customerId, List<Product> products) throws CustomerNotFoundException {
        checkCustomerExists(customerId);
        Customer customer = customerRepo.findById(customerId).get();
        List<Product> list = customer.getProductList();
        list.addAll(products);
        customer.setProductList(list);
        customerRepo.save(customer);
        return customerRepo.findById(customerId).get();
    }

    @Override
    public String delete(int customerId) throws CustomerNotFoundException {
        checkCustomerExists(customerId);
        customerRepo.delete(customerRepo.findById(customerId).get());
        return "Deletion is successful";
    }

    public void checkCustomerExists(int customerId) throws CustomerNotFoundException {
        if(customerRepo.findById(customerId).isEmpty())
            throw new CustomerNotFoundException("Customer not found with id: "+ customerId);
    }
}
