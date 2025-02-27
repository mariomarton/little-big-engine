package com.mario.LittleBigEngine.frontend;

/**
 * ENUM of options you can choose in the menu
 * @author mario
 */
public enum OPTION {
    
    /**
     * DEMO level option
     */
    DEMO("file:src/main/java/com/mario/LittleBigEngine/resources/option_demo.png"),

    /**
     * CUSTOM level option
     */
    CUSTOM("file:src/main/java/com/mario/LittleBigEngine/resources/option_custom.png");
    
    private final String urlOption;
    
    private OPTION(String urlOption){
        this.urlOption = urlOption;
    }
    
    /**
     * returns the image of the option
     * @return
     */
    public String getUrl(){
        return urlOption;
    }
}
