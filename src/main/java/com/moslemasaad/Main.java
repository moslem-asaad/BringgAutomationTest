package com.moslemasaad;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    private static final String baseURL = "https://demoqa.com/webtables";
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get(baseURL);

        String pageTitle = driver.getTitle();

        driver.quit();
    }
}