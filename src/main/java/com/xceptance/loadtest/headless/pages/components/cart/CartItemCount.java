package com.xceptance.loadtest.headless.pages.components.cart;

import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.pages.Page;
import com.xceptance.loadtest.api.pages.components.Component;
import com.xceptance.loadtest.api.render.HtmlRenderer;

public class CartItemCount implements Component
{
    public final static CartItemCount instance = new CartItemCount();

    /**
     * Lookup the footer.
     */
    @Override
    public LookUpResult locate()
    {
        return Page.find().byCss(".cart-header .number-of-items");
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

    public void renderAndUpdate(final HtmlRenderer renderer)
    {
        renderer.template("/templates/cart/cart-item-count.ftlh").replace(locate().asserted().single());
    }
}
