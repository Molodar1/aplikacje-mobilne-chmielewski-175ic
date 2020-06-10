package pl.chmielewski.cafe;

public class Cake {
    private String name,description;
    private int imageResourceId;

    public static final Cake[] cakes={
        new Cake("Szarlotka","Pyszna gorąca szarlotka", R.drawable.szarlotka),
        new Cake("Sernik","Pyszny chłodny sernik",R.drawable.sernik),
        new Cake("Makowiec","Pyszny makowiec",R.drawable.makowiec)
    };
    private Cake(String name, String description, int imageResourceId) {
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
