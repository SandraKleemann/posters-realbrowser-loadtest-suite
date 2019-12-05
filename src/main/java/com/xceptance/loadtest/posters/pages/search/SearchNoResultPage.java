package com.xceptance.loadtest.posters.pages.search;

import com.xceptance.loadtest.posters.pages.components.plp.BlacklistedProductGrid;
import com.xceptance.loadtest.posters.pages.components.plp.ProductGrid;
import com.xceptance.loadtest.posters.pages.general.GeneralPages;

public class SearchNoResultPage extends GeneralPages
{
    public static final SearchNoResultPage instance = new SearchNoResultPage();

    @Override
    public void validate()
    {
        super.validate();

        validate(hasAnyOf(has(BlacklistedProductGrid.instance), hasNot(ProductGrid.instance)));
    }

    @Override
    public boolean is()
    {
        return super.is() && (matches(hasNot(ProductGrid.instance)) || matches(has(BlacklistedProductGrid.instance)));
    }
}
