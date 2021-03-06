package com.retropoktan.lshousekeeping.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table SUPER_ITEM.
 */
public class SuperItem {

    /** Not-null value. */
    private String SuperItemId;
    /** Not-null value. */
    private String SuperItemName;
    private String SuperItemImageUrl;

    public SuperItem() {
    }

    public SuperItem(String SuperItemId) {
        this.SuperItemId = SuperItemId;
    }

    public SuperItem(String SuperItemId, String SuperItemName, String SuperItemImageUrl) {
        this.SuperItemId = SuperItemId;
        this.SuperItemName = SuperItemName;
        this.SuperItemImageUrl = SuperItemImageUrl;
    }

    /** Not-null value. */
    public String getSuperItemId() {
        return SuperItemId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSuperItemId(String SuperItemId) {
        this.SuperItemId = SuperItemId;
    }

    /** Not-null value. */
    public String getSuperItemName() {
        return SuperItemName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSuperItemName(String SuperItemName) {
        this.SuperItemName = SuperItemName;
    }

    public String getSuperItemImageUrl() {
        return SuperItemImageUrl;
    }

    public void setSuperItemImageUrl(String SuperItemImageUrl) {
        this.SuperItemImageUrl = SuperItemImageUrl;
    }

}
