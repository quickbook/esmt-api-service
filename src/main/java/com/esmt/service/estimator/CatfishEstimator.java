package com.esmt.service.estimator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmt.dto.QuoteFishItem;
import com.esmt.dto.QuotePackageOption;
import com.esmt.dto.QuoteUIConfig;
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
public class CatfishEstimator extends BaseNewPondQuoteEstimator {
	
	@Autowired
	QuoteConfigService configService;

	protected CatfishEstimator(QuotePackageRepository packageRepo, PackageFishCompositionRepository compositionRepo,
			FishPriceRepository priceRepo, DistancePricingRepository distanceRepo, AddonService addonService) {
		super(packageRepo, compositionRepo, priceRepo, distanceRepo, addonService);
	 
	} 

 
	@Override
	protected QuoteEstimateResponse buildQuote(QuoteRequestDto request, List<QuotePackageOption> result) {
		
		QuoteUIConfig config = configService.getConfig(request.getQuoteType());

	    return baseResponse(request, result)
	            .title(config.getTitle())
	            .description(config.getDescription())
	            .infoNotes(config.getInfoNotes())
	            .disclaimerNotes(config.getDisclaimerNotes())
	    		.message("Quote estimation successful.")
	            .build();

		 
	}

	@Override
	public QuoteType supportedType() {
		return QuoteType.FISH_CATFISH;
	}
}