package com.xceptance.loadtest.headless.tests;

import com.xceptance.loadtest.api.events.EventLogger;
import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.headless.actions.account.Logout;
import com.xceptance.loadtest.headless.actions.cart.ViewCart;
import com.xceptance.loadtest.headless.actions.checkout.Checkout;
import com.xceptance.loadtest.headless.actions.checkout.CheckoutLogin;
import com.xceptance.loadtest.headless.actions.checkout.CheckoutPlaceOrder;
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
 * product and add it to the cart. Finally process the checkout including the final order placement step.
 *
 * @author Matthias Ullrich (Xceptance Software Technologies GmbH)
 */
public class TRegisteredOrder extends LoadTestCase
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void test() throws Throwable
    {
        Context.requiresRegisteredAccount(true);

        // Start at the landing page.
        new VisitFlow().run();

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

            // use the attached account to avoid that we get a new one, either we have one or we
            // don't, hence prefer to fail
            new CheckoutLogin(Context.get().data.getAccount().get()).run();

            new CheckoutShippingAddress().run();
            new CheckoutSelectShipping().run();
            new CheckoutSubmitShipping().run();

            new CheckoutSubmitBilling().run();

            new CheckoutPlaceOrder().run();

            new Logout().run();
        }
        else
        {
            EventLogger.CHECKOUT.warn("Empty Cart", "Cart was empty before checkout was started");
        }
    }
}
