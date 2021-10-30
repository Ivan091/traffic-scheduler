package edu.config.infra;

import org.springframework.context.annotation.Primary;
import org.springframework.data.relational.core.dialect.PostgresDialect;
import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
@Primary
public final class PostgresCustomDialect extends PostgresDialect {

    @Override
    @NonNull
    public IdentifierProcessing getIdentifierProcessing() {
        return IdentifierProcessing.create(IdentifierProcessing.Quoting.ANSI, IdentifierProcessing.LetterCasing.AS_IS);
    }
}
