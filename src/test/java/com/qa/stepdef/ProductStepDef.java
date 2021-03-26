package com.qa.stepdef;

import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class ProductStepDef {
    @Given("I'm Logged in")
    public void iMLoggedIn() throws InterruptedException {
        new LoginPage().login("standard_user","secret_sauce");
    }
    @When("I click product title \"([^\"]*)\"$")
    public void iClickProductTitle(String title) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        new ProductsPage().pressProductTitle(title);
    }
    @Then("Product is listed with title \"([^\"]*)\" and price \"([^\"]*)\"$")
    public void productIsListedWithTitleAndPrice(String title, String price) throws Exception {
        boolean check_title=new ProductsPage().getProductTitle(title).equalsIgnoreCase(title);
        boolean check_price=new ProductsPage().getProductPrice(title,price).equalsIgnoreCase(price);
        Assert.assertTrue(check_title & check_price);
    }
    @Then("I should be on Product Details Page with title \"([^\"]*)\", price \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void iShouldBeOnProductDetailsPageWithTitlePriceAndDescription(String title, String price, String description) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        ProductDetailsPage productDetailsPage=new ProductDetailsPage();
        boolean check_title=productDetailsPage.getTitle().equalsIgnoreCase(title);
        boolean check_price=productDetailsPage.getPrice().equalsIgnoreCase(price);
        boolean check_description=productDetailsPage.getDesc().equalsIgnoreCase(description);

        Assert.assertTrue(check_title & check_price &check_description);
    }
}
