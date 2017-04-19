仓库代理有两种方式从方法名中推导出存储特定的查询。它可以从查询方法中直接推导出查询，或者也可以使用手动定义的查询。可用的选项依赖于实际的存储。然而，必须有一个策略决定创建什么样的实际查询。


## 1. 查询查找策略

有以下可用策略。用XML配置时，可以在namespace中通过<code>query-lookup-strategy</code>属性配置策略，或者使用基于JavaConfig的配置时，在Enable${store
}Repositories注解中通过<code>queryLookupStrategy</code>属性设置查找策略。一些策略对于特定的存储可能不支持：

* CREATE 尝试从查询方法的名称构造一个存储特定的查询。通用的方式是从方法名中移除一组已知的前缀然后解析剩余的部分。可参考[Query creation](http://docs.spring.io/spring-data/jpa/docs/1.11.1.RELEASE/reference/html/#repositories.query-methods.query-creation)

* USE_DECLARED_QUERY 
尽力查找一个生命的查询，如果找不到将抛出一个异常。这个查询可以在某个地方使用注解定义或者通过其他方法声明。参考特定存储的文档查找这个存储可用的选项。如果仓库基础架构无法在启动时找到声明的查询，将失败。

* CREATE_IF_NOT_FOUND （默认）组合了CREATE 和 
USE_DECLARED_QUERY
。它会首先查找一个声明的查询，然后如果没有声明的查询被找到，它会创建一个自定义的基于方法名的查询。这是默认的查找策略，并且因此如果没有显式配置任何策略它将被使用。此策略根据方法名允许快速查询定义，但也可以根据需要引入声明的查询。



## 2. 查询创建

基于Spring Data Repository基础架构上的查询生成器机制对于构建基于仓库的实体智商的限制查询是非常有用的。这个机制去掉方法中的前缀find..By ，read...By， query...By，count...By和get
...By，然后接卸剩余部分。引入的条件可以包含表达式，比如一个Distinct会设置创建的查询上的distinct标志。但是，第一个<code>By</code
>作为分隔符暗示真实规则的起始位置。在一个非常基础的层面上可以在entity属性上定义条件然后使用<code>And</code>和<code>Or</code>连接起来。

```java
public interface PersonRepository extends Repository<User, Long> {

  List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);

  // Enables the distinct flag for the query
  List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
  List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);

  // Enabling ignoring case for an individual property
  List<Person> findByLastnameIgnoreCase(String lastname);
  // Enabling ignoring case for all suitable properties
  List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);

  // Enabling static ORDER BY for a query
  List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
}
```

真实的解析结果依赖于持久化存储。有以下几点需要注意：

* 表达式通常是可连接的结合了运算符的属性遍历。可以使用AND和OR组合属性表达式。也支持运算符如Between，LessThan，GreaterThan，Like等。这些支持的运算符可能随着存储的不同而变化，所以参考相应的手册。
* 方法解析对于单个属性支持IngnoreCase标志（如findByLastNameIgnoreCase(...)
）或对于一个类型的所有属性支持忽略大小写（通常是字符串实例，如findByLastnameAndFirstnameAllIgnoreCase(...)）。是否支持忽略大小写根据存储的不同而不同。
* 可以通过添加OrderBy条件使用静态排序

## 3. 属性表达式

属性表达式可以仅仅指管理的entity的直接属性。在查询创建时可以确保解析的属性是被管理的域类的一个属性。但是，也可以通过遍历迁入属性另一限制。假设Person有一个带有ZipCode的Address。这种情况下的一个方法名如：

```java
List<Person> findByAddressZipCode(ZipCode zipCode);
```

创建属性遍历x.address.zipCode。这个解析算法首先将整个AddressZipCode解释为属性，并检查域类中具有该名称的属性（uncapitalized
）。如果算法成功了将使用这个属性。如果失败，算法将骆驼写法的源名称从右向左分和那个头和尾部，并尝试找到相应的属性，如AddressZipCode分成AdressZip 和 
Code。如果算法发现了具有该头部的属性，它会带着尾部并继续从此处构造属，将所有尾部按照刚刚描述的方式分割。如果第一次分割没有匹配到，算法将分割点向左移（Address,ZipCode)并继续。

