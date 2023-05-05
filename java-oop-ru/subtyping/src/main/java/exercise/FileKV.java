package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
public class FileKV implements KeyValueStorage{

    private String path;

    public FileKV(String path, Map<String, String> dictionary){
        this.path = path;
        Utils.writeFile(path, Utils.serialize(dictionary));
    }

    @Override
    public void set(String key, String value) {
        Map<String, String> newString = Utils.unserialize(Utils.readFile(path));
        newString.put(key, value);
        Utils.writeFile(path, Utils.serialize(newString));
    }

    @Override
    public void unset(String key) {
        Map<String, String> newString = Utils.unserialize(Utils.readFile(path));
        newString.remove(key);
        Utils.writeFile(path, Utils.serialize(newString));
    }

    @Override
    public String get(String key, String defaultValue) {
        Map<String, String> newString = Utils.unserialize(Utils.readFile(path));
        return newString.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return new HashMap<>(Utils.unserialize(Utils.readFile(path)));
    }
}
// END
