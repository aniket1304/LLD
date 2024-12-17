package OrderManagement;

import java.util.*;

public class OrderManagementDemo {
    public static void main(String[] args) {
        IOrderSystem iOrderSystem = new OrderSystem();
        IOrder order1 = new Order();
        order1.setName("Order-1");
        order1.setPrice(49);
        IOrder order2 = new Order();
        order2.setName("Order-2");
        order2.setPrice(31);
        IOrder order3 = new Order();
        order3.setName("Order-3");
        order3.setPrice(74);
        IOrder order4 = new Order();
        order4.setName("Order-4");
        order4.setPrice(21);
        IOrder order5 = new Order();
        order5.setName("Order-5");
        order5.setPrice(64);
        IOrder order6 = new Order();
        order6.setName("Order-6");
        order6.setPrice(94);
        IOrder order7 = new Order();
        order7.setName("Order-7");
        order7.setPrice(23);
        IOrder order8 = new Order();
        order8.setName("Order-8");
        order8.setPrice(23);
        IOrder order9 = new Order();
        order9.setName("Order-9");
        order9.setPrice(71);

        iOrderSystem.addToCart(order1);
        iOrderSystem.addToCart(order2);
        iOrderSystem.addToCart(order3);
        iOrderSystem.addToCart(order4);
        iOrderSystem.addToCart(order5);
        iOrderSystem.addToCart(order6);
        iOrderSystem.addToCart(order7);
        iOrderSystem.addToCart(order8);
        iOrderSystem.addToCart(order9);
        /*

        IOrder order = new Order();
        order.setName("Pizza");
        order.setPrice(40);

        IOrder order1 = new Order();
        order1.setName("Sandwich");
        order1.setPrice(30);

        IOrder order2 = new Order();
        order2.setName("Water");
        order2.setPrice(10);

        iOrderSystem.addToCart(order);
        iOrderSystem.addToCart(order1);
        iOrderSystem.addToCart(order2);
        */

        System.out.println(iOrderSystem.calculateTotalAmount());
        System.out.println(iOrderSystem.categoryDiscounts());
        Map<String, Integer> cartItems = iOrderSystem.cartItems();
        for (String name : cartItems.keySet()) {
            System.out.println(name + " " + cartItems.get(name));
        }
    }
}

enum Category {
    UNKNOWN(Integer.MIN_VALUE, 0, 0, "UNKNOWN"),
    CHEAP (0, 10, 10, "Cheap"),
    MODERATE (10, 20, 20, "Moderate"),
    EXPENSIVE(20, Integer.MAX_VALUE, 30, "Expensive");

    final int minValue;
    final int maxValue;
    final int discount;
    final String categoryName;

    Category(int minValue, int maxValue, int discount, String categoryName) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.discount = discount;
        this.categoryName = categoryName;
    }
}

interface IOrder {
    String getName();
    void setName(String name);
    int getPrice();
    void setPrice(int price);
}

interface IOrderSystem {
    void addToCart(IOrder order);
    void removeFromCart(IOrder order);
    int calculateTotalAmount();
    Map<String, Integer> categoryDiscounts();
    Map<String, Integer> cartItems();
}

class Order implements IOrder {
    String name;
    int price;

    public Order() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }
}

class OrderSystem implements IOrderSystem {

    //private List<IOrder> cart = new ArrayList<>();
    List<IOrder> cart = new ArrayList<>();

    public OrderSystem() {
        //this.cart = new ArrayList<>();
    }

    @Override
    public void addToCart(IOrder order) {
        cart.add(order);
    }

    @Override
    public void removeFromCart(IOrder order) {
        cart.remove(order);
    }

    @Override
    public int calculateTotalAmount() {
        int total = 0;
        for (IOrder order : cart) {
            total += order.getPrice() - calculateDiscount(order);
            //System.out.println(order.getPrice() + "    " + calculateDiscount(order));
        }
        return total;
    }

    @Override
    public Map<String, Integer> categoryDiscounts() {
        Map<String, Integer> categoryDiscountMap = new TreeMap<>();
        //Map<String, Integer> categoryDiscountMap = new HashMap<>();
        for (IOrder order : cart) {
            Category category = getCategory(order);
            categoryDiscountMap.put(category.categoryName, categoryDiscountMap.getOrDefault(category.categoryName, 0) + calculateDiscount(order, category));
        }
        return categoryDiscountMap;
    }

    @Override
    public Map<String, Integer> cartItems() {
        Map<String, Integer> itemCountMap = new TreeMap<>();
        //Map<String, Integer> itemCountMap = new HashMap<>();
        for (IOrder order : cart) {
            itemCountMap.put(order.getName(), itemCountMap.getOrDefault(order.getName(), 0) + 1);
        }
        return itemCountMap;
    }

    private int calculateDiscount(IOrder order) {
        Category category = getCategory(order);
        return (order.getPrice()*category.discount)/100;
    }

    private int calculateDiscount(IOrder order, Category category) {
        return (order.getPrice()*category.discount)/100;
    }

    private Category getCategory(IOrder order) {
        for (Category category : Category.values()) {
            if (order.getPrice() > category.minValue && order.getPrice() <= category.maxValue)
                return category;
        }
        return Category.UNKNOWN;
    }
}


