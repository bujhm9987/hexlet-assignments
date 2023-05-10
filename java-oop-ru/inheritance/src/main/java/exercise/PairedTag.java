package exercise;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public class PairedTag extends Tag{

    private String body;
    private List<Tag> child;

    public PairedTag(String name, Map<String, String> attributes, String body, List<Tag> child) {
        super(name, attributes);
        this.body = body;
        this.child = child;
    }

    public String toString() {
        String result = "<" + super.getName();
        Map<String, String> attributes = super.getAttributes();
        for (String key : attributes.keySet()) {
            result = result + " " + key + "=" + "\"" + attributes.get(key) + "\"";
        }
        result = result + ">" + body;
        for (Tag childTag : child) {
            result = result + childTag.toString();
        }
        return result + "</" + super.getName() + ">";
    }
}
// END
