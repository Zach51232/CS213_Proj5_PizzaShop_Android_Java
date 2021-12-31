package pizzeria.menu;

import java.util.ArrayList;

/**
 * Pepperoni is a subtype of Pizza that defines the price method
 *
 * @author Zachary Goldman, Isaac Brukhman
 */
public class Pepperoni extends Pizza {

    /**
     * Default constructor
     */
    public Pepperoni() {
        super();
        super.addTopping(Topping.Pepperoni);
    }

    /**
     * fully parameterized constructor that stores the toppings and pizza size
     *
     * @param toppings the list of toppings
     * @param size     the size of the pizza
     */
    public Pepperoni(ArrayList<Topping> toppings, Size size) {
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

        if (numToppings < 1) // makes sure the price doesn't change for this pizza if toppings are taken away
            numToppings = 1;

        double price = (numToppings - 1) * 1.49;    // 1 topping included already pizza

        switch (size) {
            case Small:
                price += 8.99;
                break;
            case Medium:
                price += 10.99;
                break;
            case Large:
                price += 12.99;
                break;
        }

        return price;
    }


}
