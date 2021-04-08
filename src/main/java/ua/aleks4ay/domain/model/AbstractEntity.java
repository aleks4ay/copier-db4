package ua.aleks4ay.domain.model;

public abstract class AbstractEntity<T> {

//    private String id;
//    private T entity;

    public abstract T getEntity();
    public abstract String getId();
    public abstract String getDifferences(T t);
}
