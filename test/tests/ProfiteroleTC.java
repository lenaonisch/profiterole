/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import pages.*;

/**
 *
 * @author ЛЕНА
 */
public class ProfiteroleTC {
    
    String text251 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque viverra sagittis nulla, ac lacinia metus rhoncus eget. Vestibulum mi elit, congue in venenatis a, laoreet eget nunc. Aliquam erat volutpat. Quisque quis magna vitae turpis volutpat.";
    
    WebDriver driver;
    String mainURL = "http://gioia-profiterole.rhcloud.com/";

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(mainURL);
    }
    @After
    public void tearDown(){
        driver.quit();
    }
    
    @Test
    public void signInTest(){
        MainPage p = PageFactory.initElements(driver, MainPage.class);
        p.openSignInPage();
        SignInPage s = PageFactory.initElements(driver, SignInPage.class);
        Assert.assertTrue(s.isPageOpened());
        s.enter("nahrenoza", "111111");
        Assert.assertTrue(s.isSignedIn());
        p = PageFactory.initElements(driver, MainPage.class);
        Assert.assertTrue(p.isPageOpened());
    }
    
    @Test
    public void createBreakfast(){
        Page p = PageFactory.initElements(driver, Page.class);
        Assert.assertTrue("Didn't sign in!", p.signIn("nahrenoza", "111111"));
        p.openCreateDayMenuPage();
        CreateDayMenuPage dm = PageFactory.initElements(driver, CreateDayMenuPage.class);
        Assert.assertTrue("Didn't open Create Day Menu Page", dm.isPageOpened());
        
        //step 1
        dm.showBreakfast();

        //step 2
        dm.selectCuisine(Cuisine.Украинская);
        List<String> tabs = dm.getTypesOfDishes();
        List<TypeOfDish> types = Arrays.asList(TypeOfDish.values());
        for(int i=0;i<tabs.size();i++){
            org.testng.Assert.assertEquals(tabs.get(i), types.get(i).name(), i+" tab doesn't match");
        }
        
        //step 3-4
        String recipeTitle = dm.getRecipeTitle(0);
        try {
            //добавили рецепт
            dm.addRecipe(0);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
        //ищем его в Ваш завтрак
        String actualTitle = dm.addedRecipeTitle(0);
        org.testng.Assert.assertEquals(actualTitle, recipeTitle);
    }
}