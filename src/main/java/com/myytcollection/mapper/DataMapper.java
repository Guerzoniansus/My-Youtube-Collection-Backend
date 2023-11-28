package com.myytcollection.mapper;

/**
 * An interface for mapping Model class to DTO classes and vice-versa.
 * @param <M> The model class.
 * @param <D> The DTO class.
 */
public interface DataMapper<M, D> {

    /**
     * Creates a model object based on the given DTO object.
     * Keep in mind that if the Model class has fields that do not exist in the DTO object,
     * that <b>those fields will be set to null</b>.
     * @param dto The DTO object to convert.
     * @return A new Model object.
     */
    M toModel(D dto);

    /**
     * Creates a DTO object based on the given Model object.
     * @param model The model object to convert.
     * @return The new DTO object.
     */
    D toDTO(M model);
}