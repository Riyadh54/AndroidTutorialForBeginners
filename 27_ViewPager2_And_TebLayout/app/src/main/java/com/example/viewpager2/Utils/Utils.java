package com.example.viewpager2.Utils;

import com.example.viewpager2.Model.ProductModel;
import com.example.viewpager2.R;

import java.util.ArrayList;

public class Utils {


     public static ArrayList<String> categories = new ArrayList<>();
     public static ArrayList<ProductModel> products = new ArrayList<>();

     public static void FillCategoryData(){
         categories.add("FOOD");
         categories.add("DESERT");
         categories.add("PASTRY");
         categories.add("FAST FOOD");
     }
       /*
     public static void FillAllProductsData(){
         for (int i =0;i<50;i++)
         {
             products.add(new ProductModel(i+1, R.drawable.ic_baseline_cake,"Product #"+i, categories.get(i%5), i*2+0.2));
         }
     }
     */

    public static void FillCategory1(){
         // Food
        int[] img = new int[]{R.drawable.food,R.drawable.spagity};
        for (int i =0;i<2;i++)
        {
            products.add(new ProductModel(i+1, img[i],"Product "+i, categories.get(0), i*9+5));
        }
    }
    public static void FillCategory2(){
         // Desert
         int[] img = new int[]{R.drawable.pastry,R.drawable.pastry1,R.drawable.pastry2};
        for (int i =0;i<3;i++)
        {
            products.add(new ProductModel(i+1, img[i],"Product "+i, categories.get(1), i*9+5));
        }
    }
    public static void FillCategory3(){
         // Pastry
        int[] img = new int[]{R.drawable.pastry,R.drawable.pastry1,R.drawable.pastry2};
        for (int i =0;i<3;i++)
        {
            products.add(new ProductModel(i+1, img[i],"Product "+i, categories.get(2), i*9+5));
        }
    }
    public static void FillCategory4(){
         // FastFood
        int[] img = new int[]{R.drawable.fastfood2,R.drawable.fastfood};
        for (int i =0;i<2;i++)
        {
            products.add(new ProductModel(i+1, img[i],"Product "+i, categories.get(3), i*9+5));
        }
    }


     public static void FillData(){
         FillCategoryData();
         FillCategory1();
         FillCategory2();
         FillCategory3();
         FillCategory4();

     }

     public static ArrayList<ProductModel> GetProductByCategory(String category)
     {
         ArrayList<ProductModel> data =new ArrayList<>();
         for (int i =0 ;i< products.size();i++)
         {
             if (category.equals(products.get(i).getCategory()))
             {
                 data.add(products.get(i));
             }
         }
         return data;
     }
}
