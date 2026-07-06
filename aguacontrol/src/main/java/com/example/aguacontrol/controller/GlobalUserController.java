package com.example.aguacontrol.controller;

import com.example.aguacontrol.model.Rol;
import com.example.aguacontrol.security.ACUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserController {
    @ModelAttribute
    public void addUserDataToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof ACUserDetails user) {
            model.addAttribute("username", user.getUsername());

            //HIGHEST ROLE
            Long highestRoleId = Long.MIN_VALUE;
            String highestRoleName = "";

            for (var role : user.getRoles()) {
                if (role.getId() > highestRoleId) {
                    highestRoleId = role.getId();
                    highestRoleName = role.getNombre();
                }
            }
            model.addAttribute("userrole", highestRoleName);
        }
    }
}
