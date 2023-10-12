package de.iav.backend.security;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping("/login")
    public String login(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
