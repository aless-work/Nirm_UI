package webobjects.SignIn;

import framework.WebPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import webobjects.SignInPage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

// verified for v.2.0
public class ResetPasswordPage extends SignInPage {
    private String by_email = "//input[@id='email']";
    private String by_resetBtn = "//button[@action='resetPassword']";
    private String by_confirmation_text = "//*[contains(text(),'Your request has been submitted.')]";


    public ResetPasswordPage(WebDriver webDriver) {
        super(webDriver);
        assertTrue(waitAppear(by_login_title, text_resetPassword), "Timeout of pageObject " + this.getClass().getName() + " loading.");
    }

    public ResetPasswordPage enterEmail(String email) {
        updateElement(by_email, email);
        return this;
    }

    public ResetPasswordPage clickResetPasswordBtn() {
        clickElement(by_resetBtn);
        return this;
    }

    public ResetPasswordPage assertThat(boolean expectation, String message) {
        if (expectation) assertTrue(waitDisappear(by_resetBtn) && waitAppear(by_confirmation_text), message);
        else             assertFalse(waitDisappear(by_resetBtn) && waitAppear(by_confirmation_text), message);
        return this;
    }

    public boolean isResetConfirmationAppeared() {
        return waitDisappear(by_resetBtn) && waitAppear(by_confirmation_text);
    }
}
