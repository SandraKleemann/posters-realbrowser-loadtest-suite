package com.xceptance.loadtest.headless.tests;

import com.xceptance.loadtest.api.events.EventLogger;
import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.headless.actions.account.Logout;
import com.xceptance.loadtest.headless.actions.cart.ViewCart;
import com.xceptance.loadtest.headless.actions.checkout.Checkout;
import com.xceptance.loadtest.headless.actions.checkout.CheckoutLogin;
import com.xceptance.loadtest.headless.actions.checkout.CheckoutSelectShipping;
import com.xceptance.loadtest.headless.actions.checkout.CheckoutShippingAddress;
import com.xceptance.loadtest.headless.actions.checkout.CheckoutSubmitBilling;
import com.xceptance.loadtest.headless.actions.checkout.CheckoutSubmitShipping;
import com.xceptance.loadtest.headless.flows.AddToCartFlow;
import com.xceptance.loadtest.headless.flows.CartCleanUpFlow;
import com.xceptance.loadtest.headless.flows.CreateAccountFlow;
import com.xceptance.loadtest.headless.flows.VisitFlow;
import com.xceptance.loadtest.headless.pages.cart.CartPage;
import com.xceptance.loadtest.headless.pages.general.GeneralPages;

/**
 * Open the landing page, register account if necessary and browse the catalog to a random product. Configure this
 * product, add it to the cart and process the checkout steps but do not place the order finally.
 *
 * @author Matthias Ullrich (Xceptance Software Technologies GmbH)
 */
public class TRegisteredCheckout extends LoadTestCase
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void test() throws Throwable
    {
        // Mark test case to be used by a registered customer.
        Context.requiresRegisteredAccount(true);

        // Start at the landing page.
        new VisitFlow().run();

        // first account access, hence we use getAccount
        if (Context.get().data.attachAccount().get().isRegistered == false)
        {
            // Register user
            new CreateAccountFlow().run();

            // Logout from freshly created account, but login later during checkout again
            new Logout().run();
        }

        // Fill
        new AddToCartFlow(Context.configuration().addToCartCount.value).run();

        // View the cart if not just done
        if (!CartPage.instance.is())
        {
            new ViewCart().runIfPossible();
        }

        if (CartPage.instance.isOrderable() == false)
        {
            new CartCleanUpFlow().run();
        }

        // we can only checkout if we still got a cart
        if (GeneralPages.instance.miniCart.isEmpty() == false)
        {
            new Checkout().run();

            // make sure we use the account we already got before using getattachedaccount
            new CheckoutLogin(Context.get().data.getAccount().get()).run();

            new CheckoutShippingAddress().run();
            new CheckoutSelectShipping().run();
            new CheckoutSubmitShipping().run();

            new CheckoutSubmitBilling().run();

            // no place order
            // we cannot log out from this page
        }
        else
        {
            EventLogger.CHECKOUT.warn("Empty Cart", "Cart was empty before checkout was started");
        }
    }
}
