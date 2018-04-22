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
    private int quantity = 1;
    private TextView mTextView;
    //private TextView nTextView;
    private static final String KEY_TEXT_VALUE1 = "textValue1";

    EditText customerNameEditText;
    String customerName ;

    EditText customerPhoneEditText;
    String customerPhone ;
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));

    LinearLayout mOrderLayout;
    ImageView mClosedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mOrderLayout = findViewById(R.id.order_layout);
        mClosedImageView = findViewById(R.id.image_closed);

        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        Log.v("MainActivity", "Current Hour: " + currentHour);
        if (currentHour >= 8 && currentHour <= 22) {
            Log.v("MainActivity", "Hi");
            showOpened();
        }
        mTextView = findViewById(R.id.quantity_text_view);
            //nTextView = findViewById(R.id.order_summary_text_view);
            if (savedInstanceState != null) {
                CharSequence savedText1 = savedInstanceState.getCharSequence(KEY_TEXT_VALUE1);
                mTextView.setText(savedText1);
                //CharSequence savedText2 = savedInstanceState.getCharSequence(KEY_TEXT_VALUE2);
                //nTextView.setText(savedText2);
            }
        }


    public void showOpened() {
        mOrderLayout.setVisibility(View.VISIBLE);
        mClosedImageView.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putCharSequence(KEY_TEXT_VALUE1, mTextView.getText());
            //outState.putCharSequence(KEY_TEXT_VALUE2, nTextView.getText());
        }

        public String getCustomerName () {
            customerNameEditText = findViewById(R.id.name_edit_text);
            customerName = customerNameEditText.getText().toString();
            return customerName;
        }

        public String getCustomerPhone () {
            customerPhoneEditText = findViewById(R.id.phone_edit_text);
            customerPhone = customerPhoneEditText.getText().toString();
            return customerPhone;

        }
        public void increment (View view){
            if (quantity == 100) {
                Toast.makeText(this, R.string.error_100, Toast.LENGTH_SHORT).show();

            } else {
                quantity++;
                displayQuantity(quantity);
            }
        }

        public void decrement (View view){
            if (quantity == 1) {
                Toast.makeText(this, R.string.error_0, Toast.LENGTH_SHORT).show();

            } else {
                quantity--;
                displayQuantity(quantity);
            }

        }

        private boolean topping1 () {
            CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
            Boolean whippedCreamCheckBoxChecked = whippedCreamCheckBox.isChecked();
            Log.v("Main Activity", getString(R.string.whipped_cream_log) + " : " + whippedCreamCheckBoxChecked);
            return whippedCreamCheckBoxChecked;
        }

        private boolean topping2 () {
            CheckBox chocolateCheckBox = findViewById(R.id.chocolate_check_box);
            Boolean chocolateCheckBoxChecked = chocolateCheckBox.isChecked();
            Log.v("Main Activity", getString(R.string.chocolate_log) + " : " + chocolateCheckBoxChecked);
            return chocolateCheckBoxChecked;
        }

        /**
         * This method is called when the order button is clicked.
         */
        public void submitOrder (View view){
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
         * @param quantityDisp incremented or decremented quantity
         */
        private void displayQuantity ( int quantityDisp){
            TextView quantityTextView = findViewById(R.id.quantity_text_view);
            quantityTextView.setText(String.valueOf(quantityDisp));
        }


        /**
         * calculates the price of the order
         *
         * @param pricePerCup cost of each cup of coffee
         *
         * @return total price
         */
        private int calculatePrice ( int pricePerCup){
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
         *@param price total order price
         */
        private String createOrderSummary ( int price){
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

