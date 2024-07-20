package managers.interfaces;

import java.util.List;

public interface Manager<T> {
    boolean addEntity(String... args);
    boolean editEntity(int id, String... args);
    boolean removeEntity(int id);
    T getEntity(int id);
    List<T> listAllEntities();
    List<T> searchBy(String criteria, String value);
}