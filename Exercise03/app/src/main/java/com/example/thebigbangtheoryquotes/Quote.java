package com.example.thebigbangtheoryquotes;

public class Quote {

    private int imageId;
    private String quote;
    private String characterName;

    Quote (int imageId, String quote, String name) {
        this.imageId = imageId;
        this.quote = quote;
        this.characterName = name;
    }

    public int getImageId(){
        return this.imageId;
    }

    public String getQuote(){
        return this.quote;
    }

    public String getCharacterName(){
        return this.characterName;
    }
}
