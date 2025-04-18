package com.kardex.domain.spi;

import com.kardex.domain.model.Status;

public interface IStatusPersistencePort {

    Status getStatusById(Long statusId);
}
