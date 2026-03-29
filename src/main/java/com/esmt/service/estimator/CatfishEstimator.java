package com.esmt.service.estimator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esmt.dto.QuoteFishItem;
import com.esmt.dto.QuotePackageOption;
import com.esmt.enums.QuoteType;
import com.esmt.enums.UnitType;
import com.esmt.exception.PriceNotFoundException;
import com.esmt.model.PackageFishComposition;
import com.esmt.model.QuotePackageMaster;
import com.esmt.repository.DistancePricingRepository;
import com.esmt.repository.FishPriceRepository;
import com.esmt.repository.PackageFishCompositionRepository;
import com.esmt.repository.QuotePackageRepository;
import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;
import com.esmt.service.AddonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatfishEstimator implements QuoteEstimator {

    private static final BigDecimal MIN_ORDER = BigDecimal.valueOf(750);

    private final QuotePackageRepository packageRepo;
    private final PackageFishCompositionRepository compositionRepo;
    private final FishPriceRepository priceRepo;
    private final DistancePricingRepository distanceRepo;
    private final AddonService addonService;

    @Override
    public QuoteType supportedType() {
        return QuoteType.FISH_CATFISH;
    }

    @Override
    public QuoteEstimateResponse estimate(QuoteRequestDto request) {

        try {
            BigDecimal acres = Optional.ofNullable(request.getPondSurfaceAcres()).orElse(BigDecimal.ZERO);

            List<QuotePackageMaster> packages =
                    packageRepo.findByQuoteTypeAndIsActiveTrue(request.getQuoteType().name());

            List<QuotePackageOption> result = new ArrayList<>();

            for (QuotePackageMaster pkg : packages) {

                List<PackageFishComposition> fishes =
                        compositionRepo.findByQuoteTypeAndPackageCodeAndIsActiveTrue(
                                request.getQuoteType().name(), pkg.getPackageCode());

                BigDecimal total = BigDecimal.ZERO;
                List<QuoteFishItem> items = new ArrayList<>();

                for (PackageFishComposition fish : fishes) {

                    int qty = resolveQuantity(fish, acres);

                    BigDecimal price = priceRepo.findActivePrice(
                            fish.getFishType().getId(),
                            fish.getFishSize().getId(),
                            fish.getUnitType().getId(),
                            fish.getVariant());

                    if (price == null) {
                        throw new PriceNotFoundException(
                                "Price not found for Fish=" + fish.getFishType().getName() +
                                ", Size=" + fish.getFishSize().getName() +
                                ", Unit=" + fish.getUnitType().getCode() +
                                ", Variant=" + fish.getVariant());
                    }

                    total = total.add(price.multiply(BigDecimal.valueOf(qty)));

                    items.add(
                            QuoteFishItem.builder()
                                    .fishType(fish.getFishType().getName())
                                    .size(fish.getFishSize().getName())
                                    .unitType(resolveUnitType(fish.getUnitType().getCode()))
                                    .quantity(qty)
                                    .build());
                }

                if (total.compareTo(MIN_ORDER) < 0) {
                    total = MIN_ORDER;
                }

                int miles = Optional.ofNullable(request.getDistanceFromLonokeMiles()).orElse(0);
                BigDecimal rate = Optional.ofNullable(distanceRepo.findRate(miles)).orElse(BigDecimal.ZERO);
                BigDecimal delivery = rate.multiply(BigDecimal.valueOf(miles));

                total = total.add(delivery);

                result.add(
                        QuotePackageOption.builder()
                                .packageCode(pkg.getPackageCode())
                                .packageName(pkg.getPackageName())
                                .estimatedPrice(total)
                                .fishItems(items)
                                .build());
            }

            return QuoteEstimateResponse.builder()
                    .quoteType(request.getQuoteType())
                    .packages(result)
                    .addons(addonService.getAddons(request.getQuoteType()))
                    .minimumOrderAmount(MIN_ORDER)
                    .message("Quote estimation successful.")
                    .infoNotes(List.of(
                            "Can be stocked year round so long as not too hot for redear",
                            "All fish stocked at same time"))
                    .disclaimerNotes(List.of(
                            "Estimated price is calculated using pond size, fish size and distance from Lonoke, Arkansas",
                            "A representative will contact you to confirm the estimate prior to fish delivery"))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            String errorMessage;
            if (e instanceof PriceNotFoundException) {
                errorMessage = "Pricing configuration missing. Please contact support.";
            } else {
                errorMessage = "An error occurred while estimating the quote. Please try again later.";
            }

            return QuoteEstimateResponse.builder()
                    .quoteType(request.getQuoteType())
                    .packages(new ArrayList<>())
                    .addons(addonService.getAddons(request.getQuoteType()))
                    .minimumOrderAmount(MIN_ORDER)
                    .message(errorMessage)
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }

    private int resolveQuantity(PackageFishComposition fish, BigDecimal acres) {
        Integer perAcre = fish.getQuantityPerAcre();
        if (perAcre != null && perAcre > 0) {
            return acres.multiply(BigDecimal.valueOf(perAcre))
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValue();
        }

        return Optional.ofNullable(fish.getFixedQuantity()).orElse(0);
    }

    private UnitType resolveUnitType(String code) {
        if (code == null || code.isBlank()) {
            return UnitType.FISH;
        }

        try {
            return UnitType.valueOf(code.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return UnitType.FISH;
        }
    }
}