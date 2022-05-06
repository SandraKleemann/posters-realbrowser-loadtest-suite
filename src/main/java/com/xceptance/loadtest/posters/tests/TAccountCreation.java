package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.posters.pages.GeneralPage;
import com.xceptance.loadtest.posters.pages.Homepage;

public class TAccountCreation extends LoadTestCase
{
	/**
	 * Create a new account
	 */
    public void test()
    {
    	// loads the homepage, data needed is taking from the properties automatically
    	// using the Context as well as the attached configuration.
    	Homepage.open();
    	
    	Context.get().data.attachAccount();
    	GeneralPage.goToLogin();
    	GeneralPage.createAccount();
    }
}
