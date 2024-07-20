package utils.interfaces;

import java.util.List;
import java.util.Map;

public interface Readable {
    <T>List<Map<String, String>> read (String filename, Class<T> cl);
}