package com.example.sosinventory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {
    @PostMapping("jsonrpc/datacollection")
    public void test(@RequestBody String test){
        log.info("test: {}",test);
    }
}
