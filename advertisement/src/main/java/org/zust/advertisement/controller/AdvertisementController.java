package org.zust.advertisement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.zust.advertisement.entity.AdvertisementEntity;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    public ResponseEntity<?> addAdvertisement(@RequestBody Map params)
  {
      ResType res = advertisementService.addAdvertisement(params);
      if(res.getStatus()==200) {
          return new ResponseEntity<AdvertisementDto>((AdvertisementDto)res.getData(), HttpStatus.valueOf(res.getStatus()));
      }
      return new ResponseEntity<String>((String)res.getMsg(), HttpStatus.valueOf(res.getStatus()));
  }

  }

