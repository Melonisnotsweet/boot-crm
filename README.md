# boot-crm
SSM框架整合boot管理系统
首先简单思考以下如果我们进行分页需要哪些数据？

1.需要分页数据的总条数。
2.每页需要分多少条。
3.需要求出每一页开始的是那一条。
在本次项目中，需要写一个Page类作为工具使用。在Page页面中需要定义什么属性呢？
1.总条数 total//总计
2.当前页 page//当前页面
3.页数     size//
4.结果集  rows//这里指的是在这一页中的结果集

在Dao层创建完接口和映射文件后，需要在服务层创建接口和实现类

那么现在考虑一下在服务层创建的接口需要在前端接受什么数据？返回什么数据？
1.当前页数//用来求起始行
2.页面总行数//表示这一页需要查询多少数据
3.查询条件中出现的客户名称，客户来源，客户所属行业，客户级别。为什么要查询这个呢？
因为在前端的设计中，我么设计了通过这四种条件的模糊查询来得到数据。
现在得知需要的参数后，可以在服务层写一个用Page工具包装的Customer类接口。findCustomerList

在服务层编写实现类代码。
首先应该考虑写这层代码是干什么的，他的作用是什么？
是为了进行事务管理的，在这个类里边应该实现什么样的功能？
继承了CustomerService接口后需要实现CustomerService接口中的方法，返回值是Page类型的参数。
Page类有什么属性？
当前页面//page
页面总行数//size
结果集//rows
数据总条数//count
这些数据都是怎么得到的？
前端传过来的数据中有当前页page和页面行数rows这个可以直接set到Page的返回结果中。
结果集指的是Customer中的结果集。根据传入的参数我么可以调用的CustomerDao中的方法来进行查询。通过映射返回结果集。
在此之前我们应先判断传入的参数是否非空。。。需要用到isNotBlank   判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成，等于!isBlank(String str)。在CustomerDao的映射文件中分页还需要页面起始数据和页面总数据，即start=(page-1)*row,row.通过这些数据我们可以得到要查询到客户列表，和customer中的客户总条数。最后将结果集返回给调用它的控制层。

在编写控制层代码之前我们还需要编写BaseDict的Dao层接口和映射文件，还有服务层接口和实现类。目的是查询数据字典中客户来源，客户行业，和客户级别的数据，编写数据自动各个接口和实现类的目的？
前端界面中在查询窗口部分需要。

@Value通过前端控制器的配置文件中扫描resource.properties 可以的得到其中的数据并注入到控制层
编写控制层代码的目的？

实现与前端页面的交互，返回值是jsp页面的名称。

在方法的形参中需要接收前端传过来的参数。需要接受什么参数？
1.当前页面
2.页面总条数
3.customer中的客户姓名，客户来源，客户行业，客户级别。
4.定义Model用来往前端传输数据。

调用CustonerService中的findCustomerList方法要注意的是在不使用注解的情况下，控制层的形参要与前端页面中的形参名字相同，这样才能接受到数据。
调用BaseDictService中的方法查询各个属性。


在控制层的传输参数中，用到了一个注解@RequestParam
@RequestParam有三个属性值
value:可以理解为使用这个属性之后配置的值是不可变的
required:是否必须的意思，默认值是true，可以理解为是不是不可变的
defaultvalue:这里边的值是可变的。
在设计分页时应该考虑到当前页是变化的，可以赋初值但是页面是变化的，页面总行数考虑到最后一页是不知道有几条数据，这样页面总行是也是可变的。所以应该用defaultcalue属性。














