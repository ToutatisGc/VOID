package cn.toutatis.xvoid.orm.base.authentication.persistence;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserLoginRepository extends JpaRepository<SystemUserLogin,String> {

    List<SystemUserLogin> findByAccount(String account);

}
