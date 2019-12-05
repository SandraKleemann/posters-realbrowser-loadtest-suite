package posters.tests;

import com.xceptance.loadtest.api.events.EventLogger;
import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;

import posters.actions.cart.ViewCart;
import posters.actions.checkout.Checkout;
import posters.actions.checkout.CheckoutGuest;
import posters.actions.checkout.CheckoutSelectShipping;
import posters.actions.checkout.CheckoutShippingAddress;
import posters.actions.checkout.CheckoutSubmitBilling;
import posters.actions.checkout.CheckoutSubmitShipping;
import posters.flows.AddToCartFlow;
import posters.flows.CartCleanUpFlow;
import posters.flows.VisitFlow;
import posters.pages.cart.CartPage;
import posters.pages.general.GeneralPages;

/**
 * Open the landing page and browse the catalog to a random product. Configure
 * this product, add it to the cart and process the checkout steps but do not
 * place the order finally.
 *
 * @author Matthias Ullrich (Xceptance Software Technologies GmbH)
 */
public class TGuestCheckout extends LoadTestCase
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void test() throws Throwable
    {
        // Start at the landing page.
        new VisitFlow().run();

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

        // we have not touched any account yet
        // attach it to the context, this method will complain if we
        // set one up already
        Context.get().data.attachAccount();

        // we can only checkout if we still got a cart
        if (GeneralPages.instance.miniCart.isEmpty() == false)
        {
            new Checkout().run();

            new CheckoutGuest().run();

            new CheckoutShippingAddress().run();
            new CheckoutSelectShipping().run();
            new CheckoutSubmitShipping().run();

            new CheckoutSubmitBilling().run();
        }
        else
        {
            EventLogger.CHECKOUT.warn("Empty Cart", "Cart was empty before checkout was started");
        }
    }
}