package com.lex.com.lex.kickscrape;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PageScraperTest {
	

    @Test
    public void testSucceededProject() throws IOException {
    	Project p = PageScraper.getData("https://www.kickstarter.com/projects/1173449368/lord-cirenegs-city-facades/");
    	assertEquals("https://www.kickstarter.com/projects/1173449368/lord-cirenegs-city-facades/,true,NZ$ 100,NZ$ 20.118,Jan 25. 2021 - Feb 16. 2021,21,Hastings. NZ,false,574,56,7,2,0",
    			p.toString());
    }
    
    @Test
    public void testFailedProject() throws IOException {
    	Project p = PageScraper.getData("https://www.kickstarter.com/projects/jackbrainy/jack-brainy-el-perrito-viajero/");
    	assertEquals("https://www.kickstarter.com/projects/jackbrainy/jack-brainy-el-perrito-viajero/,false,MXN 25000.0,MXN 4568.8,Jan 12. 2021 - Feb 15. 2021,33,Mexico City. Mexico,false,19,0,0,5,3",
    			p.toString());
    }

}
