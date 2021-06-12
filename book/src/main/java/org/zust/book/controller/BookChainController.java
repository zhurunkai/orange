package org.zust.book.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.book.repository.BookShelfRepository;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookChainService;
import org.zust.interfaceapi.service.CommonUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;

/**
 * @author: Linxy
 * @Date: 2021/6/10 19:24
 * @function:
 **/
@RestController
@RequestMapping("/book/shelf/chain")
public class BookChainController {

    @Reference(check = false)
    private CommonUserService commonUserService;
    @Autowired
    private BookChainService chainService;

    @GetMapping("/{sid}/chain/{cid}")
    public ResponseEntity<?> getBookChainById(@RequestHeader("Authorization") String token,
                                              @PathVariable String sid,String cid) {

        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        HashMap<String, Object> map = new HashMap<>();
        BookUserDto buser = (BookUserDto) tokenRes.getData();
        map.put("owner",buser.getId());
        map.put("sid",sid);
        map.put("cid",cid);

        ResType res = chainService.getChainBySidAndCid(map);
        if (res.getStatus() == 200) {
            return ResponseEntity.ok(res.getData());
        }else {
            return ResponseEntity.status(res.getStatus()).body(res.getCode());
        }

    }



}
