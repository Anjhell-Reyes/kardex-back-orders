package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.exception.StatusNotFoundException;
import com.kardex.domain.model.Status;
import com.kardex.domain.spi.IStatusPersistencePort;
import com.kardex.infrastructure.out.entity.StatusEntity;
import com.kardex.infrastructure.out.mapper.StatusEntityMapper;
import com.kardex.infrastructure.out.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusJpaAdapter implements IStatusPersistencePort {

    private final IStatusRepository statusRepository;
    private final StatusEntityMapper statusEntityMapper;

    @Override
    public Status getStatusById(Long statusId) {
        return statusEntityMapper.toStatus(statusRepository.findById(statusId).orElseThrow(StatusNotFoundException::new));
    }
}
