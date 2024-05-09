package com.thanhtan.groceryshop.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingRequest implements Serializable {
    @Min(message = "Rating start should be min 1", value = 1)
    @Max(message = "Rating start should be max 5", value = 5)
    double rate;
    Long productId;
    Long userId;
}