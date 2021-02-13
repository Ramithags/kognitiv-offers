package com.kognitive.offers.controller;

import com.kognitive.offers.exception.ContentNotFoundException;
import com.kognitive.offers.model.Offers;
import com.kognitive.offers.model.OffersResponse;
import com.kognitive.offers.service.OffersService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RequestMapping("/kognitive/collect")
@RestController
@AllArgsConstructor
public class OffersController {

    private static final Logger logger = LoggerFactory.getLogger(OffersController.class);

    private OffersService offersService;

    @GetMapping(value = "/offers", produces={"application/xml", "application/json"})
    @ResponseBody
    public ResponseEntity<List<Offers>> getOffers(@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "3") int size) {

        if(offersService.fetchOffers(name, page, size).isEmpty()) {
            throw new ContentNotFoundException();
        }
        return status(OK).body(offersService.fetchOffers(name, page, size));

    }

    @PostMapping("/offers")
    public ResponseEntity<OffersResponse> postOffers(@RequestBody Offers offers) {
                    return status(OK).body(offersService.postOffers(offers));
    }
}
