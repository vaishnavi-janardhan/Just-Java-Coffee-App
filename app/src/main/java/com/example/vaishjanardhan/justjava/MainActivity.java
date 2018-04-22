package com.example.vaishjanardhan.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private static final String KEY_TEXT_VALUE1 = "textValue1";
    private EditText customerNameEditText;
    private EditText customerPhoneEditText;
    private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
    private LinearLayout mOrderLayout;
    private ImageView mClosedImageView;
    private int quantity = 1;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Handling orientation changes by saving the instance state
        mTextView = findViewById(R.id.quantity_text_view);
        if (savedInstanceState != null) {
            CharSequence savedText1 = savedInstanceState.getCharSequence(KEY_TEXT_VALUE1);
            mTextView.setText(savedText1);
            //updating the quantity variable, else it will be reset to 1
            //converting charSequence to string and then to integer
            quantity = Integer.parseInt(mTextView.getText().toString());
        }
        mOrderLayout = findViewById(R.id.order_layout);
        mClosedImageView = findViewById(R.id.image_closed);

        //Finding the current time:
        //accepting orders only from 8 am to 10 pm
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        Log.v("MainActivity", "Current Hour: " + currentHour);
        if (currentHour >= 8 && currentHour <= 22) {
            showOpened();
        }

    }


    /**
     * makes the order form layout visible
     * if time is between 8 am and 10 pm
     */
    public void showOpened() {
        mOrderLayout.setVisibility(View.VISIBLE);
        mClosedImageView.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_TEXT_VALUE1, mTextView.getText());
    }

    /**
     * to get the name of the customer
     *
     * @return name of the customer
     */
    public String getCustomerName() {
        customerNameEditText = findViewById(R.id.name_edit_text);
        return customerNameEditText.getText().toString();
    }

    /**
     * to get the phone number of th customer
     *
     * @return phone number of the customer
     */
    public String getCustomerPhone() {
        customerPhoneEditText = findViewById(R.id.phone_edit_text);
        return customerPhoneEditText.getText().toString();

    }

    /**
     * Called when the '+' button is clicked
     * increases quantity by 1
     *
     * @param view
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, R.string.error_100, Toast.LENGTH_SHORT).show();

        } else {
            quantity++;
            displayQuantity(quantity);
        }
    }

    /**
     * Called when the '-' button is clicked
     * decreases the quantity by 1
     *
     * @param view
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, R.string.error_0, Toast.LENGTH_SHORT).show();

        } else {
            quantity--;
            displayQuantity(quantity);
        }

    }

    /**
     * Checks whether the whipped cream checkBox is ticked or not
     *
     * @return the state of the whipped cream checkBox
     */
    private boolean topping1() {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
        Boolean whippedCreamCheckBoxChecked = whippedCreamCheckBox.isChecked();
        Log.v("Main Activity", getString(R.string.whipped_cream_log) + " : " + whippedCreamCheckBoxChecked);
        return whippedCreamCheckBoxChecked;
    }

    /**
     * Checks whether the chocolate checkBox is ticked or not
     *
     * @return the state of the chocolate checkBox
     */
    private boolean topping2() {
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_check_box);
        Boolean chocolateCheckBoxChecked = chocolateCheckBox.isChecked();
        Log.v("Main Activity", getString(R.string.chocolate_log) + " : " + chocolateCheckBoxChecked);
        return chocolateCheckBoxChecked;
    }

    /**
     * This method is called when the order button is clicked.
     * It checks whether the name and the phone number fields are filled
     * It sends an email intent
     */
    public void submitOrder(View view) {
        int price = calculatePrice(10);

        if (TextUtils.isEmpty(getCustomerName())) {
            customerNameEditText.setError(getString(R.string.name_empty_error));
        }

        if (TextUtils.isEmpty(getCustomerPhone())) {
            customerPhoneEditText.setError(getString(R.string.phone_empty_error));
        } else {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:vaish.janardhan@gmail.com")); // only email apps should handle this
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + " " + getCustomerName());
            emailIntent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price));
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     *
     * @param quantityDisp incremented or decremented quantity
     */
    private void displayQuantity(int quantityDisp) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantityDisp));
    }


    /**
     * calculates the price of the order
     *
     * @param pricePerCup cost of each cup of coffee
     * @return total price
     */
    private int calculatePrice(int pricePerCup) {
        int totalPrice = quantity * pricePerCup;
        if (topping1()) {
            totalPrice += quantity * 2;
        }
        if (topping2()) {
            totalPrice += quantity * 3;
        }
        return totalPrice;
    }

    /**
     * creates the order summary whivh
     *
     * @param price total order price
     */
    private String createOrderSummary(int price) {
        String name = getCustomerName();
        String phone = getCustomerPhone();
        String topping1;
        String topping2;
        if (topping1()) {
            topping1 = getString(R.string.add_yes);
        } else {
            topping1 = getString(R.string.add_no);
        }
        if (topping2()) {
            topping2 = getString(R.string.add_yes);
        } else {
            topping2 = getString(R.string.add_no);
        }
        String priceInRs = (NumberFormat.getCurrencyInstance().format(price));
        String priceMessage = getString(R.string.price_disp) + " : " + priceInRs;
        return getString(R.string.name_disp) + " : " + name + "\n" + getString(R.string.phone_disp) + " : " + phone + "\n " + getString(R.string.quantity_disp) + ": " + quantity + "\n" + getString(R.string.whipped_cream_disp) + " : " + topping1 + "\n" + getString(R.string.chocolate_disp) + " : " + topping2 + "\n" + priceMessage + "\n" + getString(R.string.thank_you_disp) + "!";
    }

}

