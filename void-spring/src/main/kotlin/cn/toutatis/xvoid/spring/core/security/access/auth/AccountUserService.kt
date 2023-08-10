package cn.toutatis.xvoid.spring.core.security.access.auth

import org.springframework.stereotype.Service

@Service
class AccountUserService {

    companion object{

        /**
         * Auth Times Key 账户登录重试次数
         */
        const val AUTH_TIMES_KEY = "VOID_RETRY_AUTH_TIMES_KEY"

    }

}