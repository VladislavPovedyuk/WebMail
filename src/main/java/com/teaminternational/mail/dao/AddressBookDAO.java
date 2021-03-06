package com.teaminternational.mail.dao;

import com.teaminternational.mail.domain.Address;

import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 31.10.13
 */
public interface AddressBookDAO {

    void addNewAddress(Address address);

    void deleteAddress(Address address);

    Address getAddress(Integer addressId);

    Address checkAddressInAddressBook(String address, Integer userId);

    List<Address> getAddresses(Integer userId);

    List<Address> getAddressesContainingInput(Integer userId, String input);
}
