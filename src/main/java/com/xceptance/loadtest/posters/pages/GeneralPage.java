package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;

import org.junit.Assert;

import static com.codeborne.selenide.CollectionCondition .*;
import static com.codeborne.selenide.Condition .*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.xlt.api.util.XltRandom;

/**
 * Contains everything doable on all page types.
 * 
 * @author rschwietzke
 */
public class GeneralPage 
{
	public static void search(final String phrase, final String expectedCount)
	{
		Action.run("Search", () ->
		{
			// open search
			$("#header-search-trigger").click();

			// enter phrase
			$("#searchForm input[name='searchText']").sendKeys(phrase);

			// send search, this is our page load
			$("#btnSearch").click();

			// verify count
			$("#totalProductCount").should(Condition.exactText(expectedCount));
		});
	}
	
	public static void openTopCategory()
	{
	    Action.run("OpenTopCategory", () ->
	    {
	        // click random top category link
	        ElementsCollection categoryLinks = getTopCategories();
	        SelenideElement randomCatagory = categoryLinks.get(XltRandom.nextInt(0, categoryLinks.size()-1));
	        randomCatagory.click();
	        
	        // verify category page
	        $("#titleCategoryName").should(exist);
	    });
	}
	
	public static void openCategory()
	{
	    Action.run("OpenCategory", () ->
	    {
	        // click random category link
	        ElementsCollection topCategories = getTopCategories();
	        SelenideElement randomTopCategory = topCategories.get(XltRandom.nextInt(0, topCategories.size()-1)).hover();
	        ElementsCollection categoryLinks = randomTopCategory.$$("ul.dropdown-menu a").filterBy(visible).as("CategoryLinks").shouldBe(sizeGreaterThan(0));
            SelenideElement randomCatagory = categoryLinks.get(XltRandom.nextInt(0, categoryLinks.size()-1));
	        randomCatagory.click();
	        
	        // verify category page
	        $("#titleCategoryName").should(exist);
	    });
	}

    private static ElementsCollection getTopCategories()
    {
        return $$("#categoryMenu .header-menu-item").filterBy(visible).as("TopCategories").shouldBe(sizeGreaterThan(0));
    }

    public static void openProductDetailsPage()
    {
        Action.run("OpenProduct", () ->
        {
            ElementsCollection productLinks = $$("#productOverview a").filterBy(visible).as("Products").shouldBe(sizeGreaterThan(0));
            productLinks.get(XltRandom.nextInt(0, productLinks.size()-1)).click();       
            
            // verify
            $("#addToCartForm").should(exist);
        });
    }

    public static void executePaging()
    {
        ElementsCollection pagingLinks = $$("#pagination-bottom li:not(.active)>a:not([title*='Go to'])").filterBy(visible);
        if(Context.configuration().displayMoreProbability.random() && !pagingLinks.isEmpty())
        {
            Action.run("Paging", () ->
            {
                pagingLinks.shouldBe(sizeGreaterThan(0));
                SelenideElement pagingLink = pagingLinks.get(XltRandom.nextInt(0, pagingLinks.size()-1));
                int page = Integer.parseInt(pagingLink.getText());
                pagingLink.click();   
                
                // verify
                int currentpage = Integer.parseInt($("#pagination-bottom .active > a").should(visible).getText());
                Assert.assertTrue("Paging was not successfull", page == currentpage);
            });           
        }       
    }

    public static void viewCart()
    {
        Action.run("ViewCart", () ->
        {
            $("#headerCartOverview").should(visible).hover();
            $("#miniCartMenu .btn-primary").should(visible).hover().click();
            
            // validate
            $("#cartOverviewTable").should(visible);
        });
        
    }

    public static void goToLogin()
    {
        Action.run("GoToLogin", () ->
        {
            $("#showUserMenu").should(visible).hover();
            $(".goToRegistration").should(visible).hover().click();
            
            // validate
            $("#formRegister").should(visible);
        });
    }
    
    public static void createAccount()
    {
        Action.run("CreateAccount", () ->
        {
            Account account = Context.get().data.getAccount().get();
            $("#lastName").should(visible).sendKeys(account.lastname);
            $("#firstName").should(visible).sendKeys(account.firstname);
            $("#eMail").should(visible).sendKeys(account.email);
            $("#password").should(visible).sendKeys(account.password);
            $("#passwordAgain").should(visible).sendKeys(account.password);
            
            $("#btnRegister").should(visible).click();
            
            // validate
            $("#successMessage").should(visible);
        });
    }

    public static void login()
    {
        Action.run("Login", () ->
        {
            Account account = Context.get().data.getAccount().get();

            $("#email").should(visible).sendKeys(account.email);
            $("#password").should(visible).sendKeys(account.password);
            
            $("#btnSignIn").should(visible).click();
            
            // validate
            $("#successMessage").should(visible);
        });
        
    }

    public static void logout()
    {
        Action.run("Logout", () ->
        {
            $("#showUserMenu").should(visible).hover();
            $(".goToLogout").should(visible).hover().click();
            
            // validate
            $("#successMessage").should(visible);
        });
        
    }
}
