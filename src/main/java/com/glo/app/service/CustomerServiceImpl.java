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
        //If customer name is empty or null, throw exception
        if (customer.getCustomerName()==null || customer.getCustomerName() == "")
            throw new RequestFieldNullException("Name field cannot be null");
        return customerRepo.save(customer);
    }

    @Override
    public Customer addProducts(int customerId, List<Product> products) throws CustomerNotFoundException {
        //Check if customer exists
        checkCustomerExists(customerId);

        //get customer
        Customer customer = customerRepo.findById(customerId).get();
        //get list of products of customer
        List<Product> list = customer.getProductList();
        //add new products
        list.addAll(products);
        //set modified product list to customer
        customer.setProductList(list);
        //save updated customer
        customerRepo.save(customer);
        return customerRepo.findById(customerId).get();
    }

    @Override
    public String delete(int customerId) throws CustomerNotFoundException {
        //check if customer is present
        checkCustomerExists(customerId);
        //get customer by id, then delete it
        customerRepo.delete(customerRepo.findById(customerId).get());
        //return success response
        return "Deletion is successful";
    }

    public void checkCustomerExists(int customerId) throws CustomerNotFoundException {
        //if customer is not present then throw exception
        if(customerRepo.findById(customerId).isEmpty())
            throw new CustomerNotFoundException("Customer not found with id: "+ customerId);
    }
}
