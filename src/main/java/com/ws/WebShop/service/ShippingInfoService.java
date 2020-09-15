package com.ws.WebShop.service;

import com.ws.WebShop.model.ShippingInfo;
import com.ws.WebShop.repository.ShippingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingInfoService {

    @Autowired
    private ShippingInfoRepository shippingInfoRepository;

    public void save(ShippingInfo shippingInfo){
        shippingInfoRepository.save(shippingInfo);
    }

    public void delete(Long id){
        Optional<ShippingInfo> shippingInfo = shippingInfoRepository.findById(id);
        if(!shippingInfo.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping info with id " + id + " does not exist!");
        shippingInfoRepository.delete(shippingInfo.get());
    }

    public ShippingInfo findById(Long id){
        Optional<ShippingInfo> shippingInfo = shippingInfoRepository.findById(id);
        if(!shippingInfo.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping info with id "  + id + " does not exists");
        return shippingInfo.get();
    }

    public List<ShippingInfo> findAll(){
        return shippingInfoRepository.findAll();
    }
}
