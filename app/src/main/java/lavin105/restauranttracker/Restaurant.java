package lavin105.restauranttracker;

import java.io.Serializable;
import java.util.Comparator;

/*Simple Restaurant class that is used to create restaurant objects so that passing them around and accessing their
*attributes are much easier */

public class Restaurant implements Serializable{
    String name,phone,website,category, logo;
    String rating;

    public Restaurant(String name, String phone, String website, String category, String logo, String rating) {
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.category = category;
        this.logo = logo;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
