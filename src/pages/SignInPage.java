/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author ЛЕНА
 */
public class SignInPage extends Page{
    @FindBy(tagName="h2")
    WebElement title;
    
    @FindBy(id="j_username")
    WebElement login;
    
    @FindBy(id="j_password")
    WebElement pass;
    
    @FindBy(tagName="button")
    WebElement btn;
    
    public SignInPage(WebDriver driver){
        super(driver);
    }
    
    @Override
    public boolean isPageOpened() {
        if (title.isEnabled())
            return title.getText().equals("Вход");
        return false;
    }
    
    public void enter(String login, String password){
       this.login.clear();
       this.login.sendKeys(login);
       this.pass.clear();
       this.pass.sendKeys(password);
       btn.click();
    }
    
}
