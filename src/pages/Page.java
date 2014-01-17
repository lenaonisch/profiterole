/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author ЛЕНА
 */
public class Page {
    WebDriver driver;
    
    @FindBy(linkText="Вход")
    WebElement enterLink;
    
    @FindBy(linkText="Выход")
    WebElement closeLink;
    
    @FindBy(linkText="Регистрация")
    WebElement regLink;
    
    @FindBy(linkText="Создать рецепт")
    WebElement createRecipe;
    
    @FindBy(linkText="Меню на день")
    WebElement createDayMenu;
    
    @FindBy(linkText="Рецепты")
    WebElement recipes;
    
    @FindBy(linkText="Создать меню")
    WebElement createMenu;
    
    public Page(WebDriver driver){
        this.driver = driver;
    }
    
    public final void openSignInPage(){
        enterLink.click();
    }
    
    public final void signOut(){
        closeLink.click();
    }
    
    //комплексный метод
    public final boolean signIn(String login, String password){
        MainPage p = PageFactory.initElements(driver, MainPage.class);
        p.openSignInPage();
        SignInPage s = PageFactory.initElements(driver, SignInPage.class);
        s.login.sendKeys(login);
        s.pass.clear();
        s.pass.sendKeys(password);
        s.btn.click();
        return p.isSignedIn();
    }
    
    public final boolean isSignedIn(){
        return closeLink.isEnabled();
    }
    
    public boolean isPageOpened(){return false;}
    
    public final void openRegistrationPage(){
        regLink.click();
    }
    
    public final void openCreateRecipePage(){
        WebElement w = recipes.findElement(By.xpath(".."));
        if (!w.getAttribute("class").contains("open"))
            recipes.click();
        createRecipe.click();
    }
    
    public final void openCreateDayMenuPage(){
        WebElement w = createMenu.findElement(By.xpath(".."));
        if (!w.getAttribute("class").contains("open"))
            createMenu.click();
        createDayMenu.click();
    }
    
}
