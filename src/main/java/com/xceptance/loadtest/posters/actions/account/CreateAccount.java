package com.xceptance.loadtest.posters.actions.account;

import org.junit.Assert;

import com.xceptance.loadtest.api.actions.PageAction;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.validators.Validator;
import com.xceptance.loadtest.posters.models.pages.account.CreateAccountPage;
import com.xceptance.loadtest.posters.models.pages.account.LoginPage;

public class CreateAccount extends PageAction<CreateAccount>
{
    private final Account account;

    public CreateAccount(final Account account)
    {
        this.account = account;
    }

    @Override
    protected void doExecute() throws Exception
    {
        CreateAccountPage.instance.createAccountCard.fillCreateAccountForm(account);
       
        loadPageByClick(CreateAccountPage.instance.createAccountCard.getCreateAccountButton());
    }

    @Override
    protected void postValidate() throws Exception
    {
        Validator.validatePageSource();

        Assert.assertTrue("Failed to register. Expected login form.", LoginPage.instance.loginCard.exists());        

        account.isRegistered = true;
    }
}