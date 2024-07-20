package utils.interfaces;

import java.util.List;
import java.util.Map;

public interface Writable {
    boolean write(String filename, List<Map<String, String>> objs);
}