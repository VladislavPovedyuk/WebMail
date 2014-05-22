package com.teaminternational.mail.web;

import com.teaminternational.mail.domain.User;
import com.teaminternational.mail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * Loads login page
     *
     * @param model page model
     * @return tiles view of login page
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadLoginForm(ModelMap model) {
        User loggedInUser = new User();
        model.addAttribute("new_user", loggedInUser);
        return "login";
    }

    /**
     * Loads registration page
     *
     * @param model page model
     * @return tiles view of register page
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model) {
        User userForRegister = new User();
        model.put("userForRegister", userForRegister);
        return "register";
    }

    /**
     * Validate and then registers a new user, after successful register redirects it to success page with
     * information after 5 sec redirects to login page (or user can press a link by himself and redirect by himself)
     *
     * @param userForRegister object of user to register
     * @param result          validation result
     * @return tiles view of register if not valid or success if registration is successful
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("userForRegister") User userForRegister, final BindingResult result) {
        if (userService.getUser(userForRegister.getEmail()) != null) {
            result.addError(new ObjectError(result.getObjectName(), "* such email is already registered"));
        } else if (!userForRegister.getPassword().equals(userForRegister.getPasswordConfirm())) {
            result.addError(new ObjectError(result.getObjectName(), "* passwords not match"));
        }
        if (result.hasErrors()) {
            return "register";
        } else {
            userService.addUser(userForRegister);
            return "register-successful";
        }
    }
}