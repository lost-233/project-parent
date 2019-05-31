package com.zhaoming.web.thymeleaf;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

/**
 * FormatDialect
 *
 * @author hhx
 */
public class FormatDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Format Dialect";


    public FormatDialect() {
        // We will set this dialect the same "dialect processor" precedence as
        // the Standard Dialect, so that processor executions can interleave.
        super(DIALECT_NAME, "format", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new StringProcessor(dialectPrefix));
        processors.add(new NumberProcessor(dialectPrefix));
        processors.add(new DateProcessor(dialectPrefix));
        return processors;
    }
}
