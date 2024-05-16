package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.v3.oas.annotations.media.Schema;
</#if>
<#if entityLombokModel>
    <#if usePersistence>
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
        <#else>
import lombok.Data;
import lombok.EqualsAndHashCode;
    </#if>
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>
<#if usePersistence>
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import java.util.Objects;
</#if>
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.text.SimpleDateFormat;

import static ${package.Entity}.${realName}.TABLE;

/**
 * <p>
    <#if table.comment == ''>
 * 请填写${entity}表注释
        <#else >
 * ${table.comment!}
    </#if>
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
    <#if usePersistence>
@Getter @Setter <#if superEntityClass??>@ToString(callSuper = true)<#else>@ToString(callSuper = false)</#if>
        <#else>
@Data
        <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
        <#else>
@EqualsAndHashCode(callSuper = false)
        </#if>
    </#if>
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
@JsonIgnoreProperties({"reservedString","reservedInt","createTimeMs","lastUpdateTimeMs"})
<#if swagger2>
@Schema(name="${realName}对象",description="${table.comment!}"<#if superEntityClass??>,parent = ${superEntityClass}.class</#if>)
</#if>
<#if table.convert>
@TableName(TABLE)
</#if>
<#if usePersistence>
@Entity @Table(name = TABLE ) @org.hibernate.annotations.Table(appliesTo = TABLE, comment = "${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${realName} extends ${superEntityClass}<#if activeRecord><${realName}></#if> {
<#elseif activeRecord>
public class ${realName} extends Model<${realName}> {
<#else>
public class ${realName} implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
    public static final String TABLE = "vb_forum_article";
    // FIXME 请修改业务类型
    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
    @Schema(name = "${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    <#--判断主键(只能使用uuid和id两种名称)-->
    <#if field.propertyName == 'uuid' || field.propertyName == 'id'>
        <#if field.propertyType == 'String'>
    <#if usePersistence>@Id </#if>@TableId(value = "uuid",type = IdType.ASSIGN_UUID)
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        </#if>
        <#if field.propertyType == 'Integer'>
    <#if usePersistence>@Id </#if>@TableId(value = "id",type = IdType.AUTO)
        </#if>
    </#if>
    <#-- 普通字段 -->
    <#if field.propertyName != 'uuid' && field.propertyName != 'id'>
    @TableField("${field.annotationColumnName}")
        <#if usePersistence>
            <#if field.propertyType == 'Integer'>
    @Column(name="${field.annotationColumnName}",columnDefinition = "INT COMMENT '${field.comment}'")
            <#elseif field.propertyType == 'String'>
    @Column(name="${field.annotationColumnName}",columnDefinition = "VARCHAR(255) COMMENT '${field.comment}'")
            <#elseif field.propertyType == 'LocalDateTime'>
    @Column(name="${field.annotationColumnName}",columnDefinition = "DATETIME COMMENT '${field.comment}'")
            </#if>
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>

    <#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
    <#if chainModel>
        return this;
    </#if>
    }

    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if field.propertyType == 'Date'>
    public String ${getprefix}${field.capitalName}Str() {
        return ${field.propertyName} == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(${field.propertyName});
    }

    public long ${getprefix}${field.capitalName}Long(){
        return ${field.propertyName} == null ? 0L : ${field.propertyName}.getTime();
    }
    </#if>
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#if activeRecord>
    @Override
    public Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }

</#if>
<#if !entityLombokModel>
    @Override
    public String toString() {
        return "\r\n${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>

<#if usePersistence>
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) { return false; }
        ${realName} that = (${realName}) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
</#if>
}
