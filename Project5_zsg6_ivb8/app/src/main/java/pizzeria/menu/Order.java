package pizzeria.menu;

import java.util.ArrayList;

/**
 * Order holds the pizzas and phone number for one customer
 *
 * @author Zachary Goldman, Isaac Brukhman
 */

public class Order {

    private ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
    private String phoneNumber;

    /**
     * default constructor
     */
    public Order() {
        pizzas = new ArrayList<Pizza>();
        phoneNumber = "";
    }

    /**
     * parameterized constructor that only stores the phone number
     *
     * @param phoneNumber the customer's phone number
     */
    public Order(String phoneNumber) {
        this.pizzas = new ArrayList<Pizza>();
        this.phoneNumber = phoneNumber;
    }


    /**
     * Fully parameterized constructor that stores the list of pizzas and phone number
     *
     * @param pizzas      the list of pizzas
     * @param phoneNumber the customer's phone number
     */
    public Order(ArrayList<Pizza> pizzas, String phoneNumber) {
        this.pizzas = new ArrayList<Pizza>(pizzas);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Fully parameterized constructor that stores one pizza and phone number
     *
     * @param pizza       the list of pizzas
     * @param phoneNumber the customer's phone number
     */
    public Order(Pizza pizza, String phoneNumber) {
        this.pizzas.add(pizza);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Adds a pizza to the order list. Duplicates allowed
     *
     * @param pizza the pizza that will be added to the customer's order
     */
    public void add(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * removes one pizza from the list even if it occurs more than once
     *
     * @param pizza that wants to cancel the order
     */
    public void remove(Pizza pizza) {
        if (pizzas == null) {
            return;
        }
        if (pizzas.size() == 0) {
            return;
        }
        for (Pizza p : pizzas) {
            if (p.equals(pizza)) {
                pizzas.remove(pizza);
                break;
            }
        }
    }

    /**
     * removes one pizza from the list even if it occurs more than once
     *
     * @param index of the pizza that wants to cancel the order
     */
    public void remove(int index) {
        pizzas.remove(index);
    }

    /**
     * adds the price of every pizza into the total
     * @return double subtotal
     */
    public double calcSubTotal() {
        double total = 0;

        for (Pizza p : pizzas) {
            total += p.price();
        }

        return total;
    }

    /**
     * calculates the tax for the whole order
     * @return double of the tax
     */
    public double calcTax() {
        return calcSubTotal() * .06625;
    }

    /**
     * Getter for phone number
     *
     * @return phoneNumber the person's phone number
     */
    public String getNumber() {
        return phoneNumber;
    }

    /**
     * Gets the number of pizzas
     *
     * @return int the number of pizzas
     */
    public int getTotalPizzas() {
        return pizzas.size();
    }

    /**
     * Prints the orders financial information
     *
     * @return output the full order information
     */
    public String printOrder() {
        String output = toString();
        double subTotal = calcSubTotal();

        output += String.format("\n\tSubtotal: %1.2f \nTax: %1.2f \n Order Total: %1.2f \n", subTotal, subTotal * .06625, subTotal + subTotal * .06625);

        return output;
    }

    /**
     * returns a list of the pizza flavors and toppings
     *
     * @return output the Pizza type and all the toppings
     */
    public String toString() {
        String output = "Phone number: " + phoneNumber + "\nFull Order: \n";
        for (Pizza p : pizzas)
            output += "\n" + findType(p) + "\n Toppings: \n" + p.toString();
        return output;
    }

    /**
     * Overloaded toString that returns the details of a pizza at one index
     *
     * @param i the index of the pizza we want
     * @return output the specific Pizza
     */
    public String toString(int i) {
        return findType(pizzas.get(i)) + " pizza, " + pizzas.get(i).toString() + pizzas.get(i).size + ", $" + pizzas.get(i).price();
    }

    /**
     * Returns the pizza flavor
     *
     * @param p the pizza you want to find the type of
     * @return s the Pizza type
     */
    private String findType(Pizza p) {
        String s = "";
        if (p instanceof Deluxe) {
            s = "Deluxe";
        } else if (p instanceof Hawaiian) {
            s = "Hawaiian";
        } else if (p instanceof Pepperoni) {
            s = "Pepperoni";
        }
        return s;
    }

    /**
     * getPizza returns an indexed pizza from our pizzas arraylist
     * @param i index
     * @return pizzas.get(i) which is a pizza at index i from our order's arraylist
     * of pizzas
     */
    public Pizza getPizza(int i) {
        return pizzas.get(i);
    }

    /**
     * setPizzas lets us set our list of pizzas to a new arraylist of pizzas
     * @param newPizzas which is an arraylist of pizzas
     */
    public void setPizzas(ArrayList<Pizza> newPizzas) {
        pizzas = newPizzas;
    }

    /**
     * getPizzas() returns our arraylist of pizzas from order
     * @return pizzas which is what we called the arraylist of pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }
}
