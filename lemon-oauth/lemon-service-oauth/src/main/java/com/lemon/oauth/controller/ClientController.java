package com.lemon.oauth.controller;

import com.lemon.oauth.config.CmsProperties;
import com.lemon.oauth.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ClientController
 **/
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/key")
    public ResponseEntity<String> getSecretKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret){
        return ResponseEntity.ok(clientService.getSecretKey(clientId, secret));
    }


    @RequestMapping("/jwt/key")
    public ResponseEntity<CmsProperties> getSecretJwtKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret){
        return ResponseEntity.ok(clientService.getSecretJwtKey(clientId, secret));
    }


}