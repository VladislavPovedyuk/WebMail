package com.teaminternational.mail.web;

import com.teaminternational.mail.domain.Address;
import com.teaminternational.mail.service.AddressBookService;
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
 * Date: 31.10.13
 */

@Controller
@RequestMapping(value = "/address")
public class AddressBookController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    /**
     * Loads "Address book" page which contains form to add a new address to address book and a form with address
     * book of current user with the ability to remove and choose a one or a few addresses like receivers in "New
     * message" page
     *
     * @param model page model
     * @return tiles view of address_book page
     */
    @RequestMapping(value = "/address_book", method = RequestMethod.GET)
    public String loadAddressBook(ModelMap model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("newAddress", new Address());
        model.addAttribute("currentAddresses", addressBookService.getAddresses(userService.getUser
                (SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id()));
        return "address/address_book";

    }

    /**
     * Removes an address from address book
     *
     * @param address_id id of address to remove
     * @param model      page model
     * @return tiles view of address_book
     */
    @RequestMapping("/remove_address/{address_id}")
    public String removeAddress(@PathVariable("address_id") Integer address_id, ModelMap model,
                                RedirectAttributes redirectAttributes) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        Address addressToDelete;
        try {
            addressToDelete = addressBookService.getAddress(address_id);
            if (addressToDelete == null){
                redirectAttributes.addFlashAttribute("message", "Address is removed successfully");
                return "redirect:/address/address_book";
            }
            addressBookService.deleteAddress(addressToDelete);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        redirectAttributes.addFlashAttribute("message", "Address " + addressToDelete.getEmail_address() + " removed " +
                "successfully");
        return "redirect:/address/address_book";
    }

    /**
     * Validates new address and if it is new for user address book and in mail format adds it to the current user
     * address_book
     *
     * @param newAddress address object to add
     * @param result     validation result
     * @param model      page model
     * @return tiles view of address_book
     */
    @RequestMapping(value = "/address_book", method = RequestMethod.POST)
    public String addAddress(@Valid @ModelAttribute("newAddress") Address newAddress, final BindingResult result,
                             ModelMap model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors() && addressBookService.checkAddressInAddressBook(newAddress.getEmail_address(),
                userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id())
                != null) {
            result.addError(new ObjectError(result.getObjectName(), "* there is such address in your address book"));
        }

        if (result.hasErrors()) {
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("currentAddresses", addressBookService.getAddresses(userService.getUser
                    (SecurityContextHolder.getContext().getAuthentication().getName()).getUser_id()));
            return "address/address_book";
        } else {
            newAddress.setUser(userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()));
            addressBookService.addNewAddress(newAddress);
            redirectAttributes.addFlashAttribute("message", "Address " + newAddress.getEmail_address() + " added " +
                    "successfully");
        }
        return "redirect:/address/address_book";
    }
}
