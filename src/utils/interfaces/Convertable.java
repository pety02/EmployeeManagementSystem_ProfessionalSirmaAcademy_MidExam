package utils.interfaces;

import java.util.List;
import java.util.Map;

public interface Convertable<T> {
    List<T> fromListOfMapsToListOfModel(List<Map<String, String>> data);
    List<Map<String, String>> fromListOfModelToListOfMaps(List<T> objs);
}