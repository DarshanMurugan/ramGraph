package Builder;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.AbstractBeanField;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Graph.Node;


public class KeyValueBuilder extends AbstractBeanField<Object, String> {

    private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("(\\w+)=\"([^\"]*)\"");

    @Override
    protected Object convert(String value) {
        Map<String, Object> map = new HashMap<>();

        if (value == null || value.trim().isEmpty()) {
            return map;
        }

        Matcher matcher = KEY_VALUE_PATTERN.matcher(value);

        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }

        return map;
    }
}
