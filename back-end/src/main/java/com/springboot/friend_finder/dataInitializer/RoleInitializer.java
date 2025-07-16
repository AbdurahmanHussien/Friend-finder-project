package com.springboot.friend_finder.dataInitializer;


import com.springboot.friend_finder.constant.RoleType;
import com.springboot.friend_finder.entity.auth.Role;
import com.springboot.friend_finder.repository.auth.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (!roleRepository.existsRoleByRoleType(RoleType.ADMIN)) {
            roleRepository.save(new Role(RoleType.ADMIN));
        }

        if (!roleRepository.existsRoleByRoleType(RoleType.USER)) {
            roleRepository.save(new Role(RoleType.USER));
        }
    }
}
