package com.xceptance.loadtest.posters.actions.catalog;

import org.junit.Assert;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.xceptance.loadtest.api.actions.AjaxAction;
import com.xceptance.loadtest.api.util.HttpRequest;
import com.xceptance.loadtest.posters.pages.catalog.ProductListingPage;

/**
 * Views more items, either via paging or some load more (infinite scroll) operation.
 */
public class DisplayMore extends AjaxAction<DisplayMore>
{
    /**
     * The selected paging link.
     */
    private HtmlElement moreButton;

    /**
     * Initial product count
     */
    private int productCount;

    @Override
    public void precheck()
    {
        // Get the 'view more' button
        moreButton = ProductListingPage.instance.productGrid.getMoreButton().asserted("No Display More option available").single();

        // Store the current product count for validation purposes
        productCount = ProductListingPage.instance.productGrid.getDisplayedProductCount();
        
        // If the displayed item count is not helpful, use our item count.
        // productCount = ProductListingPage.instance.itemCount.getItemCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute() throws Exception
    {
        // Save old footer element
        final HtmlElement oldFooter = ProductListingPage.instance.productGrid.getFooter().asserted("Failed to find grid footer element").single();

        // Request more elements
        new HttpRequest().XHR().url(moreButton.getAttribute("data-url"))
                        .param("selectedUrl", moreButton.getAttribute("data-url"))
                        .appendTo(ProductListingPage.instance.productGrid.locate().first())
                        .assertContent("Nothing came back from XHR.", true, HttpRequest.NOT_BLANK)
                        .fire();

        // Remove old footer, otherwise we would have two
        oldFooter.remove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void postValidate() throws Exception
    {
        // Validate that we end up on PLP
        ProductListingPage.instance.validate();

        // Get the new (displayed) item count on the page
        final int newProductCount = ProductListingPage.instance.productGrid.getDisplayedProductCount();
        // Or alternatively user our item count again
        // final int newProductCount = ProductListingPage.instance.itemCount.getItemCount();

        // Validate that we have at least one more item
        Assert.assertTrue("New displayed product count is not larger than old", newProductCount > productCount);
    }
}