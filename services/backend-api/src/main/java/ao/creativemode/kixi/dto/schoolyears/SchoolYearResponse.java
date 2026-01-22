package ao.creativemode.kixi.dto.schoolyears;

import java.time.LocalDateTime;

public record SchoolYearResponse(
        Long id,
        Integer startYear,
        Integer endYear,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {}