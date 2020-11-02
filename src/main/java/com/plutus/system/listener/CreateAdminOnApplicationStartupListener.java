package com.plutus.system.listener;

import com.plutus.system.configuration.security.AdminConfiguration;
import com.plutus.system.model.entity.employee.Employee;
import com.plutus.system.model.entity.employee.EmployeeRole;
import com.plutus.system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CreateAdminOnApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AdminConfiguration configuration;
    private final EmployeeRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        Employee toFind = new Employee();
        toFind.setLogin(configuration.getLogin());
        Employee toPersist = repository.findOne(Example.of(toFind))
                .map(found -> {
                    found.setPassword(encoder.encode(configuration.getPassword()));
                    return found;
                })
                .orElseGet(() -> {
                    Employee admin = new Employee();
                    admin.setLogin(configuration.getLogin());
                    admin.setPassword(encoder.encode(configuration.getPassword()));
                    admin.setRole(EmployeeRole.ADMIN);
                    return admin;
                });
        repository.save(toPersist);
    }
}
