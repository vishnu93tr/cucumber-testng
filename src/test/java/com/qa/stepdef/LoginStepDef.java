package com.qa.stepdef;

import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDef {
    @When("I enter  username as \"([^\"]*)\"$")
    public void iEnterUsernameAs(String userName) throws InterruptedException {
        new LoginPage().enterUserName(userName);

    }
    @When("I enter password as \"([^\"]*)\"$")
    public void iEnterPasswordAs(String Password) {
       new LoginPage().enterPassword(Password);
    }
    @When("I Login$")
    public void iLogin() {
        new LoginPage().pressLoginBtn();

    }
    @Then("Login should fail with an error \"([^\"]*)\"$")
    public void loginShouldFailWithAnError(String errormessage) {
        Assert.assertEquals(new LoginPage().getErrTxt(),errormessage);
    }
    @Then("I should see Products page with title \"([^\"]*)\"$")
    public void iShouldSeeProductsPageWithTitle(String product_title) {
        Assert.assertEquals(new ProductsPage().getTitle(),product_title);
    }
}
