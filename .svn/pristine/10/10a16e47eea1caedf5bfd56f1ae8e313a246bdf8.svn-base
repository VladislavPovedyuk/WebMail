package com.teaminternational.mail.web;

import com.teaminternational.mail.domain.User;
import com.teaminternational.mail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * User: Vladislav Povedyuk
 * Date: 22.10.13
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Loads "Admin" page with two forms. First used to add a new user (with validation). Second contains a list of
     * current users registered in application with the ability to edit their data like password/status(disabled or
     * enabled)/role
     *
     * @param model page model
     * @return tiles view of admin page
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String loadAdmin(ModelMap model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("userList", userService.userList());
        return "admin/page";
    }

    /**
     * Validates and registers new User
     *
     * @param newUser object of user to register
     * @param result  validation result
     * @param model   page model
     * @return tiles view of admin_page
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("newUser") User newUser, final BindingResult result,
                           ModelMap model, RedirectAttributes redirectAttributes ) {
        if (userService.getUser(newUser.getEmail()) != null) {
            result.addError(new ObjectError(result.getObjectName(), "* such email is already registered"));
        } else if (!newUser.getPassword().equals(newUser.getPasswordConfirm())) {
            result.addError(new ObjectError(result.getObjectName(), "* passwords not match"));
        }
        if (result.hasErrors()) {
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("userList", userService.userList());
            return "admin/page";
        } else {
            userService.addUser(newUser);
            redirectAttributes.addFlashAttribute("message", "New user with email " + newUser.getEmail() + " registered " +
                    "successful");
            return "redirect:/admin/page";
        }
    }

    /**
     * Loads "Edit user" page with data of selected user in admin page
     *
     * @param userId of user to edit
     * @param model  page model
     * @return tiles view of edit_user page
     */
    @RequestMapping("/edit_user/{userId}")
    public String editUser(@PathVariable("userId") Integer userId, ModelMap model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("userToEdit", userService.getUser(userId));
        return "admin/edit_user";
    }

    /**
     * Confirms user edit
     *
     * @param userToEdit user object which is edited
     * @param result     validation result
     * @param model      page model
     * @return tiles view of admin_page
     */
    @RequestMapping(value = "/edit_user/{userId}", method = RequestMethod.POST)
    public String submitEditUser(@Valid @ModelAttribute("userToEdit") User userToEdit, final BindingResult result,
                                 ModelMap model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/edit_user";
        } else {
            userService.updateUser(userToEdit);
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("userList", userService.userList());
            redirectAttributes.addFlashAttribute("message", "User with id = " + userToEdit.getUser_id() + " updated " +
                    "successfully");
        }
        return "redirect:/admin/page";
    }
}
