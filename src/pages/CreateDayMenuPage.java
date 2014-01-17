/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

/**
 *
 * @author ЛЕНА
 */
public class CreateDayMenuPage extends Page{
    @FindBy(xpath="//button[text()='Завтрак']")
    WebElement breakfast;
    
    @FindBy(tagName="h1")
    List<WebElement> titles;
    
    @FindBy(xpath="//div[@class='btn']")
    List<WebElement> cuisines; //кухни
    
    @FindBys({ @FindBy(id="myTab"), @FindBy(xpath="./li") })
    List<WebElement> dishTypes;
    
    @FindBy(xpath="//*[contains(@class,'draggable')]")
    List<WebElement> dishes;
    
    @FindBy(xpath="//button[@class='btn btn-success'][last()]")
    WebElement addBtnInModal;
    
    @FindBy(id="myModal")
    WebElement recipeModal;
    
    @FindBys({ @FindBy(id="menuDrop"), @FindBy(xpath="//div/label/../..") })
    List<WebElement> addedDishes;
    
    public CreateDayMenuPage(WebDriver driver){
        super(driver);
    }
    
    @Override
    public boolean isPageOpened(){
        return(titles.size()==4&&
                breakfast.isDisplayed());
    }
    
    public void showBreakfast(){
        breakfast.click();
    }
    
    public void selectCuisine(Cuisine cuisine){
        cuisines.get(cuisine.ordinal()).click();
    }
    
    public List<String> getTypesOfDishes(){
        List<String> result = new ArrayList<>();
        for(WebElement w:dishTypes)
            result.add(w.getText());
        return result;
    }
    
    public String getRecipeTitle(int index){
        return findRecipe(index).findElement(By.xpath(".//label")).getText();
    }
    
    private WebElement findRecipe(int index){
        int i=0;
        for(WebElement w:dishes){
            if (w.isDisplayed()){
                if (i==index){
                    return w;
                }
                i++;
            }        
        }
        throw new NoSuchElementException("Can't find recipe with such index!");
    }
    
    public void addRecipe(int index) throws Exception{
        findRecipe(index).click();
        if (recipeModal.isDisplayed())
            addBtnInModal.click();
        else throw new Exception("Modal window didn't open!");
    }
    
    public String addedRecipeTitle(int index){
        return addedDishes.get(index).findElement(By.xpath("//label")) .getText();
    }
}
