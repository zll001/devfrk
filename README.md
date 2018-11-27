# devfrk
## 1.研发背景
  开发规范不统一会增加开发人员学习成本，并严重影响开发质量。本文主要描述开发规范。

## 2.设计原则

- 服务化：以实现服务化和权限试点为目标，不追求过度微服务化
- 规范化：试图解决服务化与业务逻辑耦合，统一异常处理等问题。
- 程序化：提供机器的testcase，用于检查开发人员是否存在违反开发规范的问题

## 3 详细规范

### 3.1 URL请求规范

我们将URL分为ContextPath和ServletPath两部分：

- ContextPath是在application.yml中配置的： 包含端口、模块名与版本号

```
server:
  port: 8081
  context-path: /application/2.0
```

- ServletPath会通过本框架进行管控，包含服务名与具体操作名

```
1. 当前只支持Http，不支持Https
2. 如果请求的URL为http://127.0.0.1：8081/application/2.0/user/list，则ContextPath为http://127.0.0.1：8081/application/2.0， ServletPath为user/list
3. 我们约定ServletPath必须为两层，比如/user/list，其中user为服务名，list为具体操作
4. 如果ServletPath为为一层，或者超过两层，均违约本规范，这意味着/user,或者/user/list/byname都会抛出异常
5. URL请求只支持Get, Post, Put和Delete四种，数据传递通过requestBody，且其格式为Json
      5.1 Get操作必须以create, list或者query开头，可以没有requestBody
      5.2 Delete操作必须以delete，或者reomve开头，必须有requestBody
      5.3 其它操作没有硬性要求，但必须有requestBody
```

### 3.2 ServletPath的注册规范

如前所描述，ServletPath必须为两层，比如/userService/list，其中user为服务名，list为具体操作

- 服务名必须以Service结束，必须实现com.github.qcase.webfrk.core.spi.HttpBodyHandler，且打上com.github.qcase.webfrk.core.spi.ServiceDefinition标签，框架会识别该标签，将其转化为服务名，转换规则是类名首字母小写，然后删除Service。如MockService，对应的服务名为mock, 如CustomeUserService对应的服务名为customeUser

```
@ServiceDefinition
public class MockService extends HttpBodyHandler {
}
```
- 具体操作即为服务类中的方法，你可以根据自己需求进行命名，其必须打上com.github.qcase.webfrk.core.spi.BeanDefinition标签，如方法名为listMock，且操作名为listMock

```
@ServiceDefinition
public class MockService extends HttpBodyHandler {
   @BeanDefinition
	public String listMock() {
		return "Mock";
	}
}
```

- 打上com.github.qcase.webfrk.core.spi.BeanDefinition标签的方法名必须唯一，不运行出现方法名一样，参数不一样的定义
- 根据上述规范，当请求的ServerletPath为/mock/listMock时，实际上会映射到MockService的listMock方法进行处理


### 3.3 BeanDefinition的使用规范

假设请求的Json为

```
{
  "A" : ObjectA
  "B" : ObjectB
}
```

- 如果你想整体获取Json信息，则BeanDefinition无需传递参数，而在参数中类型可以是该Json能转换的任何类型

```
将Json转换为Map
@BeanDefinition
public String createMock(Map<String, String> map) {
		return map.toString();
}

或者将Json转换为User对象，假设User预先定义
@BeanDefinition
public String createMock(User user) {
		return user.toString();
}

```
- 如果你想分别获取Json信息，则BeanDefinition需传递参数，参数即为Json的key数值，且与参数的顺序一致，如将Json的A的信息传递给name，将B的信息传递给map。具体参数使用什么类型，可以自定义，只要Json能转换即可。

```
@BeanDefinition(names= {"A", "B"})
	public String deleteMock(String name, Map<String, String> map) {
		return name + map.toString();
	} 
```

### 3.4 业务逻辑的处理规范

- 对于BeanDefinition对应的方法实现，可以是void的方法，否则可以返回除去JSON对象的任意返回数值，如:

```
满足规范
@BeanDefinition
public void createMock() {
	return;
}

满足规范
@BeanDefinition
public String createMock() {
	return user.toString();
}

满足规范
@BeanDefinition
public Map createMock() {
	return user.toMap();
}

满足规范
@BeanDefinition
public Object createMock() {
	return user;
}

不满足规范
@BeanDefinition
public JSON createMock() {
	return JSON;
}

不满足规范
@BeanDefinition
public JSONObject createMock() {
	return JSONObject;
}
```
- 对于BeanDefinition对应的方法实现，如果有异常，则统一抛出，但需要明确异常原因

```
public void createMock() {
	if ...
	   throw new Execption("Mysql is not start");
}
```
- 返回值一定是Json格式，包含status, message和result三项
     - 返回状态500表示有异常，message会详细描述原因，result为null
     - 返回状态200表示正常，message为null，而result会使返回结果的json格式（如果是void方法，则result为null）


### 3.5 开发的约束条件

- 不允许使用的annotation

```
RequestMapping
GetMapping
PostMapping
DeleteMapping
PutMapping
RestController
Controller
ControllerAdvice
RequestBody
ResponseBody
Component
Service
Bean
```

- 只允许使用的javax.validation.constraints，不支持spring自身的validation（尚未开发）

```
@Null 被注释的元素必须为null 
@NotNull 被注释的元素不能为null 
@AssertTrue 被注释的元素必须为true 
@AssertFalse 被注释的元素必须为false 
@Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 
@Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 
@DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 
@Size(max,min) 被注释的元素的大小必须在指定的范围内。
@Digits(integer,fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内 
@Past 被注释的元素必须是一个过去的日期
@Future 被注释的元素必须是一个将来的日期
@Pattern(value) 被注释的元素必须符合指定的正则表达式。 
@Email 被注释的元素必须是电子邮件地址 
@Length 被注释的字符串的大小必须在指定的范围内 
@NotEmpty 被注释的字符串必须非空 
@Range 被注释的元素必须在合适的范围内
```

### 3.6 返回Json规范


- 如果输入错误的URL，比如IP和Port写错，则返回HTTP的500错误
- 其它情况返回HTTP的200，但通过status进行区分，status只有200和500两个状态

```
正常返回
{
    "status": 200,
    "object": "..."
}
```

```

异常返回
{
    "status": 500,
    "message": "Unsupport request URL"
}

```

