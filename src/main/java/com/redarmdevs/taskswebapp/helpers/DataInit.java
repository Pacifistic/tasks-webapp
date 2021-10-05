package com.redarmdevs.taskswebapp.helpers;

import com.redarmdevs.taskswebapp.models.Role;
import com.redarmdevs.taskswebapp.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInit {

    public final static String[] roles = {"ROLE_USER", "ROLE_ADMIN"};

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataInit.class);

    @EventListener
    public void checkRoles(ApplicationReadyEvent e){
        // get current roles in role repo as strings
        List<String> repoRoles = roleRepository.findAll()
                .stream().map(role -> role.getName().toString())
                .collect(Collectors.toList());

        // check if defined roles are already present
        if(repoRoles.containsAll(Arrays.stream(roles).collect(Collectors.toList()))) {
            logger.info("predefined roles found.");
            return;
        }

        // add missing roles
        logger.info("predefined roles not found, adding default roles.");
        for(String role : roles){
            if(!repoRoles.contains(role))
                roleRepository.save(new Role(role));
        }
    }
}
