package com.kognitive.offers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kognitive.offers.model.Offers;
import com.kognitive.offers.model.OffersResponse;
import com.kognitive.offers.repository.OffersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class KognitiveOffersIntegrationTests {

    private static final String NAME = "Kognitive";
    private static final String LOCATION = "Bangalore";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OffersRepository offersRepository;

    @Test
    public void shouldCreateEntryInDatabaseWhenPostOffersWithValidData() throws Exception {
        //Arrange
        Offers offers = getOffers();

        //Act
        mockMvc.perform(MockMvcRequestBuilders.post("/kognitive/collect/offers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(offers))
                .accept("application/json"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    OffersResponse actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), OffersResponse.class);
                    assertThat(actualResponse.isSuccess()).isEqualTo(true);
                });


        //Assert
        Pageable paging = PageRequest.of(0, 9);
        // To validate whether data is created in database , so we were just retrieve and test it back :)
        List<Offers> offersEntity = offersRepository.findByNameContaining(NAME, paging);
        offersEntity.forEach(offersResult -> {
            assertThat(offersResult.getName()).isEqualTo(NAME);
            assertThat(offersResult.getLocation()).isEqualTo(LOCATION);
        });

    }

    private Offers getOffers() {
        Offers offers = new Offers();
        offers.setName(NAME);
        offers.setLocation(LOCATION);
        offers.setValidTo("Jan");
        offers.setValidFrom("Feb");
        return offers;
    }
}
