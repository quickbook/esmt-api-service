package com.esmt.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteUIConfig {

    private String title;
    private String description;

    private List<String> infoNotes;
    private List<String> disclaimerNotes;

    // Optional future fields (keep for extensibility)
    private String successMessage;
    private String errorMessage;

}