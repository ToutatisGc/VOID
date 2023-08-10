package cn.toutatis.xvoid.orm.support.jpa;

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
@Component("voidDefaultAuditAware")
public class VoidDefaultAuditAware implements AuditorAware<String> {

    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        // FIXME 使用standardFields
        return Optional.of("SYSTEM");
    }
}
