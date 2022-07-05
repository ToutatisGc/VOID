package cn.toutatis.spring.core.security.entity.mapper;

import cn.toutatis.data.common.security.SystemUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserLoginRepository extends JpaRepository<SystemUserLogin,String> {

    List<SystemUserLogin> findByAccount(String account);

}
