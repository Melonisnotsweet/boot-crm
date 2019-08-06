package com.crm.core.service;
import com.crm.common.utils.Page;
import com.crm.core.po.Customer;
public interface CustomerService {
	// 查询客户列表
	public Page<Customer> findCustomerList(Integer page, Integer rows,
                                           String custName, String custSource,
                                           String custIndustry, String custLevel);
	public Integer createCustomer(Customer customer);
    public Customer getCustomerById(Integer id);
    public Integer updateCustomer(Customer customer);
    public Integer deleteCustomer(Integer id);

}
