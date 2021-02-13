package com.kognitive.offers.service;

import com.kognitive.offers.configuration.RestTemplateClientConfig;
import com.kognitive.offers.model.Images;
import com.kognitive.offers.model.Offers;
import com.kognitive.offers.model.OffersResponse;
import com.kognitive.offers.repository.ImageRepository;
import com.kognitive.offers.repository.OffersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.kognitive.offers.constants.OffersConstants.PHOTOS_EXTERNAL_URI;

@Component
@Service
@AllArgsConstructor
public class OffersService {

    private OffersRepository offersRepository;
    private ImageRepository imageRepository;

    private RestTemplateClientConfig clientConfig;

    public List<Offers> fetchOffers(String name, int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        List<Offers> offersPage;

        if (StringUtils.hasText(name)) {
            offersPage = offersRepository.findByNameContaining(name, paging);
            if (offersPage.isEmpty()) {
                return new ArrayList<>();
            }
        } else {
            offersPage = offersRepository.findAll();
        }

        return offersPage;
    }

    @NonNull
    public OffersResponse postOffers(Offers offers) {
        extractedImageFromExternalResource(offers);

        offersRepository.save(offers);

        boolean isSuccessful = offersRepository.existsById(offers.getId());

        OffersResponse offersResponse = new OffersResponse();
        offersResponse.setSuccess(isSuccessful);

        return offersResponse;
    }

    private void extractedImageFromExternalResource(Offers offers) {
        Images[] images = clientConfig.getForObject(PHOTOS_EXTERNAL_URI, Images[].class);

        Images image = Arrays
                .asList(Objects.requireNonNull(images))
                .get(new Random().nextInt(images.length));

        imageRepository.save(image);

        offers.setImages(image);
    }
}
