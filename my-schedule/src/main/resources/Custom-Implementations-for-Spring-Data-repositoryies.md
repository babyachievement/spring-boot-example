通常为一些仓库方法提供自定义的实现是必要的。Spring  Data 仓库可以方便地让用户提供自定义仓库代码，并将它和通用CRUD抽象以及查询方法功能集成到一起。

## 1. 为单个仓库提供自定义行为

为了使用自定义功能丰富一个仓库需要首先顶一个接口，并未自定义的功能提供固始县。使用提供的仓库接口集成这个自定义接口即可。

自定义的接口：
```java
interface UserRepositoryCustom {
  public void someCustomMethod(User user);
}
```

自定义接口的实现：

```java
class UserRepositoryImpl implements UserRepositoryCustom {

  public void someCustomMethod(User user) {
    // Your custom implementation
  }
}
```

> 最重要的一点是实现类的名称是自定义接口名称后边加上后缀Impl。

实现自身并不依赖Spring Data，并且可以是一个常规Bean。若依可以使用标准依赖注入将引用注入到其他bean，如一个JdbcTemplate，参与切面等等。

```java
interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

  // Declare query methods here
}
```


## 2. 给所有Repository添加自定义行为

给所有仓库添加自定义行为需要首先添加一个中间接口，声明共享行为：
```java
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable>
  extends PagingAndSortingRepository<T, ID> {

  void sharedCustomMethod(ID id);
}
```

然后自己的Repository继承这个中间接口而不是Repository接口。然后创建一个中间接口的实现，并且继承了持久化技术特定的Repository基类。这个类将作为自定义了基类的仓库代理。

```java
public class MyRepositoryImpl<T, ID extends Serializable>
  extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {

  private final EntityManager entityManager;

  public MyRepositoryImpl(JpaEntityInformation entityInformation,
                          EntityManager entityManager) {
    super(entityInformation, entityManager);

    // Keep the EntityManager around to used from the newly introduced methods.
    this.entityManager = entityManager;
  }

  public void sharedCustomMethod(ID id) {
    // implementation goes here
  }
}
```

> 这个类需要提供一个存储特定仓库工厂实现使用的超类的构造函数。这种情况下仓库基类有多个构造函数，覆盖其中将EntityInformation和存储特定架构对象（如EntityManager或一个模板类)作为参数的构造函数。

最后一步是让Spring Data框架干摘到自定义仓库基类。在JavaConfig使用@Eanble...Repositories的repositoryBaseClass属性实现。

```java
@Configuration
@EnableJpaRepositories(repositoryBaseClass = MyRepositoryImpl.class)
class ApplicationConfiguration { … }
```