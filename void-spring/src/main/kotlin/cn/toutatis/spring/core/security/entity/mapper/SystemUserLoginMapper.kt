package cn.toutatis.spring.core.security.entity.mapper

import cn.toutatis.data.common.security.SystemUserLogin
import cn.toutatis.spring.core.security.entity.AccountCheckUserDetails
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface SystemUserLoginMapper : BaseMapper<SystemUserLogin> {}