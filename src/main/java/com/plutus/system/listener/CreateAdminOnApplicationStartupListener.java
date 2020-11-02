package com.plutus.system.listener;

import com.plutus.system.configuration.security.AdminConfiguration;
import com.plutus.system.model.entity.employee.Employee;
import com.plutus.system.model.entity.employee.EmployeeRole;
import com.plutus.system.service.EmployeeService;
import com.plutus.system.utils.RepositorySearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAdminOnApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AdminConfiguration configuration;
    private final EmployeeService service;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (!service.exists(RepositorySearchUtils.employeeForSearchByLogin(configuration.getLogin()))) {
            Employee admin = new Employee();
            admin.setLogin(configuration.getLogin());
            admin.setPassword(configuration.getPassword());
            admin.setRole(EmployeeRole.ADMIN);
            service.create(admin);
        }
    }
}
