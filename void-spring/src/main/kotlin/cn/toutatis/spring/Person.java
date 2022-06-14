package cn.toutatis.spring;

import cn.toutatis.common.annotations.database.AutoCreateDataObject;
import cn.toutatis.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 */
@Entity
@ApiModel("测试类")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@Table(name="vb_person")
@org.hibernate.annotations.Table(appliesTo = "vb_person", comment = "测试类")
public class Person extends EntityBasicAttribute<Person> {

    @Id @TableId
    private String id;

    @ApiModelProperty("姓名")
    private String name;

    private String age;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
