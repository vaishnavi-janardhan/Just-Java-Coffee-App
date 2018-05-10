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
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class OrderActivity extends AppCompatActivity {
    private static final String KEY_TEXT_VALUE1 = "textValue1";
    private static final String KEY_TEXT_VALUE2 = "textValue2";
    private EditText customerNameEditText;
    private EditText customerPhoneEditText;
    private int quantityCoffee = 0;
    private int quantityDonut = 0;
    private TextView mCoffeeTextView;
    private TextView mDonutTextView;
    private CheckBox mWhippedCreamCheckBox;
    private CheckBox mChocolateCheckBox;
    private CheckBox mGlazeCheckBox;
    private CheckBox mFrostCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Set the price per cup of coffee
        String coffeePriceDisp = getString(R.string.price_coffee);
        TextView mCoffeePriceTextView = findViewById(R.id.coffee_price_text_view);
        mCoffeePriceTextView.setText(coffeePriceDisp);

        //Set the price per Donut
        String donutPriceDisp = getString(R.string.price_donut);
        TextView mDonutPriceTextView = findViewById(R.id.donut_price_text_view);
        mDonutPriceTextView.setText(donutPriceDisp);

        //Set the price of whipped cream
        String whippedCreamPriceDisp = getString(R.string.price_whipped_cream);
        TextView mWhippedCreamTextView = findViewById(R.id.whipped_cream_price_disp);
        mWhippedCreamTextView.setText(whippedCreamPriceDisp);

        //Set the price of Chocolate topping
        String chocolatePriceDisp = getString(R.string.price_chocolate);
        TextView mChocolateTextView = findViewById(R.id.chocolate_price_disp);
        mChocolateTextView.setText(chocolatePriceDisp);

        //Set the price of glazing
        String glazePriceDisp = getString(R.string.price_glaze);
        TextView mGlazeTextView = findViewById(R.id.glaze_price_text_view);
        mGlazeTextView.setText(glazePriceDisp);

        //Set the price of frost
        String frostPriceDisp = getString(R.string.price_frost);
        TextView mFrostTextView = findViewById(R.id.frost_price_text_view);
        mFrostTextView.setText(frostPriceDisp);

        //Handling orientation changes by saving the instance state
        mCoffeeTextView = findViewById(R.id.coffee_quantity_text_view);
        if (savedInstanceState != null) {
            CharSequence savedText1 = savedInstanceState.getCharSequence(KEY_TEXT_VALUE1);
            mCoffeeTextView.setText(savedText1);
            //updating the quantity variable, else it will be reset to 1
            //converting charSequence to string and then to integer
            quantityCoffee = Integer.parseInt(mCoffeeTextView.getText().toString());
        }
        mDonutTextView = findViewById(R.id.donut_quantity_text_view);
        if (savedInstanceState != null) {
            CharSequence savedText2 = savedInstanceState.getCharSequence(KEY_TEXT_VALUE2);
            mCoffeeTextView.setText(savedText2);
            //updating the quantity variable, else it will be reset to 1
            //converting charSequence to string and then to integer
            quantityDonut = Integer.parseInt(mDonutTextView.getText().toString());
        }
        mWhippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
        mChocolateCheckBox = findViewById(R.id.chocolate_check_box);
        mGlazeCheckBox = findViewById(R.id.glazed_donut_check_box);
        mFrostCheckBox = findViewById(R.id.frosted_donut_check_box);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_TEXT_VALUE1, mCoffeeTextView.getText());
        outState.putCharSequence(KEY_TEXT_VALUE2, mDonutTextView.getText());
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
    public void incrementCoffee(View view) {
        if (quantityCoffee == 100) {
            Toast.makeText(this, R.string.error_100, Toast.LENGTH_SHORT).show();

        } else {
            quantityCoffee++;
            displayCoffeeQuantity(quantityCoffee);
        }
        mWhippedCreamCheckBox.setClickable(true);
        mChocolateCheckBox.setClickable(true);
    }

    /**
     * Called when the '-' button is clicked
     * decreases the quantity by 1
     *
     * @param view
     */
    public void decrementCoffee(View view) {
        if (quantityCoffee == 1) {
            mWhippedCreamCheckBox.setClickable(false);
            mChocolateCheckBox.setClickable(false);
            mWhippedCreamCheckBox.setChecked(false);
            mChocolateCheckBox.setChecked(false);
        }
        if (quantityCoffee == 0) {
            Toast.makeText(this, R.string.error_negative_coffee, Toast.LENGTH_SHORT).show();
        } else {
            quantityCoffee--;
            displayCoffeeQuantity(quantityCoffee);
        }
    }

    /**
     * Called when the '+' button is clicked
     * increases quantity by 1
     *
     * @param view
     */
    public void incrementDonut(View view) {
        if (quantityDonut == 100) {
            Toast.makeText(this, R.string.error_100, Toast.LENGTH_SHORT).show();

        } else {
            quantityDonut++;
            displayDonutQuantity(quantityDonut);
        }
        mFrostCheckBox.setClickable(true);
        mGlazeCheckBox.setClickable(true);
    }

    /**
     * Called when the '-' button is clicked
     * decreases the quantity by 1
     *
     * @param view
     */
    public void decrementDonut(View view) {
        if (quantityDonut == 1) {
            mGlazeCheckBox.setClickable(false);
            mFrostCheckBox.setClickable(false);
            mFrostCheckBox.setChecked(false);
            mGlazeCheckBox.setChecked(false);
        }
        if (quantityDonut == 0) {
            Toast.makeText(this, R.string.erroe_negative_donut, Toast.LENGTH_SHORT).show();
        } else {
            quantityDonut--;
            displayDonutQuantity(quantityDonut);
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
     * Checks whether the glaze checkBox is ticked or not
     *
     * @return the state of the glaze checkBox
     */
    private boolean topping3() {
        CheckBox glazeCheckBox = findViewById(R.id.glazed_donut_check_box);
        Boolean glazeCheckBoxChecked = glazeCheckBox.isChecked();
        Log.v("Main Activity", getString(R.string.glaze_disp) + " : " + glazeCheckBoxChecked);
        return glazeCheckBoxChecked;
    }

    /**
     * Checks whether the frost checkBox is ticked or not
     *
     * @return the state of the frost checkBox
     */
    private boolean topping4() {
        CheckBox frostCheckBox = findViewById(R.id.frosted_donut_check_box);
        Boolean frostCheckBoxChecked = frostCheckBox.isChecked();
        Log.v("Main Activity", getString(R.string.frosted_disp) + " : " + frostCheckBoxChecked);
        return frostCheckBoxChecked;
    }

    /**
     * This method is called when the order button is clicked.
     * It checks whether the name and the phone number fields are filled
     * It sends an email intent
     */
    public void submitOrder(View view) {
        int price = calculatePrice(15, 50);

        if (TextUtils.isEmpty(getCustomerName())) {
            customerNameEditText.setError(getString(R.string.name_empty_error));
        } else if (TextUtils.isEmpty(getCustomerPhone())) {
            customerPhoneEditText.setError(getString(R.string.phone_empty_error));
        } else if (quantityDonut == 0 && quantityCoffee == 0) {
            Toast.makeText(this, R.string.error_no_order, Toast.LENGTH_SHORT).show();
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
     * @param quantityCoffeeDisp incremented or decremented quantity
     */
    private void displayCoffeeQuantity(int quantityCoffeeDisp) {
        TextView quantityCoffeeTextView = findViewById(R.id.coffee_quantity_text_view);
        quantityCoffeeTextView.setText(String.valueOf(quantityCoffeeDisp));
    }

    /**
     * This method displays the given quantity value on the screen.
     *
     * @param quantityDonutDisp incremented or decremented quantity
     */
    private void displayDonutQuantity(int quantityDonutDisp) {
        TextView quantityDonutTextView = findViewById(R.id.donut_quantity_text_view);
        quantityDonutTextView.setText(String.valueOf(quantityDonutDisp));
    }


    /**
     * calculates the price of the order
     *
     * @param pricePerCup cost of each cup of coffee
     * @return total price
     */
    private int calculatePrice(int pricePerCup, int pricePerDonut) {
        int totalPrice = (quantityCoffee * pricePerCup) + (quantityDonut * pricePerDonut);
        if (topping1()) {
            totalPrice += quantityCoffee * 5;
        }
        if (topping2()) {
            totalPrice += quantityCoffee * 8;
        }
        if (topping3()) {
            totalPrice += quantityDonut * 10;
        }
        if (topping4()) {
            totalPrice += quantityDonut * 15;
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
        String topping3;
        String topping4;
        String priceCoffee;
        String priceDonut;

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
        if (topping3()) {
            topping3 = getString(R.string.add_yes);
        } else {
            topping3 = getString(R.string.add_no);
        }
        if (topping4()) {
            topping4 = getString(R.string.add_yes);
        } else {
            topping4 = getString(R.string.add_no);
        }
        String priceInRs = (NumberFormat.getCurrencyInstance().format(price));
        String priceMessage = getString(R.string.price_disp) + " : " + priceInRs;
        if (quantityCoffee != 0) {
            priceCoffee = "Cups of Coffee: " + quantityCoffee + "\n" + getString(R.string.whipped_cream_disp) + " : " + topping1 + "\n" + getString(R.string.chocolate_disp) + " : " + topping2;
        } else {
            priceCoffee = "";
        }
        if (quantityDonut != 0) {
            priceDonut = "Donuts: " + quantityDonut + "\n" + getString(R.string.glaze_disp) + " : " + topping3 + "\n" + getString(R.string.frosted_disp) + " : " + topping4;
        } else {
            priceDonut = "";
        }
        return getString(R.string.name_disp) + " : " + name + "\n" + getString(R.string.phone_disp) + " : " + phone + "\n\n" + priceCoffee + "\n\n" + priceDonut + "\n\n" + priceMessage + "\n\n" + getString(R.string.thank_you_disp) + "!";
    }

}
