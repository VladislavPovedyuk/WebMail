package com.teaminternational.mail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Vladislav Povedyuk
 * Date: 21.11.13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = {"classpath:/Test-context.xml"})
public class AddressBookServiceImplTest {

    @Autowired
    private AddressBookService addressBookService;

    @Test
    public void testSearchInAddressBookCount() {
        int user_id = 2;
        String input = "vlad";
        List<String> addressBook = addressBookService.searchInAddressBook(input, user_id);
        assertEquals(addressBook.size(), 3);
    }

    @Test
    public void testSearchInAddressBookContains(){
        int user_id = 2;
        String input = "vlad";
        List<String> addressList = addressBookService.searchInAddressBook(input, user_id);
        for (String address : addressList){
            assertTrue(address.contains(input));
        }
    }

    @Test
    public void testSearchInAddressBookContainsOne(){
        int user_id = 2;
        String input = "ad.po";
        List<String> addressList = addressBookService.searchInAddressBook(input, user_id);
        for (String address : addressList){
            assertTrue(address.contains(input));
        }
        assertEquals(addressList.size(), 1);
    }

    @Test
    public void testSearchInAddressBookNotContains(){
        int user_id = 0;
        String input = "whatever";
        List<String> addressList = addressBookService.searchInAddressBook(input, user_id);
        assertEquals(addressList.size(), 0);
    }
}
