package com.crm.core.controller;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.common.utils.Page;
import com.crm.core.po.BaseDict;
import com.crm.core.po.Customer;
import com.crm.core.po.User;
import com.crm.core.service.BaseDictService;
import com.crm.core.service.CustomerService;
/**
 * 客户管理控制器类
 */
@Controller
public class CustomerController {
	// 依赖注入
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BaseDictService baseDictService;
	// 客户来源
	@Value("${customer.from.type}")
	private String FROM_TYPE;
	// 客户所属行业
	@Value("${customer.industry.type}")
	private String INDUSTRY_TYPE;
	// 客户级别
	@Value("${customer.level.type}")
	private String LEVEL_TYPE;
	/**
	 *  客户列表
	 */
	@RequestMapping(value = "/customer/list.action")
	public String list(@RequestParam(defaultValue ="1")Integer page,
			@RequestParam(defaultValue="10")Integer rows,
			String custName, String custSource, String custIndustry,
			String custLevel, Model model) {
		// 条件查询所有客户
		Page<Customer> customers = customerService
				.findCustomerList(page, rows, custName, 
                                        custSource, custIndustry,custLevel);
		model.addAttribute("page", customers);
		// 客户来源
		List<BaseDict> fromType = baseDictService
				.findBaseDictByTypeCode(FROM_TYPE);
		// 客户所属行业
		List<BaseDict> industryType = baseDictService
				.findBaseDictByTypeCode(INDUSTRY_TYPE);
		// 客户级别
		List<BaseDict> levelType = baseDictService
				.findBaseDictByTypeCode(LEVEL_TYPE);
		// 添加参数
		model.addAttribute("fromType", fromType);
		model.addAttribute("industryType", industryType);
		model.addAttribute("levelType", levelType);
		model.addAttribute("custName", custName);
		model.addAttribute("custSource", custSource);
		model.addAttribute("custIndustry", custIndustry);
		model.addAttribute("custLevel", custLevel);
		return "customer";
	}
	@RequestMapping(value = "customer/create.action")
	@ResponseBody
	public String customerCreate(Customer customer,HttpSession session)
	{
		//从session中获取用户id
		User user= (User) session.getAttribute("USER_SESSION");
		customer.setCust_create_id(user.getUser_id());
		//获取当前时间并且将获取的时间
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		customer.setCust_createtime(timestamp);
		int rows=customerService.createCustomer(customer);
		if (rows>0)
			return "OK";
		else
			return "FALL";
	}
    @RequestMapping(value = "customer/getCustomerById.action")
	@ResponseBody
	public Customer getCustomerById(Integer id)
	{
		Customer customer=customerService.getCustomerById(id);
		return customer;
	}
    @RequestMapping(value = "customer/update.action")
	@ResponseBody
	public String updateCustomer(Customer customer)
	{
      int rows=customerService.updateCustomer(customer);
      if (rows>0)
      	return "OK";
      else
      	return "Fall";
	}
	@RequestMapping(value = "customer/delete.action")
	@ResponseBody
	public String deleteCustomer(Integer id)
	{
		int rows=customerService.deleteCustomer(id);
		if (rows>0)
			return "OK";
		else
			return "FALL";
	}
}
