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
public class MainPage extends Page{
    @FindBy(tagName="h2")
    WebElement title;
    
    public MainPage(WebDriver driver){
        super(driver);
    }
    
    @Override
    public boolean isPageOpened() {
        if (title.isEnabled())
            return title.getText().equals("Добро пожаловать на сайт");
        return false;
    }  
    
}
