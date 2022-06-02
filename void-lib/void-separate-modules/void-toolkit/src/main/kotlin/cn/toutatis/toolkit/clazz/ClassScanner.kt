package cn.toutatis.toolkit.clazz

import org.springframework.context.ResourceLoaderAware
import java.util.LinkedList
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.type.classreading.MetadataReaderFactory
import org.springframework.core.type.classreading.CachingMetadataReaderFactory
import org.springframework.core.io.support.ResourcePatternUtils
import java.util.HashSet
import org.springframework.util.SystemPropertyUtils
import java.lang.ClassNotFoundException
import java.io.IOException
import org.springframework.beans.factory.BeanDefinitionStoreException
import kotlin.Throws
import org.apache.commons.lang3.ArrayUtils
import org.springframework.core.io.ResourceLoader
import org.springframework.core.type.classreading.MetadataReader
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.core.type.filter.TypeFilter
import org.springframework.util.ClassUtils
import org.springframework.util.StringUtils
import java.util.function.Consumer
import kotlin.jvm.JvmStatic

/**
 * 扫描所有包下的类
 * @author Toutatis_Gc
 */
class ClassScanner : ResourceLoaderAware {

    //保存过滤规则要排除的注解
    private val includeFilters: MutableList<TypeFilter> = LinkedList()
    private val excludeFilters: MutableList<TypeFilter> = LinkedList()
    private var resourcePatternResolver: ResourcePatternResolver = PathMatchingResourcePatternResolver()
    private var metadataReaderFactory: MetadataReaderFactory = CachingMetadataReaderFactory(resourcePatternResolver)

    override fun setResourceLoader(resourceLoader: ResourceLoader) {
        resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
        metadataReaderFactory = CachingMetadataReaderFactory(resourceLoader)
    }

    fun addIncludeFilter(includeFilter: TypeFilter) {
        includeFilters.add(includeFilter)
    }

    fun addExcludeFilter(excludeFilter: TypeFilter) {
        excludeFilters.add(0, excludeFilter)
    }

    fun resetFilters(useDefaultFilters: Boolean) {
        includeFilters.clear()
        excludeFilters.clear()
    }

    fun doScan(basePackage: String): Set<Class<*>> {
        val classes: MutableSet<Class<*>> = HashSet()
        try {
            val packageSearchPath = (ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage!!))
                    + "/**/*.class")
            val resources = resourcePatternResolver.getResources(packageSearchPath)
            for (i in resources.indices) {
                val resource = resources[i]
                if (resource.isReadable) {
                    val metadataReader = metadataReaderFactory.getMetadataReader(resource)
                    if (includeFilters.size == 0 && excludeFilters.size == 0 || matches(metadataReader)) {
                        try {
                            classes.add(Class.forName(metadataReader.classMetadata.className))
                        } catch (e: ClassNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } catch (ex: IOException) {
            throw BeanDefinitionStoreException("I/O failure during classpath scanning", ex)
        }
        return classes
    }

    @Throws(IOException::class)
    private fun matches(metadataReader: MetadataReader?): Boolean {
        for (tf in excludeFilters) {
            if (tf.match(metadataReader!!, metadataReaderFactory)) {
                return false
            }
        }
        for (tf in includeFilters) {
            if (tf.match(metadataReader!!, metadataReaderFactory)) {
                return true
            }
        }
        return false
    }



    companion object {

        /*TODO ISSUE扫描多个包，着急用，不解决，下面的scan需要扫描到具体的包名*/
        private fun scan(basePackages: Array<String>, vararg annotations: Class<out Annotation>?): Set<Class<*>> {
            val scanner = ClassScanner()
            if (ArrayUtils.isNotEmpty(annotations)) {
                for (annotation in annotations) {
                    scanner.addIncludeFilter(AnnotationTypeFilter(annotation!!))
                }
            }
            val classes: MutableSet<Class<*>> = HashSet()
            for (s in basePackages) classes.addAll(scanner.doScan(s))
            return classes
        }

        fun scan(basePackages: String, vararg annotations: Class<out Annotation>?): Set<Class<*>> {
            return ClassScanner.scan(StringUtils.tokenizeToStringArray(basePackages, ",; "),*annotations)
        }

    }
}