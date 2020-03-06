package com.example.svakatha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.svakatha.Adapter.ProductAdapter;
import com.example.svakatha.Model.ProductModel;
import com.example.svakatha.view.CartActivity;

import java.util.ArrayList;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity implements ProductAdapter.CallBackUs, ProductAdapter.HomeCallBack {

    public static ArrayList<ProductModel> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    ProductAdapter productAdapter;
    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProduct();
        productAdapter = new ProductAdapter(arrayList, this, this);
        productRecyclerView = findViewById(R.id.product_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        productRecyclerView.setLayoutManager(gridLayoutManager);
        productRecyclerView.setAdapter(productAdapter);

    }


    private void addProduct() {
        ProductModel productModel = new ProductModel("layout 1", "10", "20", R.drawable.image1);
        arrayList.add(productModel);
        ProductModel productModel1 = new ProductModel("layout 2", "20", "10", R.drawable.image2);
        arrayList.add(productModel1);
        ProductModel productModel2 = new ProductModel("layout 3", "30", "10", R.drawable.image3);
        arrayList.add(productModel2);

        ProductModel productModel3 = new ProductModel("layout 4", "40", "20", R.drawable.image4);
        arrayList.add(productModel3);
        ProductModel productModel12 = new ProductModel("layout 5", "50", "10", R.drawable.image4);
        arrayList.add(productModel12);
        ProductModel productModel23 = new ProductModel("layout 6", "60", "10", R.drawable.image5);
        arrayList.add(productModel23);

        ProductModel productModel4 = new ProductModel("layout 7", "70", "20", R.drawable.image6);
        arrayList.add(productModel4);
        ProductModel productModel14 = new ProductModel("layout 8", "80", "10", R.drawable.image7);
        arrayList.add(productModel14);
        ProductModel productModel25 = new ProductModel("layout 9", "90", "10", R.drawable.image8);
        arrayList.add(productModel25);

        ProductModel productModel5 = new ProductModel("roll", "100", "20", R.drawable.image8);
        arrayList.add(productModel5);

    }

    @Override
    public void addCartItemView() {
        //addItemToCartMethod();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.ic_shopping_cart_white_24dp));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.cart_action:
                if (cart_count < 1) {
                    Toast.makeText(this, "there is no item in cart", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this,CartActivity.class));
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void updateCartCount(Context context) {
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }
}
