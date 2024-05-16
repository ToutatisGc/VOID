package cn.toutatis.xvoid.spring.business.test;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    @TableId
//    @GeneratedValue(generator="UUID")
//    @GenericGenerator(name="UUID",strategy="UUID")
    private String id;

    @ApiModelProperty("姓名")
    private String name;

    private String age;


    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid")
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
