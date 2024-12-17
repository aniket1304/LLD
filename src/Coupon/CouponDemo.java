package Coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDemo {
    public static void main(String[] args) {
        List<Coupon> couponData = List.of(
                new Coupon("Comforter Sets", "Comforters Sale"),
                new Coupon("Bedding", "Savings on Bedding"),
                new Coupon("Bed & Bath", "Low price for Bed & Bath")
        );
        List<Category> categoryData = List.of(
                new Category("Comforter Sets","Bedding"),
                new Category("Bedding","Bed & Bath"),
                new Category("Bed & Bath",null),
                new Category("Soap Dispensers","Bathroom Accessories"),
                new Category("Bathroom Accessories", "Bed & Bath"),
                new Category("Toy Organizers","Baby And Kids"),
                new Category("Baby And Kids",null)
        );

        Map<String, String> couponMap = new HashMap<>();

        for (Coupon coupon : couponData) {
            couponMap.put(coupon.categoryName, coupon.name);
        }

        Map<String, String> categoryMap = new HashMap<>();

        for (Category category : categoryData) {
            categoryMap.put(category.name, category.parentName);
        }

        CouponManager couponManager = new CouponManager();

        System.out.println(couponManager.findBestCoupon("Comforter Sets", couponMap, categoryMap));
        System.out.println(couponManager.findBestCoupon("Bedding", couponMap, categoryMap));
        System.out.println(couponManager.findBestCoupon("Bathroom Accessories", couponMap, categoryMap));
        System.out.println(couponManager.findBestCoupon("Soap Dispensers", couponMap, categoryMap));
        System.out.println(couponManager.findBestCoupon("Toy Organizers", couponMap, categoryMap));
    }
}

class Category {
    String name;
    String parentName;

    public Category(String name, String parentName) {
        this.name = name;
        this.parentName = parentName;
    }
}

class Coupon {
    String categoryName;
    String name;

    public Coupon(String categoryName, String name) {
        this.categoryName = categoryName;
        this.name = name;
    }
}

class CouponManager {

    public CouponManager() {

    }

    public String findBestCoupon(String categoryName, Map<String, String> couponMap, Map<String, String> categoryMap) {
        while (true) {
            if (categoryName == null)
                return null;
            if (couponMap.containsKey(categoryName))
                return couponMap.get(categoryName);
            categoryName = categoryMap.get(categoryName);
        }
    }
}


