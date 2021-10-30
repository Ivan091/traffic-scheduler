package edu.config.infra;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
public class CamelCaseNamingStrategy implements NamingStrategy {

    private String toCamelCase(String name) {
        if (StringUtils.equalsIgnoreCase(name, "id")) {
            return "ID";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @Override
    @NonNull
    public String getTableName(Class<?> type) {
        return toCamelCase(type.getSimpleName());
    }

    @Override
    @NonNull
    public String getColumnName(RelationalPersistentProperty property) {
        return toCamelCase(property.getName());
    }
}
