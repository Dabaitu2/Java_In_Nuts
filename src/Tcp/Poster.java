package Tcp;

import java.util.Map;

public interface Poster {
    void Post(String url, Map<String, String> form);
}
