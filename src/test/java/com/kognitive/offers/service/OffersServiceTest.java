package com.kognitive.offers.service;

import com.kognitive.offers.configuration.RestTemplateClientConfig;
import com.kognitive.offers.model.Images;
import com.kognitive.offers.model.Offers;
import com.kognitive.offers.model.OffersResponse;
import com.kognitive.offers.repository.ImageRepository;
import com.kognitive.offers.repository.OffersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class OffersServiceTest {

    @Mock
    private OffersRepository offersRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private RestTemplateClientConfig templateClientConfig;

    @InjectMocks
    private OffersService offersService;


    @Test
    void shouldFetchOffersWhenFetchOffersDatabaseHasMockedValues() {
        //Arrange
        when(offersRepository.findByNameContaining(any(String.class), any(Pageable.class))).thenReturn(setDefaultOfferResponse());

        //Act
        List<Offers> offersList = offersService.fetchOffers("Koginitive", 1, 9);

        //Assert
        offersList.forEach(offers -> assertThat(offers.getName()).isEqualTo("Koginitive"));
    }


    @Test
    void shouldFetchOffersWithEmptyListWhenFetchOffersCalledAndDatBaseValueReturnedAsEmpty() {
        //Arrange
        when(offersRepository.findByNameContaining(any(String.class), any(Pageable.class))).thenReturn(new ArrayList<>());

        //Act
        List<Offers> offersList = offersService.fetchOffers("Koginitive", 1, 9);

        //Assert
        assertTrue(offersList.isEmpty());
    }


    @Test
    void shouldCreatOffersAndReturnResponseWhenOfferModelSetToDefaultValues() {

        Images[] images = new Images[10];

        when(templateClientConfig.getForObject("https://jsonplaceholder.typicode.com/photos", Images[].class)).thenReturn(images);
        ;
        when(offersRepository.save(any(Offers.class))).thenReturn(getOffers());
        when(offersRepository.existsById(any())).thenReturn(true);

        //Act
        OffersResponse offersResponse = offersService.postOffers(getOffers());

        //Assert
        assertTrue(offersResponse.isSuccess());
    }


    private List<Offers> setDefaultOfferResponse() {
        List<Offers> offersPage = new ArrayList<>();
        Offers offers = getOffers();

        Images images = new Images();
        offers.setImages(images);

        images.setUrl("https://google.com");
        offersPage.add(offers);

        return offersPage;
    }

    private Offers getOffers() {
        Offers offers = new Offers();
        offers.setName("Koginitive");
        offers.setLocation("London");
        offers.setValidFrom("Jan");
        offers.setValidTo("Feb");
        return offers;
    }
}