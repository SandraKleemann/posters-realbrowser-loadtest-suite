package com.xceptance.loadtest.headless.pages.components.account;

import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.pages.Page;
import com.xceptance.loadtest.api.pages.components.Component;
import com.xceptance.loadtest.api.util.Context;

public class AddressBookCard implements Component
{
    public final static AddressBookCard instance = new AddressBookCard();

    /**
     * Lookup the footer.
     */
    @Override
    public LookUpResult locate()
    {
        // this CSS path is SO SO SO bad
        return Page.find().byCss(".card").hasCss("a[aria-label = '" + Context.configuration().localizedText("account.addressbookcard.link.aria-label") + "']");
    }

    /**
     * Indicates if this component exists
     *
     * @return
     */
    @Override
    public boolean exists()
    {
        return locate().exists();
    }
}
