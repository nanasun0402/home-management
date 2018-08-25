package com.caring.dao.service;

import com.caring.dao.model.Address;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.AddressFilter;
import com.caring.dao.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends BaseService {
    @Autowired
    private AddressRepository addressRepository;

    public Page<Address> findAddresses(PageParam<AddressFilter> pageParam) {
        StringBuilder SQL = new StringBuilder("SELECT a FROM Address a");
        StringBuilder SQLcount = new StringBuilder("SELECT COUNT(1) FROM Address a");
        StringBuilder OrderBy = new StringBuilder("ORDER BY a.created DESC");
        return executePageQuery(pageParam, SQL, SQLcount, OrderBy);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.delete(id);
    }

    public Address findAddressById(Long id) {
        return addressRepository.findOne(id);
    }
}
