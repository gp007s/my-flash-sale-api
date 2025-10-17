package edu.umass.flashsale.controller;

import edu.umass.flashsale.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ConfigureSaleStocksController {

    private final RedisService redisService;

    @Autowired
    public ConfigureSaleStocksController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/admin/set-sale-stock")
    public String setStock(@RequestParam String name, @RequestParam int quantity) {
        System.out.println("#Setting Counts as : "+ quantity +" for each Item : "+name);
        redisService.increment("stock:" + name, quantity);
        return "Stock set for item " + name + " = " + quantity;
    }
}