package com.teaminternational.mail.service;

import com.teaminternational.mail.domain.Address;

import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 31.10.13
 */
public interface AddressBookService {

    void addNewAddress(Address address);

    void deleteAddress(Address address);

    Address getAddress(Integer addressId);

    Address checkAddressInAddressBook(String address, Integer userId);

    List<Address> getAddresses(Integer userId);

    List<String> searchInAddressBook(String email_address, Integer userId);
}
