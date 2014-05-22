package com.teaminternational.mail.service;

import com.teaminternational.mail.dao.AddressBookDAO;
import com.teaminternational.mail.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 31.10.13
 */

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookDAO addressBookDAO;

    /**
     * Adds a new address
     *
     * @param address address to add
     */
    @Transactional
    public void addNewAddress(Address address) {
        addressBookDAO.addNewAddress(address);
    }

    /**
     * Remove address by user request
     *
     * @param address address to delete
     */
    @Transactional
    public void deleteAddress(Address address) {
        addressBookDAO.deleteAddress(address);
    }

    /**
     * Gets the address by Id
     *
     * @param addressId id of needed address
     * @return address object
     */
    @Transactional
    public Address getAddress(Integer addressId) {
        return addressBookDAO.getAddress(addressId);
    }

    /**
     * Checks if address exists in current user address book
     *
     * @param address address to check
     * @param userId  current user id
     * @return null if there is no such email and an object if there is one
     */
    @Transactional
    public Address checkAddressInAddressBook(String address, Integer userId) {
        return addressBookDAO.checkAddressInAddressBook(address, userId);
    }

    /**
     * Gets current user address book
     *
     * @param userId current user id
     * @return user's address book
     */
    @Transactional
    public List<Address> getAddresses(Integer userId) {
        return addressBookDAO.getAddresses(userId);
    }

    /**
     * Used by auto complete on "New message" page. Gets address book of current user and adds to result list those
     * addresses which contains user input
     *
     * @param email_address user input
     * @param userId        current user id
     * @return list of addresses which contain user input
     */
    @Transactional
    public List<String> searchInAddressBook(String email_address, Integer userId) {
        List<String> result = new ArrayList<String>();
        List<Address> addressBook = addressBookDAO.getAddressesContainingInput(userId, email_address);
        for (Address address : addressBook) {
            result.add(address.getEmail_address());
        }
        return result;
    }
}
