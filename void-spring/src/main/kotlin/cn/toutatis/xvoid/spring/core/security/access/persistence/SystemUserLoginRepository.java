package cn.toutatis.xvoid.spring.core.security.access.persistence;

import cn.toutatis.data.common.security.SystemUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserLoginRepository extends JpaRepository<SystemUserLogin,String> {

    List<SystemUserLogin> findByAccount(String account);

}
