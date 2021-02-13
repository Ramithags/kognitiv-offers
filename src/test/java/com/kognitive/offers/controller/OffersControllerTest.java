package com.kognitive.offers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kognitive.offers.model.Images;
import com.kognitive.offers.model.Offers;
import com.kognitive.offers.model.OffersResponse;
import com.kognitive.offers.service.OffersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = OffersController.class)
@ExtendWith(MockitoExtension.class)
class OffersControllerTest {

    public static final String KOGNITIVE_COLLECT_OFFERS = "/kognitive/collect/offers";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffersService offersService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Offers> offersPage;

    @Test
    public void shouldFetchOffersWhenGetOffersCalledWithDefaultPagination() throws Exception {
        //Arrange
        setDefaultOfferResponse();
        given(offersService.fetchOffers(null, 0, 3)).willReturn(offersPage);

        //Act
        mockMvc.perform(MockMvcRequestBuilders.get(KOGNITIVE_COLLECT_OFFERS)
                .accept("application/json"))
                //Assert
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(offersPage.size())));
    }

    @Test
    public void shouldReturnNotFoundWhenGetOffersIsEmptyOrNullWithDefaultPagination() throws Exception {
        //Act
        mockMvc.perform(MockMvcRequestBuilders.get(KOGNITIVE_COLLECT_OFFERS)
                .accept("application/json"))
                //Assert
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldPostOffersAndReturnTrueWhenPostOffersCalledAndResponseValueSetAsTrue() throws Exception {
        //Arrange
        OffersResponse offersResponse = getOffersResponse();
        given(offersService.postOffers(any(Offers.class))).willReturn(offersResponse);

        //Act
        mockMvc.perform(MockMvcRequestBuilders.post(KOGNITIVE_COLLECT_OFFERS)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(getOffers()))
                .accept("application/json"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    //Assert
                    OffersResponse actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), OffersResponse.class);
                    assertThat(actualResponse.isSuccess()).isEqualTo(true);
                });

    }

    private void setDefaultOfferResponse() {
        offersPage = new ArrayList<>();
        Offers offers = getOffers();

        Images images = new Images();
        offers.setImages(images);

        images.setUrl("https://google.com");
        offersPage.add(offers);
    }

    private Offers getOffers() {
        Offers offers = new Offers();
        offers.setName("Koginitive");
        offers.setLocation("London");
        offers.setValidFrom("Jan");
        offers.setValidTo("Feb");
        return offers;
    }

    private OffersResponse getOffersResponse() {
        OffersResponse offersResponse = new OffersResponse();
        offersResponse.setSuccess(true);
        return offersResponse;
    }
}