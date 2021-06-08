package org.zust.advertisement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.zust.advertisement.entity.AdvertisementEntity;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

import static java.lang.Integer.valueOf;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    //新增一条广告
    @PostMapping
    public ResponseEntity<?> addAdvertisement(@RequestBody Map params)
  {
      ResType res = advertisementService.addAdvertisement(params);
      if(res.getStatus()==200) {
          return new ResponseEntity<AdvertisementDto>((AdvertisementDto)res.getData(), HttpStatus.valueOf(res.getStatus()));
      }
      return new ResponseEntity<Integer>(res.getCode(), HttpStatus.valueOf(res.getStatus()));
  }

  //根据广告id获取广告信息
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisement(@PathVariable String id)
    {
        ResType res = advertisementService.getAdvertisement(Integer.valueOf(id));
        if (res.getStatus()==200){
            return new ResponseEntity<AdvertisementDto>((AdvertisementDto)res.getData(),HttpStatus.valueOf(res.getStatus()));
        }
        return new ResponseEntity<Integer>(res.getCode(),HttpStatus.valueOf(res.getStatus()));
    }

    //更新广告状态
    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> changeAdvertisementStatus(@PathVariable String id,@RequestBody Map param )
    {
        ResType res = advertisementService.changeAdvertisementStatus(valueOf(id),(String) param.get("status"));
        if (res.getStatus()==200){
            return new ResponseEntity<AdvertisementDto>((AdvertisementDto)res.getData(),HttpStatus.valueOf(res.getStatus()));
        }
        return new ResponseEntity<Integer>(res.getCode(),HttpStatus.valueOf(res.getStatus()));
    }

    //根据广告id获得该广告的投放信息
    @GetMapping("/{id}/throw")
    public ResponseEntity<?> getAdvertisementThrow(@PathVariable String id)
    {
        ResType res = advertisementService.getAdvertisementThrow(valueOf(id));
        if (res.getStatus()==200){
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

  }

