package pizzeria.menu;

import java.util.ArrayList;

/**
 * Deluxe is a subtype of Pizza that defines the price method
 *
 * @author Zachary Goldman, Isaac Brukhman
 */
public class Deluxe extends Pizza {

    /**
     * Default constructor
     */
    public Deluxe() {
        size = Size.Small;
        addTopping(Topping.Mozzarella);
        addTopping(Topping.Mushroom);
        addTopping(Topping.Pepperoni);
        addTopping(Topping.Spinach);
        addTopping(Topping.Olives);
    }

    /**
     * fully parameterized constructor that stores the toppings and pizza size
     *
     * @param toppings the list of toppings
     * @param size     the size of the pizza
     */
    public Deluxe(ArrayList<Topping> toppings, Size size) {
        super(toppings, size);
    }

    /**
     * Calculates the price of the pizza
     *
     * @return double the price
     */
    @Override
    public double price() {
        int numToppings = toppings.size();
        if (numToppings < 5) // makes sure the price doesn't change for this pizza if toppings are taken away
            numToppings = 5;
        double price = (numToppings - 5) * 1.49;   // 5 toppings included already on pizza
        switch (size) {
            case Small:
                price += 12.99;
                break;
            case Medium:
                price += 14.99;
                break;
            case Large:
                price += 16.99;
                break;
        }
        return price;
    }
}
