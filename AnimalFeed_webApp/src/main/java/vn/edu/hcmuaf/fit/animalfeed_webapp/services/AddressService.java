package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.AddressDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Address;

import java.util.List;

public class AddressService {
    static AddressDao addressDao = new AddressDao();

    public List<Address> getAddressesByUserId(int id) {
        return addressDao.getAddressesByUserId(id);
    }
}
