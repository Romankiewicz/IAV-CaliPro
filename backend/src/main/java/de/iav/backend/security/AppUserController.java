package de.iav.backend.security;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/me")
    public String getMe(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return "anonymousUser";
    }

//    @PostMapping("/login")
//    public String login(){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        UserDetails user = appUserService.loadUserByUsername(username);
//
//        return user.getUsername() + user.getAuthorities();
//    }
    @PostMapping("/login")
    public String login() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String role = authorities.isEmpty() ? "ROLE_USER" : authorities.iterator().next().getAuthority();
        return username + "[" + role + "]";
    }

    @PostMapping("/register/metrologist")
    public AppUserResponse registerNewMetrologist(@RequestBody NewAppUser newAppUser){
        return appUserService.registerNewMetrologist(newAppUser);
    }

    @PostMapping("/register/operator")
    public AppUserResponse registerNewOperator(@RequestBody NewAppUser newAppUser){
        return appUserService.registerNewOperator(newAppUser);
    }
    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
        return "anonymousUser";
    }
}
