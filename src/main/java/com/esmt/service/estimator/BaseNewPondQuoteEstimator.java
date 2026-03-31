package com.esmt.service.estimator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.esmt.dto.QuoteFishItem;
import com.esmt.dto.QuotePackageOption;
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
 

 
public abstract class BaseNewPondQuoteEstimator implements QuoteEstimator {

    protected static final BigDecimal MIN_ORDER = BigDecimal.valueOf(750);

    protected final QuotePackageRepository packageRepo;
    protected final PackageFishCompositionRepository compositionRepo;
    protected final FishPriceRepository priceRepo;
    protected final DistancePricingRepository distanceRepo;
    protected final AddonService addonService;

    protected BaseNewPondQuoteEstimator(
            QuotePackageRepository packageRepo,
            PackageFishCompositionRepository compositionRepo,
            FishPriceRepository priceRepo,
            DistancePricingRepository distanceRepo,
            AddonService addonService) {

        this.packageRepo = packageRepo;
        this.compositionRepo = compositionRepo;
        this.priceRepo = priceRepo;
        this.distanceRepo = distanceRepo;
        this.addonService = addonService;
    }

    @Override
    public QuoteEstimateResponse estimate(QuoteRequestDto request) {

        try {
            BigDecimal acres = request.getPondSurfaceAcres();

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

                    int qty = fish.getQuantityPerAcre() * acres.intValue();

                    BigDecimal price = priceRepo.findActivePrice(
                            fish.getFishType().getId(),
                            fish.getFishSize().getId(),
                            fish.getUnitType().getId(),
                            fish.getVariant()
                    );

                    if (price == null) {
                        throw new PriceNotFoundException("Price missing for " + fish.getFishType().getName());
                    }

                    total = total.add(price.multiply(BigDecimal.valueOf(qty)));

                    items.add(
                            QuoteFishItem.builder()
                                    .fishType(fish.getFishType().getName())
                                    .size(fish.getFishSize().getName())
                                    .unitType(UnitType.valueOf(fish.getUnitType().getCode()))
                                    .quantity(qty)
                                    .build()
                    );
                }

                total = applyMinimum(total);

                BigDecimal delivery = distanceRepo.findRate(request.getDistanceFromLonokeMiles())
                        .multiply(BigDecimal.valueOf(request.getDistanceFromLonokeMiles()));

                total = total.add(delivery);

                result.add(
                        QuotePackageOption.builder()
                                .packageCode(pkg.getPackageCode())
                                .packageName(pkg.getPackageName())
                                .estimatedPrice(total)
                                .fishItems(items)
                                .build()
                );
            }

            return buildQuote(request, result);

        } catch (Exception e) {
        	e.printStackTrace();
            return buildErrorResponse(request, e);
        }
    }

    protected BigDecimal applyMinimum(BigDecimal total) {
        return total.compareTo(MIN_ORDER) < 0 ? MIN_ORDER : total;
    }
    
    protected QuoteEstimateResponse.QuoteEstimateResponseBuilder baseResponse(
            QuoteRequestDto request, List<QuotePackageOption> result) {

        return QuoteEstimateResponse.builder()
                .quoteType(request.getQuoteType())
                .packages(result)
                .addons(addonService.getAddons(request.getQuoteType()))
                .minimumOrderAmount(MIN_ORDER)
                .message("Quote estimation successful.");
    }

    // 🔥 Hooks (ONLY DIFFERENCE BETWEEN FLOWS)
    protected abstract QuoteEstimateResponse buildQuote(
            QuoteRequestDto request, List<QuotePackageOption> result);

    protected QuoteEstimateResponse buildErrorResponse(QuoteRequestDto request, Exception e) {
        return QuoteEstimateResponse.builder()
                .quoteType(request.getQuoteType())
                .packages(new ArrayList<>())
                .addons(addonService.getAddons(request.getQuoteType()))
                .minimumOrderAmount(MIN_ORDER)
                .message("Error occurred")
                .errors(List.of(e.getMessage()))
                .build();
    }
}