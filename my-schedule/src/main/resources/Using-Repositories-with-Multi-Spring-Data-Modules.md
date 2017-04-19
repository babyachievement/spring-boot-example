这里的多个"Spring Data模块"指的是在class path中同时存在多个Spring Data模块，Repository将绑定到哪个Spring Data模块上呢？

只有一个Spring Data模块时，事情非常简单，所有定义范围内的仓库接口都绑定到这个Spring Data模块。但有时程序需要用到多个Spring Data模块，这种情况下，对于一个仓库定义区分不同的持久化技术是必需的。

因会在类路径下探测到多个仓库工厂，Spring Data进入严格仓库定义模式。严格的仓库定义需要仓库或者域类型细节来决定仓库定义将绑定到哪个Spring Data 模块上：

1. 如果仓库定义继承了模块特定的仓库接口，那么它会是这个特定Spring Data模块的有效候选
2. 如果域类型使用了模块特定的注解，那么它是这个特定Spring Data模块的有效候选者。Spring Dta模块接受第三方注解（如JPA的@Entity）或提供子集的主机如Spring Data MongoDB/Spring 
Data Elasticsearch的@Document注解

## 1. 使用了模块特定接口的仓库定义

```java
interface MyRepository extends JpaRepository<User, Long> { }

@NoRepositoryBean
interface MyBaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
  …
}

interface UserRepository extends MyBaseRepository<User, Long> {
  …
}
```

MyRepository 和 UserRepository 继承了 JpaRepository。他们是Spring Data JPA模块的有效候选者。


## 2. 使用通用接口的仓库定义

```java
interface AmbiguousRepository extends Repository<User, Long> {
 …
}

@NoRepositoryBean
interface MyBaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
  …
}

interface AmbiguousUserRepository extends MyBaseRepository<User, Long> {
  …
}
```

AmbiguousRepository 和 AmbiguousUserRepository 仅仅继承了 Repository 和 CrudRepository 。在只有一个Spring Data 
模块时，这是完全没问题的，但如果有多个Spring Data模块存在时，将无法区别出哪个将被绑定。


## 3. 使用带注解域类型的仓库定义

```java
interface PersonRepository extends Repository<Person, Long> {
 …
}

@Entity
public class Person {
  …
}

interface UserRepository extends Repository<User, Long> {
 …
}

@Document
public class User {
  …
}
```

PersonRepository 引用了使用JPA注解@Entity的Person，所以这个仓库将归属于Spring Data JPA。UserRepository使用了带Spring Data
 MongoDB的@Document注解的User。
 
 
 
 ## 4. 使用有多个注解的域类型的仓库定义
 
 ```java
interface JpaPersonRepository extends Repository<Person, Long> {
 …
}

interface MongoDBPersonRepository extends Repository<Person, Long> {
 …
}

@Entity
@Document
public class Person {
  …
}
```

这个例子中域类型同时使用了JPA和Spring Data MongoDB注解。它定义了两个仓库：JpaPersonRepository和MongoDBPersonRepository。一个用于JPA，另一个用于MongoDB
。Spring Data将不再能够分辨出存储仓库哪个导致了未定义行为。



Repository类型细节和域类型注解用于严格的仓库配置以识别特定Spring Data模块的候选者。在同一个域类型上使用多个持久化技术特定注解能够跨多个持久化技术重用域类型，但是之后Spring 
Data将无法决定出一个绑定到这个仓库上的唯一Spring Data模块。


##  5. 使用基本包
最后一个用于区分仓库的方法是限制仓库的base packages。base packages定义了扫描仓库接口定义的起点，暗示了在相应的包中有仓库定义。默认的，注解驱动的配置使用配置类的包。在基于XML的配置中，基本包是强制的。

```java
@EnableJpaRepositories(basePackages = "com.acme.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.acme.repositories.mongo")
interface Configuration { }
```