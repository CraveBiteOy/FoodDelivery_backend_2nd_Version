package com.cravebite.backend_2.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDTO {

    @NotNull(message = "userId cannot be null")
    private Long userId;

    @NotNull(message = "locationId cannot be null")
    private Long locationId;
}