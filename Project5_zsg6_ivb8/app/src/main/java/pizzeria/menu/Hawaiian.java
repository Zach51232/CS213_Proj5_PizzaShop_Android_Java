package pizzeria.menu;

import java.util.ArrayList;

/**
 * Hawaiian is a subtype of Pizza that defines the price method
 *
 * @author Zachary Goldman, Isaac Brukhman
 */
public class Hawaiian extends Pizza {

    /**
     * Default constructor
     */
    public Hawaiian() {
        super();
        super.addTopping(Topping.Pineapple);
        super.addTopping(Topping.Ham);
    }

    /**
     * fully parameterized constructor that stores the toppings and pizza size
     *
     * @param toppings the list of toppings
     * @param size     the size of the pizza
     */
    public Hawaiian(ArrayList<Topping> toppings, Size size) {
        super(toppings, size);
    }

    /**
     * price()
     * Calculates the price of the pizza
     * @return double the price
     */
    @Override
    public double price() {
        int numToppings = toppings.size();

        if (numToppings < 2) // makes sure the price doesn't change for this pizza if toppings are taken away
            numToppings = 2;

        double price = (numToppings - 2) * 1.49;    // 2 toppings included already on pizza

        switch (size) {
            case Small:
                price += 10.99;
                break;
            case Medium:
                price += 12.99;
                break;
            case Large:
                price += 14.99;
                break;
        }

        return price;
    }
}
