package com.cravebite.backend_2.models.request;

import com.cravebite.backend_2.models.enums.CourierStatus;
import com.cravebite.backend_2.models.enums.NavigationMode;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierRequestDTO {

    @NotNull(message = "userId cannot be null")
    private Long userId;

    @NotNull(message = "locationId cannot be null")
    private Long locationId;

    @NotNull(message = "status cannot be null")
    private CourierStatus status;

    @NotNull(message = "availability cannot be null")
    private Boolean availability;

    @NotNull(message = "mode cannot be null")
    private NavigationMode mode;

}
