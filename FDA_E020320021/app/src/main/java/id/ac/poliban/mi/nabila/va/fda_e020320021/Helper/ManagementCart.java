package id.ac.poliban.mi.nabila.va.fda_e020320021.Helper;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import id.ac.poliban.mi.nabila.va.fda_e020320021.Domain.FoodDomain;
import id.ac.poliban.mi.nabila.va.fda_e020320021.Interface.ChangeNumberItemsListener;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    //
    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList",listFood);
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show();

    }
    //
    public ArrayList<FoodDomain> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    public void plusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemsListener.changed();
    }

    public void MinusNumerFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<FoodDomain> listFood2 = getListCard();
        double fee = 0;
        for (int i = 0; i < listFood2.size(); i++) {
            fee = fee + (listFood2.get(i).getFee() * listFood2.get(i).getNumberInCart());
        }
        return fee;
    }

}

