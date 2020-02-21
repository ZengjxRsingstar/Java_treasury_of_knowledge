# 1.SpringBoot的自动配置流程

https://github.com/ZhongFuCheng3y/3ySpringBoot自动配置原理

@SpringBootApplication

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```

```
@SuppressWarnings("deprecation")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
```

EnableAutoConfiguration 会自动载入默认配置

`@AutoConfigurationPackage`注解解释成**自动配置包**依靠的是@Import

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {

}
```

AutoConfiurationPackage:

```
	@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata,
				BeanDefinitionRegistry registry) {
			register(registry, new PackageImport(metadata).getPackageName());
		}
```

```
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
进入 EnableAutoConfigurationImportSelector
```

进入：List<String> configurations  配置列表

```
AutoConfigurationImportSelector：

@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		try {
			AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
					.loadMetadata(this.beanClassLoader);
			AnnotationAttributes attributes = getAttributes(annotationMetadata);
			List<String> configurations = getCandidateConfigurations(annotationMetadata,
					attributes);
			configurations = removeDuplicates(configurations);
			configurations = sort(configurations, autoConfigurationMetadata);
			Set<String> exclusions = getExclusions(annotationMetadata, attributes);
			checkExcludedClasses(configurations, exclusions);
			configurations.removeAll(exclusions);
			configurations = filter(configurations, autoConfigurationMetadata);
			fireAutoConfigurationImportEvents(configurations, exclusions);
			return configurations.toArray(new String[configurations.size()]);
		}
```

进入：getCandidateConfigurations方法通过SpringFactoriesLoader来加载

```
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata,
      AnnotationAttributes attributes) {
   List<String> configurations = SpringFactoriesLoader.loadFactoryNames(
         getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
   Assert.notEmpty(configurations,
         "No auto configuration classes found in META-INF/spring.factories. If you "
               + "are using a custom packaging, make sure that file is correct.");
   return configurations;
}
```

进入loadFacoryNames方法：

```
public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader) {
   String factoryClassName = factoryClass.getName();
   try {
      Enumeration<URL> urls = (classLoader != null ? classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
            ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
      List<String> result = new ArrayList<String>();
      while (urls.hasMoreElements()) {
         URL url = urls.nextElement();
         Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
         String factoryClassNames = properties.getProperty(factoryClassName);
         result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
      }
      return result;
   }
```

FACTORIES_RESOURCE_LOCATION：

```java
public abstract class SpringFactoriesLoader {

   private static final Log logger = LogFactory.getLog(SpringFactoriesLoader.class);

   /**
    * The location to look for factories.
    * <p>Can be present in multiple JAR files.
    */
   public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
```

@SpringBootApplication`等同于下面三个注解：

- `@SpringBootConfiguration`
- `@EnableAutoConfiguration`
- `@ComponentScan`

其中`@EnableAutoConfiguration`是关键(启用自动配置)，内部实际上就去加载`META-INF/spring.factories`文件的信息，然后筛选出以`EnableAutoConfiguration`为key的数据，加载到IOC容器中，实现自动配置功能！