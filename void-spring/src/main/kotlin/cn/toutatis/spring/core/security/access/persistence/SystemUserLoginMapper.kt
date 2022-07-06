package cn.toutatis.spring.core.security.access.persistence

import cn.toutatis.data.common.security.SystemUserLogin
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface SystemUserLoginMapper : BaseMapper<SystemUserLogin> {}