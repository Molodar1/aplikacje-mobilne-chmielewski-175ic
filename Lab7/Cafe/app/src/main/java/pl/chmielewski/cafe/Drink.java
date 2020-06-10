package pl.chmielewski.cafe;

public class Drink {
    private String name,description;
    private int imageResourceId;



    public static final Drink[] drinks={
            new Drink("Latte","Pyszne espresso z gorącym mlekiem i mleczną pianką.", R.drawable.latte),
            new Drink("Cappuccino","Czarne espresso z dużą ilością spienionego mleka.",R.drawable.cappuccino),
            new Drink("Espresso","Czarna mocna kawa ze świeżo zmielonych ziaren najwyższej jakości.",R.drawable.espresso)
    };
    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
