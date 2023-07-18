package ${package.Mapper};

import ${package.Entity}.${entity};
import cn.toutatis.xvoid.spring.support.jpa.VoidJpaRepo;

import java.util.List;

/**
 * <p>
 * ${table.comment!} JPA Repository 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
<#list table.fields as field>
    <#if field.propertyName == 'id'>
public interface ${table.repositoryName} extends VoidJpaRepo<${entity},Integer> {
    <#elseIf field.propertyName == 'uuid'>
public interface ${table.repositoryName} extends VoidJpaRepo<${entity},String> {
    </#if>
</#list>

}
