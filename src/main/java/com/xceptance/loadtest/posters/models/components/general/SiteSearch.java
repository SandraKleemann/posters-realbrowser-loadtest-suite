package com.xceptance.loadtest.posters.models.components.general;

import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.models.components.Component;

public enum SiteSearch implements Component
{
    instance;

    @Override
    public LookUpResult locate()
    {
        return Header.instance.locate().byCss("#header-menu-search");
    }

    @Override
    public boolean exists()
    {
        return locate().exists();
    }

    /**
     * Returns the container for the suggestions
     *
     * @return a DOM search result
     */
    public LookUpResult locateSuggestionContainer()
    {
        return locate().byCss(".suggestions-wrapper");
    }

    /**
     * Returns the inputfield for the text
     *
     * @return a DOM search result
     */
    public LookUpResult locateInputfield()
    {
        return locate().byCss("#searchForm input#s");
    }
}
