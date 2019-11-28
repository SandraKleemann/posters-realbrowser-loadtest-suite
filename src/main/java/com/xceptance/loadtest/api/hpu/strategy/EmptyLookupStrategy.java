package com.xceptance.loadtest.api.hpu.strategy;

import java.util.Collections;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.xceptance.loadtest.api.hpu.Strategy;

/**
 * Empty strategy to be able to have an always false return result
 *
 * @author rschwietzke
 */
public class EmptyLookupStrategy extends AbstractLookupStrategy
{
    public EmptyLookupStrategy()
    {
        super((Strategy) null, "NonNull");
    }

    @Override
    protected String getStrategyName()
    {
        return "Empty";
    }

    @Override
    protected List<?> lookup(final DomNode parent)
    {
        return Collections.emptyList();
    }
}