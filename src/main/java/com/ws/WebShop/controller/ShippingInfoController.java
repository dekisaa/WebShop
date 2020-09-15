package com.ws.WebShop.controller;

import com.ws.WebShop.model.Category;
import com.ws.WebShop.model.ShippingInfo;
import com.ws.WebShop.service.ShippingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping_info")
public class ShippingInfoController {

    @Autowired
    private ShippingInfoService shippingInfoService;

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody ShippingInfo shippingInfo){
        shippingInfoService.save(shippingInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id){
        shippingInfoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody ShippingInfo shippingInfo){
        shippingInfoService.save(shippingInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShippingInfo> findById(@RequestParam(value = "id") Long id){
        return new ResponseEntity<>(shippingInfoService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShippingInfo>> findAll(){
        return new ResponseEntity<>(shippingInfoService.findAll(), HttpStatus.OK);
    }
}
