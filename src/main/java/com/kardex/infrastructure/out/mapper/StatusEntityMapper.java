package com.kardex.infrastructure.out.mapper;

import com.kardex.domain.model.Status;
import com.kardex.infrastructure.out.entity.StatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface StatusEntityMapper {

    Status toStatus(StatusEntity status);
}
