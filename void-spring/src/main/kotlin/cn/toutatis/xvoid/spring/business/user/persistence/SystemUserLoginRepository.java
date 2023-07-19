package cn.toutatis.xvoid.spring.business.user.persistence;

import cn.toutatis.xvoid.orm.base.data.common.security.SystemUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserLoginRepository extends JpaRepository<SystemUserLogin,String> {

    List<SystemUserLogin> findByAccount(String account);

}
