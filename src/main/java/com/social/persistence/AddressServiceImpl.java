package com.social.persistence;

import com.social.persistence.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao;

    @Autowired
    AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Address address) {
        addressDao.save(address);
    }
}
