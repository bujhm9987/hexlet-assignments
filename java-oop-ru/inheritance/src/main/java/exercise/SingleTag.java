package exercise;

import java.util.Map;

// BEGIN
public class SingleTag extends Tag{

    public SingleTag(String name, Map<String, String> attributes) {
        super(name, attributes);
    }

    public String toString() {
        StringBuilder result = new StringBuilder("<" + super.getName());
        Map<String, String> attributes = super.getAttributes();
        for (String key : attributes.keySet()) {
            result.append(" ").append(key).append("=").append("\"").append(attributes.get(key)).append("\"");
        }
        return result + ">";
    }
}
// END
