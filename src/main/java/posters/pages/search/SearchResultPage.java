package posters.pages.search;

import posters.pages.components.plp.ProductGrid;
import posters.pages.components.plp.ProductSearchResult;
import posters.pages.components.plp.ProductSearchResultCount;
import posters.pages.general.GeneralPages;

public class SearchResultPage extends GeneralPages
{
    public static final SearchResultPage instance = new SearchResultPage();

    public ProductSearchResult productSearchResult = ProductSearchResult.instance;
    public ProductSearchResultCount productSearchResultCount = ProductSearchResultCount.instance;
    public ProductGrid productGrid = ProductGrid.instance;

    @Override
    public void validate()
    {
        super.validate();

        validate(has(productSearchResult, productSearchResultCount, productGrid));
    }

    @Override
    public boolean is()
    {
        return matches(has(productSearchResult, productSearchResultCount, productGrid));
    }
}
