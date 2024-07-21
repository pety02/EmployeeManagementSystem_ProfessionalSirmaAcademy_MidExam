package services.interfaces;

import java.util.List;

public interface Service<T> {
    T addEntity(String... args) throws RuntimeException;
    T editEntity(int id, String... args) throws RuntimeException;
    boolean removeEntity(int id);
    T getEntity(int id) throws RuntimeException;
    List<T> listAllEntities();
}