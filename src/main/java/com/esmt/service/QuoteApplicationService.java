package com.esmt.service;

 

import org.springframework.stereotype.Service;

import com.esmt.config.QuoteEstimatorFactory;
import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuoteApplicationService {

    private final QuoteEstimatorFactory factory;
   // private final QuoteRepository quoteRepository;
  //  private final QuoteItemRepository itemRepository;

    public QuoteEstimateResponse preview(QuoteRequestDto dto) {

        return factory.get(dto.getQuoteType())
                .estimate(dto);
    }

    @Transactional
    public QuoteEstimateResponse finalizeQuote(QuoteRequestDto dto) {

     /*   QuoteResponse response =
                factory.get(dto.getQuoteType())
                        .estimate(dto);

        QuoteEntity entity = new QuoteEntity();
        entity.setFullName(dto.getFullName());
        entity.setPondSurfaceAcres(dto.getPondSurfaceAcres());
        entity.setQuoteType(dto.getQuoteType());
        entity.setSubTotal(response.getSubTotal());
        entity.setDeliveryCharge(response.getDeliveryCharge());
        entity.setGrandTotal(response.getGrandTotal());

        quoteRepository.save(entity);

        List<QuoteItemEntity> items =
                response.getFishItems().stream()
                        .map(i -> {
                            QuoteItemEntity e = new QuoteItemEntity();
                            e.setQuoteId(entity.getId());
                            e.setFishType(i.getFishType());
                            e.setSizeCategory(i.getSizeCategory());
                            e.setQuantity(i.getQuantity());
                            e.setUnitPrice(i.getUnitPrice());
                            e.setTotalPrice(i.getTotalPrice());
                            return e;
                        }).toList();

        itemRepository.saveAll(items);

        return response;*/
    	return null;
    }
}