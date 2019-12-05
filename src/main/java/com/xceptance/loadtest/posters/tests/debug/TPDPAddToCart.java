package com.xceptance.loadtest.posters.tests.debug;

import com.xceptance.loadtest.api.actions.debug.DebugUrl;
import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.posters.actions.cart.AddToCart;
import com.xceptance.loadtest.posters.actions.cart.ShowMiniCart;
import com.xceptance.loadtest.posters.actions.cart.ViewCart;
import com.xceptance.loadtest.posters.flows.ConfigureProductFlow;
import com.xceptance.loadtest.posters.flows.VisitFlow;

/**
 * Simple test to get all possible add to cart operations tested easily
 */
public class TPDPAddToCart extends LoadTestCase
{
    @Override
    public void test() throws Throwable
    {
        // Start at the landing page.
        new VisitFlow().run();

        // Bundle with preselected variations
        new DebugUrl("/s/MobileFirst/womens/jewelry/earrings/womens-jewelry-bundle.html?lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        // Set variations and standard product
        new DebugUrl("/s/MobileFirst/womens/clothing/outfits/random.html?cgid=womens-outfits&lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        // Set only variations
        new DebugUrl("/s/MobileFirst/womens/clothing/outfits/mix-and-match.html?cgid=womens-outfits&lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        // Bundle
        new DebugUrl("/s/MobileFirst/electronics/gaming/sony-ps3-bundle.html?cgid=electronics-gaming&lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        // Variation many attr
        new DebugUrl("/s/MobileFirst/scoop-neck-shell/25493587.html?cgid=womens-clothing-tops&lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        // Variation one attr
        new DebugUrl("/s/MobileFirst/checked-silk-tie/25752235.html?cgid=mens-accessories-ties&lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        // Option
        new DebugUrl(
                        "/s/MobileFirst/electronics/televisions/sony-kdl-40w4100.html?cgid=electronics-televisions&lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        // standard product
        new DebugUrl("/s/MobileFirst/electronics/gaming/lucasarts-star-wars-the-force-unleashed-psp.html?cgid=electronics-gaming&lang=en_US").run();
        new ConfigureProductFlow().run();
        new AddToCart().run();

        new ShowMiniCart().run();

        new ViewCart().run();
    }
}
