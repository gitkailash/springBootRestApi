package com.spring.restapi.restapi.converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonConverter<E, D> implements IConverter<E, D> {
    @Override
    public E convertToEntity(D dto) {
        return null;
    }

    @Override
    public D convertToDto(E entity) {
        return null;
    }

    @Override
    public E copyConvertToEntity(D dto, E entity) {
        return null;
    }

    @Override
    public List<D> convertToDtoList(List<E> entities) {

        if (entities == null || entities.isEmpty()) {
            return List.of();
        }

        return entities.parallelStream().map(this::convertToDto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<E> convertToEntityList(List<D> dtoList) {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        return dtoList.parallelStream().map(this::convertToEntity).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
