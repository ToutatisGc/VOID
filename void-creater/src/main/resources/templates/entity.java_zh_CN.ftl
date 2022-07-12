package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>
<#if usePersistence>
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
</#if>
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.text.SimpleDateFormat;

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
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if usePersistence>
@Entity
@Table(name="${realTableName}")
@org.hibernate.annotations.Table(appliesTo = "${realTableName}", comment = "${table.comment!}")
</#if>
<#if swagger2>
    @ApiModel(
    value="${realName}对象",
    description="${table.comment!}"<#if superEntityClass??>,
    parent = ${superEntityClass}.class</#if>)
</#if>
@JsonIgnoreProperties({"reservedString","reservedInt"})
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
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    <#if field.propertyName == 'uuid' || field.propertyName == 'id'>
        <#if usePersistence>
    @Id
            <#if field.propertyName == 'uuid'>
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
            <#else>
    @Column(name="id",columnDefinition = "INT COMMENT '主键ID'")
            </#if>
        </#if>
        <#if field.propertyName == 'uuid'>
    @TableId(value = "uuid",type = IdType.ASSIGN_UUID)
        <#else>
    @TableId(value = "id",type = IdType.AUTO)
        </#if>
    </#if>
        <#-- 普通字段 -->
    <#if field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
    @TableField(value = "${field.annotationColumnName}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.annotationColumnName}")
    </#if>
    <#if usePersistence>
    @Column(name="${field.propertyName}",columnDefinition = "COMMENT '${field.comment}'")
    </#if>
    <#if swagger2>
    @ApiModelProperty(value="${field.comment}")
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
    protected Serializable pkVal() {
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
