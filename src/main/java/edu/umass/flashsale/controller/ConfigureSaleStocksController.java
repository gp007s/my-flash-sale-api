package edu.umass.flashsale.controller;

import edu.umass.flashsale.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class ConfigureSaleStocksController {

    @Autowired
    RedisService redisService;

    @PostMapping("/set-sale-stock")
    public String configureMaximumCountStock(@RequestParam String itemNameInOffer, @RequestParam int maxItemQuantityOnSale) {
        System.out.println("#Setting item counts as : "+ maxItemQuantityOnSale +" for the item in Offer : "+itemNameInOffer);
        redisService.loadTheKeyValueFirstTime("stock:" + itemNameInOffer, maxItemQuantityOnSale);
        return "Offer stock configured successfully for an item: " + itemNameInOffer + " = " + maxItemQuantityOnSale;
    }

    @GetMapping("/checkItemCount")
    public long getAvailableCount(@RequestParam String itemNameInOffer) {
        return redisService.getAvailableItemCount(itemNameInOffer);
    }
}