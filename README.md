# 项目说明

本项目为基于SSM框架搭建的博客系统，为本人的Java SSM练手之作，参考自[开源项目](https://github.com/saysky/ForestBlog)，在此表示感谢。

## 1. 项目进度

项目整体已经能够起来了，但仍有一些报错的地方尚待订正。

- 数据库被我命名为了ssmblog（全部小写），似乎改为ssmBlog（驼峰命名法）更显协调，但暂时懒得更订了。

## 2. 问题记录

1. `Serializable`接口只是一种标记接口，那么让实体类去继承它到底有什么作用？
2. `private static final long serialVersionUID = 5207865247400761539L;`语句的含义是什么？
3. aboutsite啥意思？
4. tongji啥意思？
5. XxxMapper.xml中的<resultMap>标签的子标签<result>的属性`jdbcType`有啥用？
6. 注意int和Integer是不一样的，Integer可以为null，int不可以。
7. SQL语句中有一些保留字，当数据库表的字段含有这些保留字时，对数据库表的字段进行引用时应该加入``字符。
8. `ArticleServiceImpl.java`中一些方法没有添加异常捕捉。
9. `@Transactional`注解？
10. `@Cacheable`注解？
11. `@CacheEvict`注解？
12. 探析spring-mvc与spring-core, spring-aop等的包含关系。
13. `AdminController.loginVerify()`的方法内没有使用json，注意一下这样是否正确。
14. 注意`ArticleTagRefMapper.xml`中第6行的注释所提到的问题。
15. 对于被事务控制的方法，在对其进行debug时，即便在内部打断点，也无法观察到数据库在一步步发生变化，因为它是一下子变化的（个人总结，不知对否）。
16. 