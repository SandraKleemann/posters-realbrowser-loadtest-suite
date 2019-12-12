package com.xceptance.loadtest.posters.actions.catalog;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.xceptance.loadtest.api.actions.PageAction;
import com.xceptance.loadtest.api.validators.Validator;
import com.xceptance.loadtest.posters.pages.general.GeneralPages;

/**
 * Selects a category from the top navigation menu.
 *
 * @author Matthias Ullrich (Xceptance Software Technologies GmbH)
 */
public class ClickATopCategory extends PageAction<ClickATopCategory>
{
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute() throws Exception
    {
    	HtmlElement categoryLink = GeneralPages.instance.navigation.getTopCategories().asserted("No top categories found").random();

        loadDebugUrlOrElse("/s/SiteGenesis/new arrivals/?lang=en_US").loadPageByClick(categoryLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void postValidate() throws Exception
    {
        Validator.validatePageSource();

        GeneralPages.instance.validate();
    }
}
