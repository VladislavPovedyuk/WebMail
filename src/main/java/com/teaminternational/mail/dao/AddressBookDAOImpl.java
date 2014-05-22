package com.teaminternational.mail.dao;

import com.teaminternational.mail.domain.Address;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 31.10.13
 */

@Repository
public class AddressBookDAOImpl implements AddressBookDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void addNewAddress(Address address) {
        sessionFactory.getCurrentSession().save(address);
    }

    @Override
    public void deleteAddress(Address address) {
        if (address != null) {
            sessionFactory.getCurrentSession().delete(address);
        }
    }

    @Override
    public Address getAddress(Integer addressId) {
        return (Address) sessionFactory.getCurrentSession().get(Address.class, addressId);
    }

    @Override
    public Address checkAddressInAddressBook(String address, Integer userId) {
        return (Address)sessionFactory.getCurrentSession().createQuery("FROM Address WHERE email_address = ? AND " +
                "user_id = ?")
                .setParameter(0, address)
                .setParameter(1, userId)
                .uniqueResult();
    }

    @Override
    public List<Address> getAddresses(Integer userId) {
        return sessionFactory.getCurrentSession().createQuery("FROM Address WHERE user_id = ?")
                .setParameter(0, userId)
                .list();
    }

    @Override
    public List<Address> getAddressesContainingInput(Integer userId, String input) {
        return sessionFactory.getCurrentSession().createQuery("FROM Address WHERE user_id = ? AND email_address LIKE ?")
                .setParameter(0, userId)
                .setParameter(1, '%' + input + '%')
                .list();
    }
}
