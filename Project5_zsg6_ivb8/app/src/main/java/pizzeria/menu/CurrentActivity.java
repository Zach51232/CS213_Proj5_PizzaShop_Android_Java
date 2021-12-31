package pizzeria.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * CurrentActivity is our current order page
 * @author Zach, Isaac
 *
 */
public class CurrentActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;
    Button remove, place;
    private int pos = -1;
    private double subTotal, tax, total;
    private int SUCCEED = -5;
    private static int FAIL = -10;
    public static String phoneNumber;
    public static final String NOTSET = "0";
    public static final int RESET = -1;

    /**
     * creates the current order page
     *
     * @param savedInstanceState the instance state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_activity);
        setUpIDs();
        if (!CustPizzaActivity.phoneNumber.equals(NOTSET)) {
            textView.setText(CustPizzaActivity.phoneNumber);
        }
        setPrices();
        setListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPos(position);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = removeCheck();
                if (check == SUCCEED) {
                    setPrices();
                }
            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.currentOrder == null || total == 0) {
                    noOrder();
                    return;
                }
                if (MainActivity.storeOrders == null) {
                    MainActivity.storeOrders = new StoreOrders();
                }
                MainActivity.storeOrders.add(MainActivity.currentOrder);
                resetAndMessage();
            }
        });
    }

    /**
     * Sets up most of the connections of IDs to instance variables
     */
    void setUpIDs() {
        textView = findViewById(R.id.custNumber);
        listView = findViewById(R.id.pizzaList);
        remove = findViewById(R.id.cancelBtn);
        place = findViewById(R.id.button2);
    }

    /**
     * Message display if there is no current order to send to store orders
     */
    void noOrder() {
        Toast.makeText(this, R.string.cantaddtoorder, Toast.LENGTH_SHORT).show();
    }

    /**
     * Comes from if the order is placed successfully
     */
    void resetAndMessage() {
        Toast.makeText(this, R.string.addedtoorder, Toast.LENGTH_SHORT).show();
        MainActivity.currentOrder = null;
        MainActivity.phoneNumber = "";
        CustPizzaActivity.phoneNumber = NOTSET;
        setPos(RESET);
        setListView();
        setPrices();
    }

    /**
     * sets the prices and updates the page with the new prices
     */
    void setPrices() {
        if (MainActivity.currentOrder == null) {
            subTotal = 0;
            tax = 0;
            total = 0;
        } else {
            subTotal = MainActivity.currentOrder.calcSubTotal();
            tax = MainActivity.currentOrder.calcTax();
            total = subTotal + tax;
        }
        textView = findViewById(R.id.subtotalAmount);
        textView.setText(String.format("%1.2f", subTotal));

        textView = findViewById(R.id.taxAmount);
        textView.setText(String.format("%1.2f", tax));

        textView = findViewById(R.id.totalAmount);
        textView.setText(String.format("%1.2f", total));
    }

    /**
     * Setter for the position that was last clicked in the list
     *
     * @param i index on the list that was clicked last
     */
    void setPos(int i) {
        pos = i;
    }

    /**
     * checks if a pizza is selected. Updates all values if removed. You can just remove any
     */
    int removeCheck() {
        if (pos != RESET && pos < MainActivity.currentOrder.getTotalPizzas()) {
            if (MainActivity.currentOrder.getTotalPizzas() != 0) {
                MainActivity.currentOrder.remove(pos);
                setListView();
                pos--;
                return SUCCEED;
            } else {
                String noMorePizzasMsg = getString(R.string.noMorePizzas);
                Toast.makeText(this, noMorePizzasMsg, Toast.LENGTH_LONG).show();
                return FAIL;
            }
        } else {
            String selectPizzaMsg = getString(R.string.selectPizzaRemove);
            Toast.makeText(this, selectPizzaMsg, Toast.LENGTH_LONG).show();
            return FAIL;
        }
    }

    /**
     * Will set the list based on the currentOrder
     */
    void setListView() {
        ArrayList<String> s = new ArrayList<>();
        if (!(MainActivity.currentOrder == null)) {
            for (int i = 0; i < MainActivity.currentOrder.getTotalPizzas(); i++) {
                s.add(MainActivity.currentOrder.toString(i));
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, s);
        listView.setAdapter(arrayAdapter);
    }

}
