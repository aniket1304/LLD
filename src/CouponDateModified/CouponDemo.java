package CouponDateModified;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDemo {
    private static final String dateFormat = "yyyy-MM-dd";
    public static void main(String[] args) throws ParseException {

        List<Coupon> couponData = getCoupons();

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

        Map<String, Coupon> couponMap = couponManager.getCouponMap(couponData, categoryData);

        for (String coupon : couponMap.keySet()) {
            System.out.println(coupon + "    " + couponMap.get(coupon));
        }

        System.out.println(" ");
        System.out.println(" ");

        System.out.println(couponManager.findBestCoupon("Bed & Bath", couponMap));
        System.out.println(couponManager.findBestCoupon("Bedding", couponMap));
        System.out.println(couponManager.findBestCoupon("Bathroom Accessories", couponMap));
        System.out.println(couponManager.findBestCoupon("Comforter Sets", couponMap));
        System.out.println(couponManager.findBestCoupon("Toy Organizers", couponMap));
    }

    private static List<Coupon> getCoupons() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        return List.of(
                new Coupon("Comforter Sets", "Comforters Sale", simpleDateFormat.parse("2020-01-01")),
                new Coupon("Comforter Sets", "Cozy Comforter Coupon", simpleDateFormat.parse("2021-01-01")),
                new Coupon("Bedding", "Best Bedding Bargains", simpleDateFormat.parse("2019-01-01")),
                new Coupon("Bedding", "Savings on Bedding", simpleDateFormat.parse("2019-01-01")),
                new Coupon("Bed & Bath", "Low price for Bed & Bath", simpleDateFormat.parse("2018-01-01")),
                new Coupon("Bed & Bath", "Bed & Bath extravaganza", simpleDateFormat.parse("2019-01-01")),
                new Coupon("Bed & Bath", "Big Savings for Bed & Bath", simpleDateFormat.parse("2030-01-01"))
        );
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
    Date modifiedDate;

    public Coupon(String categoryName, String name, Date modifiedDate) {
        this.categoryName = categoryName;
        this.name = name;
        this.modifiedDate = modifiedDate;
    }
}

class CouponManager {

    public CouponManager() {

    }

    public String findBestCoupon(String categoryName, Map<String, Coupon> couponMap) {
        if (!couponMap.containsKey(categoryName))
            return null;
        return couponMap.get(categoryName).name;
    }

    public Map<String, Coupon> getCouponMap(List<Coupon> couponData, List<Category> categoryData) {
        Map<String, Coupon> couponMap = new HashMap<>();

        for (Coupon coupon : couponData) {
            if (coupon.modifiedDate.after(Date.from(Instant.now())))
                continue;
            if (couponMap.containsKey(coupon.categoryName) && couponMap.get(coupon.categoryName).modifiedDate.after(coupon.modifiedDate))
                continue;
            couponMap.put(coupon.categoryName, coupon);
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


