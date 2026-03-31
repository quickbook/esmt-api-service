package com.esmt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.esmt.dto.QuoteAddonOption;
import com.esmt.enums.QuoteType;
import com.esmt.model.AddonStateMapping;
import com.esmt.model.QuoteAddonMaster;
import com.esmt.repository.AddonStateRepository;
import com.esmt.repository.QuoteAddonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddonService {

    private final QuoteAddonRepository addonRepo;
    private final AddonStateRepository stateRepo;

 
    public List<QuoteAddonOption> getAddons(QuoteType quoteType) {

        List<QuoteAddonMaster> addons =
                addonRepo.findByQuoteTypeAndIsActiveTrue(quoteType.name());

        return addons.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private QuoteAddonOption mapToDto(QuoteAddonMaster addon) {

        List<String> states = stateRepo.findByAddonId(addon.getId())
                .stream()
                .map(AddonStateMapping::getStateName)
                .collect(Collectors.toList());

        return QuoteAddonOption.builder()
                .addonCode(addon.getAddonCode())
                .fishName(addon.getAddonName())
                .fishSize(addon.getDescription())
                .quantity(Optional.ofNullable(addon.getDefaultQuantity()).orElse(0))
                .unitPrice(new BigDecimal(10.00))
                .selected(Optional.ofNullable(addon.getSelected()).orElse(false))
                .eligibleStates(states)
                .build();
    }
}