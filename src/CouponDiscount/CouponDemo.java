package CouponDiscount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class CouponDemo {
    private static final String dateFormat = "yyyy-MM-dd";
    public static void main(String[] args) throws ParseException {

        List<Coupon> couponData = getCoupons();
        List<Category> categoryData = getCategories();
        List<Product> productData = getProducts();
        Map<String, Product> productMap = getProductMap(productData);


        CouponManager couponManager = new CouponManager();

        Map<String, Coupon> couponMap = couponManager.getCouponMap(couponData, categoryData);

        for (String coupon : couponMap.keySet()) {
            System.out.println(coupon + "    " + couponMap.get(coupon));
        }

        System.out.println(" ");
        System.out.println(" ");

        System.out.println(couponManager.findBestCoupon("Cozy Comforter", productMap, couponMap));
        System.out.println(couponManager.findBestCoupon("All-in-one Bedding Set", productMap, couponMap));
        System.out.println(couponManager.findBestCoupon("Infinite Soap Dispenser", productMap, couponMap));
        System.out.println(couponManager.findBestCoupon("Rainbow Toy Box", productMap, couponMap));
    }

    private static List<Product> getProducts() {
        List<String[]> products = List.of(
                new String[]{"ProductName:Cozy Comforter","Price:100.00", "CategoryName:Comforter Sets"},
                new String[]{"ProductName:All-in-one Bedding Set", "Price:50.00", "CategoryName:Bedding"},
                new String[]{"ProductName:Infinite Soap Dispenser", "Price:500.00" ,"CategoryName:Bathroom Accessories"},
                new String[]{"ProductName:Rainbow Toy Box","Price:257.00", "CategoryName:Baby And Kids"}
        );

        List<Product> productData = new ArrayList<>();

        for (String[] product : products) {
            productData.add(
                    new Product(product[0].split(":")[1], Double.parseDouble(product[1].split(":")[1]), product[2].split(":")[1])
            );
        }
        return productData;
    }

    private static Map<String, Product> getProductMap(List<Product> productData) {
        Map<String, Product> productMap = new HashMap<>();
        for (Product product : productData) {
            productMap.put(product.name, product);
        }
        return productMap;
    }

    private static List<Category> getCategories() {

        List<String[]> categories = List.of(
                new String[]{"CategoryName:Comforter Sets", "CategoryParentName:Bedding"},
                new String[]{"CategoryName:Bedding", "CategoryParentName:Bed & Bath"},
                new String[]{"CategoryName:Bed & Bath", "CategoryParentName:None"},
                new String[]{"CategoryName:Soap Dispensers", "CategoryParentName:Bathroom Accessories"},
                new String[]{"CategoryName:Bathroom Accessories", "CategoryParentName:Bed & Bath"},
                new String[]{"CategoryName:Toy Organizers", "CategoryParentName:Baby And Kids"},
                new String[]{"CategoryName:Baby And Kids", "CategoryParentName:None"}
        );

        List<Category> categoryData = new ArrayList<>();

        for (String[] category : categories) {
            categoryData.add(
                    new Category(category[0].split(":")[1], category[1].split(":")[1])
            );
        }
        return categoryData;
    }

    private static List<Coupon> getCoupons() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        List<String[]> couponsInput = List.of(
                new String[]{"CategoryName:Comforter Sets", "CouponName:Comforters Sale", "DateModified:2020-01-01","Discount:10%"},
                new String[]{"CategoryName:Comforter Sets", "CouponName:Cozy Comforter Coupon", "DateModified:2020-01-01","Discount:$15"},
                new String[]{"CategoryName:Bedding", "CouponName:Best Bedding Bargains", "DateModified:2019-01-01","Discount:35%"},
                new String[]{"CategoryName:Bedding", "CouponName:Savings on Bedding", "DateModified:2019-01-01","Discount:25%"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Low price for Bed & Bath", "DateModified:2018-01-01","Discount:50%"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Bed & Bath extravaganza", "DateModified:2019-01-01","Discount:75%"}
        );

        List<Coupon> couponData = new ArrayList<>();

        for (String[] coupon : couponsInput) {
            String categoryName = coupon[0].split(":")[1];
            String name = coupon[1].split(":")[1];
            String modifiedDate = coupon[2].split(":")[1];
            String discount = coupon[3].split(":")[1];
            double discountValue = 0d;
            DiscountType discountType = DiscountType.UNKNOWN;
            if (discount.startsWith("$")) {
                discountValue = Double.parseDouble(discount.substring(1));
                discountType = DiscountType.ABSOLUTE;
            }
            else if (discount.endsWith("%")) {
                discountValue = Double.parseDouble(discount.substring(0, discount.length()-1));
                discountType = DiscountType.PERCENTAGE;
            }
            couponData.add(
                    new Coupon(categoryName, name, simpleDateFormat.parse(modifiedDate), discountValue, discountType)
            );
        }
        return couponData;
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
    double discount;
    DiscountType discountType;

    public Coupon(String categoryName, String name, Date modifiedDate, double discount, DiscountType discountType) {
        this.categoryName = categoryName;
        this.name = name;
        this.modifiedDate = modifiedDate;
        this.discount = discount;
        this.discountType = discountType;
    }
}

class Product {
    String name;
    double price;
    String categoryName;

    public Product(String name, double price, String categoryName) {
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }
}

enum DiscountType {
    UNKNOWN,
    ABSOLUTE,
    PERCENTAGE
}

class CouponManager {

    public CouponManager() {

    }

    public double findBestCoupon(String productName, Map<String, Product> productMap, Map<String, Coupon> couponMap) {
        Product product = productMap.get(productName);
        if (!couponMap.containsKey(product.categoryName))
            return product.price;
        Coupon coupon = couponMap.get(product.categoryName);
        if (coupon.discountType.equals(DiscountType.ABSOLUTE))
            return product.price - coupon.discount;
        else if (coupon.discountType.equals(DiscountType.PERCENTAGE))
            return product.price * (100 - coupon.discount)/100.00;
        return product.price;
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


