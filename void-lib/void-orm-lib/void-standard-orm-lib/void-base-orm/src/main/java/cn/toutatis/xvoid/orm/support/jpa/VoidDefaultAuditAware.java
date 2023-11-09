package cn.toutatis.xvoid.orm.support.jpa;

import cn.toutatis.xvoid.common.standard.StandardComponentPool;
import cn.toutatis.xvoid.common.standard.StandardFields;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Data JPA 审计
 * 需要在Application加入
 * 注解 @EnableJpaAuditing(auditorAwareRef = "voidDefaultAuditAware")
 * @author Toutatis_Gc
 */
@Component(StandardComponentPool.VOID_JPA_CREATOR_AUDIT_AWARE)
public class VoidDefaultAuditAware implements AuditorAware<String> {

    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(StandardFields.VOID_BUSINESS_DEFAULT_CREATOR);
    }
}
