package pizzeria.menu;

/**
 * PizzaMaker will create a pizza based off the type of flavor.
 *
 * @author Zachary Goldman, Isaac Brukhman
 */
public class PizzaMaker {

    /**
     * creates an instance of a pizza according to the type of pizza selected. Method assumes the flavor selected is always a correct value.
     *
     * @param flavor the type of pizza that is desired
     * @return Pizza the pizza type that was created
     */
    public static Pizza createPizza(String flavor) {
        switch (flavor) {
            case "Pepperoni":
                return new Pepperoni();
            case "Deluxe":
                return new Deluxe();
            case "Hawaiian":
                return new Hawaiian();
        }
        return null;
    }
}