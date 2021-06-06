package org.zust.advertisement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zust.advertisement.entity.AdvertisementEntity;
import org.zust.advertisement.service.AdvertisementService;

import java.util.Map;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

  @PostMapping
    public AdvertisementEntity addAdvertisement(@RequestBody Map params)
  {
      try {
          advertisementService.addAdvertisement(params);
      }catch(){

  }

  }
}
