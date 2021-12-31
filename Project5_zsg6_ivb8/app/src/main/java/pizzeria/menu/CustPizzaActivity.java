package pizzeria.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * CustPizzaActivity is our customization order page
 * @author Zach, Isaac
 *
 */
public class CustPizzaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    public static final int INVALID = 10;
    Pizza newPizza;
    private int numOrders = 0;
    private ArrayList<Pizza> myPizzas = new ArrayList<Pizza>();
    int removingPosition = INVALID;
    int addingPosition = INVALID;
    final static String NOTSET = "0";
    static String phoneNumber = NOTSET;
    ImageView imgv;
    TextView t2, t3;
    Spinner spin;
    String type;
    ListView list1, list2;
    Button add, remove;
    Size currentSize = Size.Small;
    ArrayList<String> selecteds = new ArrayList<String>();
    ArrayList<String> additionals = new ArrayList<String>();
    ArrayList<String> currentSelecteds = new ArrayList<String>();
    ArrayList<String> currentAdditionals = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custpizza_activity);

        phoneNumber = MainActivity.phoneNumber;

        spin = findViewById(R.id.spinSizes);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        list1 = findViewById(R.id.list1);
        list2 = findViewById(R.id.list2);
        add = findViewById(R.id.addButton);
        remove = findViewById(R.id.deleteButton);
        Intent intent = getIntent();

        setUpPhotoAndPizza(intent);
        setUpLists();
        setUpSpinner();
    }

    /**
     * This sets the photo to the selected photo and the pizza is created
     * that we'll use throughout this class
     *
     * @param a which is the intent
     */
    void setUpPhotoAndPizza(Intent a) {
        int typeOfPizza = a.getIntExtra("Key", INVALID);
        imgv = findViewById(R.id.imgview);
        if (typeOfPizza == 0) {
            type = "Deluxe";
            imgv.setImageResource(R.drawable.deluxe);
        } else if (typeOfPizza == 1) {
            type = "Hawaiian";
            imgv.setImageResource(R.drawable.hawaiian);
        } else if (typeOfPizza == 2) {
            type = "Pepperoni";
            imgv.setImageResource(R.drawable.pepperoni);
        }
        if (typeOfPizza == INVALID) {
            return;
        }
        newPizza = PizzaMaker.createPizza(type);
        t2.setText(type);
    }

    /**
     * setUpSpinner() sets the spinner with its sizes, and default selects small
     */
    void setUpSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        newPizza.size = Size.Small;
        spin.setOnItemSelectedListener(this);
    }

    /**
     * onItemSelected changes the price and size of our pizza when the size is changed
     *
     * @param parent   the parent
     * @param view     the view
     * @param position the position
     * @param id       the id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String size = parent.getItemAtPosition(position).toString();
        Size newSize = sizeToEnum(size);
        newPizza.size = newSize;
        currentSize = newSize;
        changePrice();
    }

    /**
     * This is a carryover method we don't use
     *
     * @param parent is the parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * sizeToEnum takes in a string version of a size enum then returns
     * the correct enum back.
     *
     * @param size of it
     * @return size thing
     */
    public Size sizeToEnum(String size) {
        if (size.equals("Small")) {
            return Size.Small;
        }
        if (size.equals("Medium")) {
            return Size.Medium;
        } else return Size.Large;
    }

    /**
     * changePrice() changes the displayed price based on our newPizza instance variable
     * and its settings
     */
    void changePrice() {
        Double price = 0.0;
        price = newPizza.price();
        DecimalFormat f = new DecimalFormat("##.00");
        String priceToSet = f.format(price);
        t3.setText(priceToSet);
    }

    /**
     * setUpLists() sets up the options of toppings based on what type of pizza it is
     * Uses type as either deluxe,hawaiian, or pepperoni
     */
    void setUpLists() {
        list1.setOnItemClickListener(this);
        list2.setOnItemClickListener(this);
        String[] select, additional;
        if (type == "Deluxe") {
            select = new String[5];
            additional = new String[2];
            select = getResources().getStringArray(R.array.deluxetoppings);
            ArrayAdapter<String> deluxeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, select);
            additional = getResources().getStringArray(R.array.hawaiiantoppings);
            ArrayAdapter<String> minusDeluxeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, additional);
            setAdapters(deluxeAdapter, minusDeluxeAdapter);
        } else if (type == "Hawaiian") {
            select = new String[2];
            additional = new String[5];
            select = getResources().getStringArray(R.array.hawaiiantoppings);
            ArrayAdapter<String> hawaiianAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, select);
            additional = getResources().getStringArray(R.array.deluxetoppings);
            ArrayAdapter<String> minusHawaiianAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, additional);
            setAdapters(hawaiianAdapter, minusHawaiianAdapter);
        } else {
            select = new String[1];
            additional = new String[6];
            select = getResources().getStringArray(R.array.pepperonitoppings);
            ArrayAdapter<String> pepperoniAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, select);
            additional = getResources().getStringArray(R.array.alltoppingsminuspepperoni);
            ArrayAdapter<String> minusPepperoniAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, additional);
            setAdapters(pepperoniAdapter, minusPepperoniAdapter);
        }
        copyToArrayLists(select, additional);
    }

    /**
     * To save space in the method above, this sets the list adapters to the correct topping-filled arrayadapters
     *
     * @param a which is adapter 1
     * @param b which is adapter 2
     */
    void setAdapters(ArrayAdapter<String> a, ArrayAdapter<String> b) {
        list1.setAdapter(a);
        list2.setAdapter(b);
    }

    /**
     * This stores the relevant toppings in instance variables we can use when we add or remove a topping
     * and store left over for when we make the order finally
     *
     * @param select     the string array
     * @param additional the string array
     */
    void copyToArrayLists(String[] select, String[] additional) {
        for (int i = 0; i < select.length; i++) {
            currentSelecteds.add(select[i]);
            selecteds.add(select[i]);
        }
        for (int i = 0; i < additional.length; i++) {
            currentAdditionals.add(additional[i]);
            additionals.add(additional[i]);
        }
    }

    /**
     * onItemClick is to store when we click on a listview to put
     * removingPosition or addingPosition stored
     * so we can choose that specific item when we click to add or remove
     *
     * @param parent   the parent
     * @param view     the view
     * @param position the position
     * @param id       the id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.list1:
                removingPosition = position;
                break;
            case R.id.list2:
                addingPosition = position;
                break;
        }
    }


    /**
     * onAddButtonClick() adds a new topping to our newPizza then changes the display
     * and moves toppings added to the selected toppings listview
     */
    public void onAddButtonClick(View v) {
        if (INVALID == addingPosition) {
            return;
        }
        String newTopping = (String) list2.getItemAtPosition(addingPosition);
        currentSelecteds.add(newTopping);
        currentAdditionals.remove(newTopping);
        Topping addedTopping = convertToppingToEnum(newTopping);
        newPizza.addTopping(addedTopping);
        changePrice();
        if (currentAdditionals.size() == 0) {
            addingPosition = INVALID;
        } else if (addingPosition >= currentAdditionals.size()) {
            addingPosition = addingPosition - 1;
        }
        resetListViews();
    }

    /**
     * onRemoveButtonClick() removes a  topping from our newPizza then changes the display
     * and moves toppings removed to the optional toppings listview
     */
    public void onRemoveButtonClick(View v) {
        if (INVALID == removingPosition) {
            return;
        }
        String newTopping = (String) list1.getItemAtPosition(removingPosition);
        currentSelecteds.remove(newTopping);
        currentAdditionals.add(newTopping);
        Topping addedTopping = convertToppingToEnum(newTopping);
        newPizza.removeTopping(addedTopping);
        changePrice();
        if (currentSelecteds.size() == 0) {
            removingPosition = INVALID;
        } else if (removingPosition >= currentSelecteds.size()) {
            removingPosition = removingPosition - 1;
        }
        resetListViews();
    }

    /**
     * convertToppingToEnum takes in a string and converts it to the enum version similar
     * to the sizeToEnum method
     *
     * @param topping of the pizza
     * @return newTopping in Enum form
     */
    public Topping convertToppingToEnum(String topping) {
        Topping newTopping = Topping.Pepperoni;
        switch (topping) {
            case "Pepperoni":
                newTopping = Topping.Pepperoni;
                break;
            case "Mushroom":
                newTopping = Topping.Mushroom;
                break;
            case "Pineapple":
                newTopping = Topping.Pineapple;
                break;
            case "Mozzarella":
                newTopping = Topping.Mozzarella;
                break;
            case "Olives":
                newTopping = Topping.Olives;
                break;
            case "Spinach":
                newTopping = Topping.Spinach;
                break;
            case "Ham":
                newTopping = Topping.Ham;
                break;
        }
        return newTopping;
    }

    /**
     * After adding, resetListViews() makes the graphical interface look correct
     * by changing the adapters
     */
    void resetListViews() {
        ArrayAdapter<String> selected = new ArrayAdapter(this, android.R.layout.simple_list_item_1, currentSelecteds);
        ArrayAdapter<String> additions = new ArrayAdapter(this, android.R.layout.simple_list_item_1, currentAdditionals);
        list1.setAdapter(selected);
        list2.setAdapter(additions);
    }

    /**
     * convertAllToppingsToEnums takes in a ListView of our selected toppings
     * and returns an arraylist of toppings to put in our pizza we'll add to
     * the current order by calling convertToppingToEnum for each topping
     * <p>
     * Uses the selectedToppings I stored
     *
     * @return toppings
     */
    public ArrayList<Topping> convertAllToppingsToEnums() {
        ArrayList<Topping> toppings = new ArrayList<Topping>();
        for (int i = 0; i < currentSelecteds.size(); i++) {
            Topping enumTopping = convertToppingToEnum(currentSelecteds.get(i));
            toppings.add(enumTopping);
        }
        return toppings;
    }

    /**
     * onAddToOrderButtonClick() takes our order if we have it, then adds
     * the pizza we're dealing with to that order based on our selections.
     * Then it sends that order to the HelloController to use.
     *
     * @throws IOException if bad load
     */

    public void onAddToOrderButtonClick(View v) throws IOException {
        //If this is our first order from this view
        if (numOrders == 0) {
            //If this is not the first order for the phone number
            if (MainActivity.currentOrder != null) {
                myPizzas = MainActivity.currentOrder.getPizzas();
            }
        }
        Pizza a = PizzaMaker.createPizza(type);
        a.size = currentSize;
        a.toppings = convertAllToppingsToEnums();
        myPizzas.add(a);
        Order newOrder = new Order(myPizzas, phoneNumber);
        numOrders++;
        MainActivity.currentOrder = newOrder;
        CurrentActivity.phoneNumber = MainActivity.phoneNumber;
        Toast.makeText(this, newOrder.toString(), Toast.LENGTH_SHORT).show();
    }
}


