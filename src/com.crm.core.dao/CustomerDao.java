package com.crm.core.dao;

import com.crm.core.po.Customer;

import java.util.List;

public interface CustomerDao {
    public List<Customer> selectCustomerList(Customer customer);

    public Integer selectCustomerListCount(Customer customer);

    public Integer createCustomer(Customer customer);

    public Customer getCustomerById(Integer id);

    public Integer updateCustomer(Customer customer);

    public Integer deleteCustomer(Integer id);

}
