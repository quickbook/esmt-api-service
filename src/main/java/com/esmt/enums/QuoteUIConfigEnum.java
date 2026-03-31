package com.esmt.enums;

import java.util.List;

public enum QuoteUIConfigEnum {

	FISH_TROPHY_BASS(
	        "Trophy Bass Pond Estimator",
	        "Grow Bass over 10 pounds, slow long-term growth. Best to stock bass in June.",

	        List.of(
	                "Bluegill, Redear and Minnows stocked in fall or spring",
	                "Bass stocked in June"
	        ),

	        List.of(
	                "Estimated price is calculated using pond size, fish size and distance from Lonoke, Arkansas",
	                "A representative will contact you to confirm the estimate prior to fish delivery"
	        )
	),
    FISH_QUALITY_BASS_BREAM(
            "Bass Pond Estimator",
            "Grow Bass over 5 pounds. Requires regular harvest to maintain balance.",
            List.of(
	                "Bluegill, Redear and Minnows stocked in fall or spring",
	                "Bass stocked in June"
	        ),

	        List.of(
	                "Estimated price is calculated using pond size, fish size and distance from Lonoke, Arkansas",
	                "A representative will contact you to confirm the estimate prior to fish delivery"
	        )
    ),

	FISH_VARIETY_SPECIES("Fishing Pond Estimator", "Grow bass, bream, crappie, and catfish. Great for kids.",
			List.of(
	                "All fish stocked at same time",
	                "Crappie are not available May through September",
	                "Hybrid Crappie can be substituted for Black Crappie if they are available",
	                "Not all sizes of hybrid crappie are available at all times"
	        ),

	        List.of(
	                "Estimated price is calculated using pond size, fish size and distance from Lonoke, Arkansas",
	                "A representative will contact you to confirm the estimate prior to fish delivery")
	        ),

    FISH_CATFISH(
            "Catfish Pond Estimator",
            "Grow catfish up to 5 pounds. Ideal for small ponds with low maintenance.",
        	List.of("All fish stocked at same time"),
			List.of("Estimated price is calculated using pond size, fish size and distance from Lonoke, Arkansas",
					"A representative will contact you to confirm the estimate prior to fish delivery")
    ),

    FISH_BIG_BREAM_SMALL_POND(
            "Hybrid Bream Pond Estimator",
            "Grow bream up to 1 pound with reduced spawning.",
            List.of("All fish stocked at same time"),
			List.of("Estimated price is calculated using pond size, fish size and distance from Lonoke, Arkansas",
					"A representative will contact you to confirm the estimate prior to fish delivery")    
    ),

    ADD_CATCHABLE_FISH(
            "Catchable Fish Stocking",
            "Stock ready-to-catch fish for immediate fishing experience.",
            List.of("Ideal for events or quick fishing setup"),
            List.of("Fish size and availability may vary")
    ),

    FEED_POND_BASS(
            "Feed-Trained Bass",
            "Enhance bass growth using feed-trained bass stocking.",
            List.of("Faster growth with proper feeding"),
            List.of("Requires feeding management")
    ),

    STOCK_GRASS_CARP(
            "Grass Carp Stocking",
            "Control aquatic vegetation using grass carp.",
            List.of("Effective for weed control"),
            List.of("Stocking rates depend on pond conditions")
    ),

    CUSTOM_STOCKING(
            "Custom Pond Stocking",
            "Build your own stocking plan tailored to your pond.",
            List.of("Flexible fish combinations"),
            List.of("Final pricing confirmed by representative")
    );

    private final String title;
    private final String description;
    private final List<String> infoNotes;
    private final List<String> disclaimerNotes;

    QuoteUIConfigEnum(String title, String description,
                      List<String> infoNotes,
                      List<String> disclaimerNotes) {
        this.title = title;
        this.description = description;
        this.infoNotes = infoNotes;
        this.disclaimerNotes = disclaimerNotes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getInfoNotes() {
        return infoNotes;
    }

    public List<String> getDisclaimerNotes() {
        return disclaimerNotes;
    }
}