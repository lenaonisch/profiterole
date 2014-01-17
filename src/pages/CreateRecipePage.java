/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author ЛЕНА
 */
public class CreateRecipePage extends Page{
    @FindBy(tagName="h1")
    WebElement title;
    
    @FindBy(id="OK")
    WebElement btnCreate;
    
    @FindBy(className="error-div")
    List<WebElement> errors;
    
    @FindBy(name="title")
    WebElement name;
    
    @FindBy(name="description")
    WebElement description;
    
    @FindBy(name="cookingTimeHours")
    WebElement hours;
    
    @FindBy(name="cookingTimeMinutes")
    WebElement minutes;
    
    @FindBy(xpath="//*[@name='cookingTimeMinutes']/../following-sibling::*[1]/span")
    List<WebElement> timeError; //list of <span>
    
    @FindBy(xpath="//input[@id='ingredientsCountList[0]']")
    WebElement ingridientWeight;
    
    @FindBy(xpath="//select[@id='ingredientsTypeList[0]']")
    WebElement weightMeasure;
    
    @FindBy(xpath="//input[@id='ingredientsNameList[0]']")
    WebElement ingridientName;
    
    @FindBy(xpath="//li[@class='ui-menu-item']/a")
    List<WebElement> foundIngridients; 
    
    @FindBy(xpath="//*[text()='Вес готового блюда']/following-sibling::*")
    WebElement totalWeight;
    
    @FindBy(xpath="//*[@name='quantityOfDish']/../following-sibling::*[1]/span")
    WebElement totalWeightError;
    
    public CreateRecipePage(WebDriver driver){
        super(driver);
    }
    
    @Override
    public boolean isPageOpened() {
        if (title.isEnabled())
            return title.getText().equals("Создать рецепт");
        return false;
    }
    
    public void submit(){
        btnCreate.click();
    }
    
    public void setNameAndDescription(String name, String description){
        this.name.clear();
        this.name.sendKeys(name);
        this.description.clear();
        this.description.sendKeys(description);
    }
    
    public int getErrorCount(){
        return errors.size();
    }
    
    public String getTitle(){
        return name.getAttribute("value");
    }
    
    public String getDescription(){
        return description.getText();
    }
    
    public void setHoursAndMinutes(String hours, String minutes){
        this.hours.clear();
        this.hours.sendKeys( hours);
        this.minutes.clear();
        this.minutes.sendKeys(minutes);
    }
    
    public String getTimeError(){
        String str="";
        for(WebElement e:timeError)
            str+=e.getText();
        return str;
    }
    
    public boolean setFirstIngridient(String name, String weight, String measure){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        boolean nameFound = false, measureFound = false;
        ingridientWeight.clear();
        ingridientName.clear();
        //находим название ингридиента
        for (int i=0;i<name.length()&&!nameFound;i++){
            ingridientName.sendKeys(""+name.charAt(i));
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='ui-menu-item']/a")));
            for(WebElement w: foundIngridients){
                if (w.getText().contains(name)){
                    w.click();
                    nameFound = true;
                }
            }
        }
        if (!nameFound) return false;
        //выбираем единицу измерения
        weightMeasure.click(); //нажать чтобы подгрузились значения
        Select select = new Select(weightMeasure);
        select.selectByVisibleText(measure);
        measureFound = (select.getFirstSelectedOption()!=null);
        //устанавливаем количество
        ingridientWeight.sendKeys(weight);
        
        return measureFound;
    }
    
    public void setTotalWeight(String weight){
        totalWeight.clear();
        totalWeight.sendKeys(weight);
    }
    
    public String getTotalWeightError(){
        return totalWeightError.getText();
    }
}
