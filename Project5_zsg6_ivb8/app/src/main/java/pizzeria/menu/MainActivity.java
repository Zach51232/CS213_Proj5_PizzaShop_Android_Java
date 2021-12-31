package pizzeria.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * MainActivity is our home page
 *
 * @author Zach, Isaac
 */
public class MainActivity extends AppCompatActivity {
    ImageView deluxe, hawaiian, pepperoni, currentOrderButton, storeOrderButton;
    String phoneNum = "";
    EditText currentPhoneNumber;
    static String phoneNumber;
    public static Order currentOrder;
    public static StoreOrders storeOrders;
    public static final String NOTSET = "0";
    /**
     * Creates the main page, and calls setUpPhotos() to launch 3 of the pages and puts a listener
     * for StoreOrders
     *
     * @param savedInstanceState different instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentPhoneNumber = findViewById(R.id.phoneNumb);
        storeOrderButton = findViewById(R.id.store);
        currentOrderButton = findViewById(R.id.current);

        setUpPhotos();

        storeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storeOrders == null || storeOrders.getTotalOrders() == 0) {
                    cantViewStoreOrders();
                } else {
                    Intent intent = new Intent(MainActivity.this, StoreActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Will take the phone number, check if it is valid,
     * and give access to the different pages
     *
     * @param intent      the intent we will go to
     * @param goingToCust whether there is a customer order or not
     */
    void testAndStart(Intent intent, boolean goingToCust) {
        String num = currentPhoneNumber.getText().toString();
        try {
            if (num.length() != 10) {
                String tenDigitsMsg = getString(R.string.tenDigits);
                Toast.makeText(this,tenDigitsMsg, Toast.LENGTH_LONG).show();
                return;
            }
            String firstPart = num.substring(0, 6);
            String secondPart = num.substring(6);
            int a = Integer.valueOf(firstPart);
            int b = Integer.valueOf(secondPart);
        } catch (NumberFormatException e) {
            String onlyNumsMsg = getString(R.string.onlyNums)+num;
            Toast.makeText(this,onlyNumsMsg, Toast.LENGTH_LONG).show();
            return;
        }
        if (goingToCust) {
            if (num.equals(CustPizzaActivity.phoneNumber) || CustPizzaActivity.phoneNumber.equals(NOTSET)) {
                if (CustPizzaActivity.phoneNumber.equals(NOTSET)) {
                    phoneNumber = num;
                }
                phoneNum = num;

                startActivity(intent);
            } else {
                String diffNumOrderMsg = getString(R.string.diffOrder)+phoneNumber+getString(R.string.useCart);
                Toast.makeText(this, diffNumOrderMsg, Toast.LENGTH_LONG).show();
            }
        } else {
            if (num.equals(CurrentActivity.phoneNumber)) {
                startActivity(intent);
            } else {
                String diffNumOrderMsg = getString(R.string.diffOrder)+phoneNumber+getString(R.string.useCart);
                Toast.makeText(this, diffNumOrderMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * checks if there are pizzas in the order
     *
     * @return true if there are some pizzas in the order
     */
    boolean checkCurrentOrder() {
        if (currentOrder == null || currentOrder.getTotalPizzas() == 0) {
            String makeOrderMsg = getString(R.string.makeOrder);
            Toast.makeText(this, makeOrderMsg, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * sets up the on click listeners for the pizza types and the current order
     */
    void setUpPhotos() {
        setUpTypes();
        if (!CustPizzaActivity.phoneNumber.equals(NOTSET))
            currentPhoneNumber.setText(CustPizzaActivity.phoneNumber);
        deluxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustPizzaActivity.class);
                intent.putExtra("Key", 0);
                testAndStart(intent, true);
            }
        });
        hawaiian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustPizzaActivity.class);
                intent.putExtra("Key", 1);
                testAndStart(intent, true);
            }
        });
        pepperoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustPizzaActivity.class);
                intent.putExtra("Key", 2);
                testAndStart(intent, true);
            }
        });
        currentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCurrentOrder()) {
                    Intent intent = new Intent(MainActivity.this, CurrentActivity.class);
                    intent.putExtra("PhoneNum", phoneNum);
                    testAndStart(intent, false);
                }
            }
        });
    }

    /**
     * A toast message for when there are no orders in the store
     */
    void cantViewStoreOrders() {
        String cantAccessStoreMsg = getString(R.string.cantAccessStore);
        Toast.makeText(this, cantAccessStoreMsg, Toast.LENGTH_LONG).show();
    }

    /**
     * sets the pizza types to the image ids in the xml file
     */
    void setUpTypes() {
        deluxe = findViewById(R.id.del);
        hawaiian = findViewById(R.id.haw);
        pepperoni = findViewById(R.id.pep);
    }
}