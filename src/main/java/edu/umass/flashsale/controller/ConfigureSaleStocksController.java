package edu.umass.flashsale.controller;

import edu.umass.flashsale.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class ConfigureSaleStocksController {

    private final RedisService redisService;

    @Autowired
    public ConfigureSaleStocksController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/set-sale-stock")
    public String configureMaximumCountStock(@RequestParam String itemNameInOffer, @RequestParam int maxItemQuantityOnSale) {
        System.out.println("#Setting item counts as : "+ maxItemQuantityOnSale +" for the item in Offer : "+itemNameInOffer);
        redisService.increment("stock:" + itemNameInOffer, maxItemQuantityOnSale);
        return "Offer stock configured successfully for an item: " + itemNameInOffer + " = " + maxItemQuantityOnSale;
    }

    @GetMapping("/checkItemCount")
    public long getAvailableCount(@RequestParam String itemNameInOffer) {
       // System.out.println("#Setting item counts as : "+ maxItemQuantityOnSale +" for the item in Offer : "+itemNameInOffer);
        //redisService.increment("stock:" + itemNameInOffer, maxItemQuantityOnSale);
        return 10;
    }
}