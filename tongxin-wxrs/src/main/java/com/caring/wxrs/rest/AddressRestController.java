package com.caring.wxrs.rest;

import com.caring.dao.model.Address;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageParam;
import com.caring.dao.model.query.filter.AddressFilter;
import com.caring.dao.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
public class AddressRestController {
    @Autowired
    private AddressService addressService;

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Address> findAddresses(@RequestBody(required = false) PageParam<AddressFilter> pageParam) {
        return addressService.findAddresses(pageParam);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address saveAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Address findAddressById(@PathVariable Long id) {
        return addressService.findAddressById(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }


}
