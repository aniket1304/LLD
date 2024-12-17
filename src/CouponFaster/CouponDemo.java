package CouponFaster;

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

        CouponManager couponManager = new CouponManager();

        Map<String, String> couponMap = couponManager.getCouponMap(couponData, categoryData);
        for (String coupon : couponMap.keySet()) {
            System.out.println(coupon + "    " + couponMap.get(coupon));
        }

        System.out.println(" ");
        System.out.println(" ");

        System.out.println(couponManager.findBestCoupon("Comforter Sets", couponMap));
        System.out.println(couponManager.findBestCoupon("Bedding", couponMap));
        System.out.println(couponManager.findBestCoupon("Bathroom Accessories", couponMap));
        System.out.println(couponManager.findBestCoupon("Soap Dispensers", couponMap));
        System.out.println(couponManager.findBestCoupon("Toy Organizers", couponMap));
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

    public String findBestCoupon(String categoryName, Map<String, String> couponMap) {
        if (!couponMap.containsKey(categoryName))
            return null;
        return couponMap.get(categoryName);
    }

    public Map<String, String> getCouponMap(List<Coupon> couponData, List<Category> categoryData) {
        Map<String, String> couponMap = new HashMap<>();

        for (Coupon coupon : couponData) {
            couponMap.put(coupon.categoryName, coupon.name);
        }

        Map<String, String> categoryMap = new HashMap<>();

        for (Category category : categoryData) {
            categoryMap.put(category.name, category.parentName);
        }

        for (String categoryName : categoryMap.keySet()) {
            if (couponMap.containsKey(categoryName))
                continue;
            String newCat = categoryName;
            while (newCat != null) {
                if (couponMap.containsKey(newCat)) {
                    couponMap.put(categoryName, couponMap.get(newCat));
                    break;
                }
                newCat = categoryMap.get(newCat);
            }
        }
        return couponMap;
    }
}


