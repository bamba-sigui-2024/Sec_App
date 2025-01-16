package com.projetSpringBoot.AppSec.web.controllers;

import com.projetSpringBoot.AppSec.Business.servicesImpl.UserServiceImpl;
import com.projetSpringBoot.AppSec.security.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.security.Principal;


@Controller
public class AuthController {

//    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtService;
    private final UserServiceImpl userServiceImpl;

    public AuthController(JwtTokenUtil jwtService, AuthenticationManager authenticationManager, UserServiceImpl userServiceImpl) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/oauth2/callback")
    public String oauthCallback(Principal principal, HttpServletResponse response) {
        // Générer le token JWT
        String jwt = jwtService.generateToken(principal.getName());

        // Ajouter le token dans un cookie sécurisé
        Cookie jwtCookie = new Cookie("jwt", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Supprimer le cookie JWT
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);

        return "redirect:/login?logout";
    }


//    // Affiche le formulaire d'ajout d'utilisateur
//    @RequestMapping("/inscription")
//    public String showAddUserForm(Model model) {
//        model.addAttribute("userForm", new UserForm());
//        return "admin/pages/inscription";
//    }
//
//    // Gère l'envoi du formulaire
//    @RequestMapping(path = "/inscription", method = RequestMethod.POST)
//    public String addUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("userForm", userForm);
//            return "admin/pages/inscription";
//        }
//        // Ajouter l'utilisateur via le service
//        userServiceImpl.registerUser(new User(userForm.getName(), userForm.getEmail(), userForm.getPassword()));
//        return "redirect:/login";
//    }
}