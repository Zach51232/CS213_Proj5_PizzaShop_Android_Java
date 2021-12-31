package pizzeria.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
/**
 * StoreActivity is our store order page
 * @author Zach, Isaac
 *
 */
public class StoreActivity extends AppCompatActivity {
    TextView orderTotal;
    ListView listOfPizzas;
    Spinner spinner;
    Button cancelOrder;
    StoreOrders storeOrders;
    public final int INVALID = -1;
    int spinnerPosition = INVALID;

    private ArrayList<String> customers = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);
        storeOrders = MainActivity.storeOrders;
        listOfPizzas = findViewById(R.id.pizzaList);
        spinner = findViewById(R.id.spinner);
        orderTotal = findViewById(R.id.totalAmount);
        cancelOrder = findViewById(R.id.cancelBtn);
        setDefaultListView();
        setUpSpinner();
        setUpOrderTotal();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position;
                String numb = spinner.getSelectedItem().toString();
                resetListViews(numb);
                resetTotalPrice(numb);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelOrderButtonClick();
            }
        });
    }

    /**
     * Sets the spinner list to the phone numbers currently held in orders for storeOrders
     */
    void setUpSpinner() {
        if (storeOrders.getTotalOrders() == 0) {
            ArrayList<String> empt = new ArrayList<String>();
            empt.add("");
            ArrayAdapter<String> phoneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, empt);
            phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(phoneAdapter);
            spinnerPosition = INVALID;
            return;
        }

        //Sets up the phone numbers in the spinner
        for (int i = 0; i < storeOrders.getTotalOrders(); i++) {
            customers.add(storeOrders.getOrders().get(i).getNumber());
        }
        ArrayAdapter<String> phoneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, customers);
        phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(phoneAdapter);

        //Defaults the most recent phone number
        int numOrders = storeOrders.getTotalOrders();
        spinnerPosition = numOrders - 1;
        spinner.setSelection(spinnerPosition);
    }

    /**
     * Gets the order
     *
     * @param phoneN the phone number
     * @return order
     */
    Order findOrder(String phoneN) {
        Order o = new Order();
        for (int i = 0; i < storeOrders.getTotalOrders(); i++) {
            if (storeOrders.getOrders().get(i).getNumber().equals(phoneN)) {
                o = storeOrders.getOrders().get(i);
                break;
            }
        }
        return o;
    }

    /**
     * cancels order
     */
    void onCancelOrderButtonClick() {
        String phoneN = (String) spinner.getSelectedItem();
        if (phoneN.equals("")) {
            return;
        }
        if (spinnerPosition == INVALID) {
            Toast.makeText(this, R.string.noOrderSelected, Toast.LENGTH_SHORT).show();
            return;
        }
        Order o = findOrder(phoneN);
        storeOrders.remove(o);
        customers = null;
        customers = new ArrayList<String>();
        setUpSpinner();
        String newNum = (String) spinner.getItemAtPosition(spinnerPosition);
        resetListViews(newNum);
        resetTotalPrice(newNum);
        Toast.makeText(this, R.string.orderCanceled, Toast.LENGTH_SHORT).show();
    }

    /**
     * resets total price
     *
     * @param phoneN cust phone num
     */
    void resetTotalPrice(String phoneN) {
        Order o = findOrder(phoneN);
        double newSum = o.calcTax() + o.calcSubTotal();
        String s = String.format("%1.2f", newSum);
        orderTotal.setText(s);
    }

    /**
     * After changing the selected order, resetListViews() makes the graphical interface look correct
     * by changing the adapters
     */
    void resetListViews(String phoneNumber) {
        if (storeOrders.getTotalOrders() == 0) {
            listOfPizzas.setAdapter(null);
            return;
        }
        ArrayList<String> pizzaList = new ArrayList<String>();
        for (int i = 0; i < storeOrders.getTotalOrders(); i++) {
            if (storeOrders.getOrders().get(i).getNumber().equals(phoneNumber)) {
                pizzaList.add(storeOrders.getOrders().get(i).toString());
            }
        }
        ArrayAdapter<String> orders = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaList);
        listOfPizzas.setAdapter(orders);
    }

    /**
     * setting default list view
     */
    void setDefaultListView() {
        setListView(storeOrders.getOrders().get(storeOrders.getTotalOrders() - 1).getNumber());
    }

    /**
     * setListViews() sets up the list view by filling it with the orders in
     * the user's current order
     *
     * @param number takes in photo number
     */
    public void setListView(String number) {
        Order orderToDisplay = new Order();
        for (int i = 0; i < storeOrders.getTotalOrders(); i++) {
            if (storeOrders.getOrders().get(i).getNumber().equals(number)) {
                orderToDisplay = storeOrders.getOrders().get(i);
                break;
            }
        }
        ArrayList<String> output = new ArrayList<String>();
        for (int i = 0; i < orderToDisplay.getTotalPizzas(); i++) {
            output.add(orderToDisplay.toString(i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, output);
        listOfPizzas.setAdapter(arrayAdapter);
    }

    /**
     * setting the order total
     */
    public void setUpOrderTotal() {
        Order a = storeOrders.getOrders().get(storeOrders.getTotalOrders() - 1);
        double firstSum = a.calcTax() + a.calcSubTotal();
        String s = String.format("%1.2f", firstSum);
        orderTotal.setText(s);
    }

}
