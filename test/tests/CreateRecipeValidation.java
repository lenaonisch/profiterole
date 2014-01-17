/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import pages.*;

/**
 *
 * @author ЛЕНА
 */
public class CreateRecipeValidation {
    
    String text251 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque viverra sagittis nulla, ac lacinia metus rhoncus eget. Vestibulum mi elit, congue in venenatis a, laoreet eget nunc. Aliquam erat volutpat. Quisque quis magna vitae turpis volutpat.";
    WebDriver driver = new FirefoxDriver();
    String mainURL = "http://gioia-profiterole.rhcloud.com/";
    
    CreateRecipePage cr;
    
    @Before
    public void setUpBeforeTest(){
        driver.get("http://gioia-profiterole.rhcloud.com/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        MainPage m = PageFactory.initElements(driver, MainPage.class);
       
        boolean isSignedIn = m.signIn("nahrenoza", "111111");
        Assert.assertTrue("Can't sign in!", isSignedIn);
        
        //step 1
        m.openCreateRecipePage(); 
        cr = PageFactory.initElements(driver, CreateRecipePage.class);
        Assert.assertTrue("Не могу открыть страницу создания рецепта!", cr.isPageOpened());
    }
    
    @After
    public void tearDown(){
        driver.quit();
    }
    
    @Test
    public void step2(){
        cr.submit();
        Assert.assertEquals("Not enough error messages!", 9, cr.getErrorCount());
    }
    
    @Test
    public void step3(){
        cr.setNameAndDescription(text251,"");
        cr.submit();  
        int actualLen = cr.getTitle().length();
        Assert.assertEquals("Title accepts text over 250 symbols", 250, actualLen);
    }
    
    @Test
    public void step4() throws FileNotFoundException, IOException{
        InputStream is = new FileInputStream(new File("test/3001.txt"));
        
        cr.setNameAndDescription("", IOUtils.toString(is));
        cr.submit();
        
        int actualLen = cr.getDescription().length();
        Assert.assertEquals("Title accepts text over 3000 symbols", 3000, actualLen);
    }
    
    @Test
    public void step5(){
        cr.setHoursAndMinutes("10", "60");
        cr.submit();
        String expected = "Корректное значение для часов приготовления лежит в диапазоне от 0 часов до 9 часов (целые).";
        String actual = cr.getTimeError();
        Assert.assertEquals("Invalid error message!", expected, actual);
    }
    
    @Test
    public void step6(){
        cr.setHoursAndMinutes("0.5", "");
        cr.submit();      
        String expected = "Время приготовления должно быть указано." +
                    "Если не указаны часы времени приготовления, корректное значение для минут лежит в диапазоне от 6 минут до 59 минут (целые)"; 
        String actual = cr.getTimeError();
        Assert.assertEquals("Invalid error message!", expected, actual);
    }
    
     @Test
    public void step7_8(){
        if(!cr.setFirstIngridient("мука", "20", "г"))
            Assert.fail("Не удалось найти ингридиент");
        cr.setTotalWeight("100");
        cr.submit();
        
        String actual=cr.getTotalWeightError();
        String expected = "Вес готового блюда не должен быть меньше 25% и не должен превышать вес всех входящих в рецепт ингредиентов. (20 гр.)";
        Assert.assertEquals("Wrong error message after dish quantity",expected,actual);   
        
        //step 8
        if(!cr.setFirstIngridient("мука", "100", "г"))
            Assert.fail("Не удалось найти ингридиент");
        cr.setTotalWeight("20");
        cr.submit();  
        
        actual = cr.getTotalWeightError();
        expected = "Вес готового блюда не должен быть меньше 25% и не должен превышать вес всех входящих в рецепт ингредиентов. (100 гр.)";
        Assert.assertEquals("Wrong error message after dish quantity",expected,actual); 
    }
    
}