package cn.toutatis.xvoid.spring.business.test.persistence;

import cn.toutatis.xvoid.spring.business.test.Person;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {

}