尽管在大部分情况下可以起作用，但算法可能会选择错误的属性。比如，如果Person有一个属性addressZip，算法将在第一次分割时匹配到错误的属性，并最终失败，因为addressZip没有一个code
属性。为了解决这种歧义性可以在方法命中使用下划线<code>_</code>手动定义遍历点。

```java
List<Person> findByAddress_ZipCode(ZipCode zipCode);
```

## 4. 特殊参数的处理

除了正常使用的参数，还有一些特殊类型如Pageable和Sort用于动态分页和排序。

```java
Page<User> findByLastname(String lastname, Pageable pageable);

Slice<User> findByLastname(String lastname, Pageable pageable);

List<User> findByLastname(String lastname, Sort sort);

List<User> findByLastname(String lastname, Pageable pageable);
```

第一个方法给查询方法传递了一个<code>org.springframework.data.domain
.Pageable</code>实例来给静态的定义方法动态添加分页。一个Page知道元素总数和可用元素。它通过框架触发一个count查询计算总数量来实现这个目标。这可能对于依赖的存储来说是昂贵的，Slice
可以作为替代的返回值。一个Slice只知道是否还有下一个Slice可用，这对于遍历一个大的数据集来说可能是非常高效的。


排序选项也通过Pageable是处理。如果仅仅需要排序，只用为方法添加一个<code>org.springframework.data.domain
.Sort</code>参数。如上所述，简单地返回一个List也是可以的。这种情况下额外需要的用于构建实际Page实例的元数据将不会被创建（也就是说额外的count查询将不会是问题），而只需要限制查询搜索原始的范围即可。

> 为了查出到底有多少页不能不触发一个额外的count查询，默认的这个查询有实际触发的查询派生。 


## 5. 限制查询结果数

查询方法的结果数可以通过first或top关键字进行限制，可以互换使用。一个可选数量值可以追加到top/first以指定最大返回的结果数。如果数量没有指定，结果大小假设为1.

```java
User findFirstByOrderByLastnameAsc();

User findTopByOrderByAgeDesc();

Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);

Slice<User> findTop3ByLastname(String lastname, Pageable pageable);

List<User> findFirst10ByLastname(String lastname, Sort sort);

List<User> findTop10ByLastname(String lastname, Pageable pageable);
```

限制表达式也支持Distinct关键字。同时，如果对查询限制结果集至1个，将结果封装成一个Optional是支持的。

如果分页或者分片使用在限制查询分页上（并且计算可用页的数量），那么分页将被用于限制的结果之上。


## 6. 流化查询结果

查询方法的结果可以使用Java8 的Stream<T>作为返回类型进行逐步处理。相比较于仅仅将查询结果封装进一个Stream，存储特定的方法可用来执行流化。
```java
@Query("select u from User u")
Stream<User> findAllByCustomQueryAndStream();

Stream<User> readAllByFirstnameNotNull();

@Query("select u from User u")
Stream<User> streamAllPaged(Pageable pageable);
```

> 一个Stream必须被显式关闭

如在java 7中使用try with
```java
try (Stream<User> stream = repository.findAllByCustomQueryAndStream()) {
  stream.forEach(…);
}
```

> 并不是所有的Spring Data模块支持流操作

## 7. 异步查询结果

仓库查询可以使用Spring的异步方法执行能异步地执行。这意味着方法调用时将立即返回并且实际查询执行将发生在一个被递交到Spring TaskExecutor的任务中。

```java
@Async
Future<User> findByFirstname(String firstname);  // Use java.util.concurrent.Future as return type.             

@Async
CompletableFuture<User> findOneByFirstname(String firstname); // Use a Java 8 java.util.concurrent.CompletableFuture as return type.

@Async
ListenableFuture<User> findOneByLastname(String lastname);    // Use a org.springframework.util.concurrent.ListenableFuture as return type.



```