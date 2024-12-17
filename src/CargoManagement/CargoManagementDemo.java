package CargoManagement;

import java.util.List;

public class CargoManagementDemo {
    public static void main(String[] args) {

        ICargoManager cargoManager = new CargoManager();

        IPackage iPackage = new Package();
        iPackage.setType(PackageType.STANDARD);
        iPackage.setWeight(3);
        iPackage.setLength(16);
        iPackage.setWidth(10);
        iPackage.setHeight(5);

        IPackage iPackage1 = new Package();
        iPackage1.setType(PackageType.HAZARDOUS);
        iPackage1.setWeight(2);
        iPackage1.setLength(14);
        iPackage1.setWidth(18);
        iPackage1.setHeight(7);

        IPackage iPackage2 = new Package();
        iPackage2.setType(PackageType.HAZARDOUS);
        iPackage2.setWeight(2);
        iPackage2.setLength(25);
        iPackage2.setWidth(15);
        iPackage2.setHeight(10);

        IPackage iPackage3 = new Package();
        iPackage3.setType(PackageType.FRAGILE);
        iPackage3.setWeight(3);
        iPackage3.setLength(20);
        iPackage3.setWidth(30);
        iPackage3.setHeight(11);

        TransportationInfo transportationInfo = cargoManager.estimateTransportationInfo(List.of(iPackage, iPackage1, iPackage2, iPackage3), 25);
        System.out.println("Total Space " + transportationInfo.space);
        System.out.println("Transportation Cost " + transportationInfo.transportationCost);
        System.out.println("Service Charge " + transportationInfo.serviceCharge);
        System.out.println("Total Cost " + transportationInfo.totalCost);
    }
}

interface IPackage {
    void setId(String id);
    void setType(PackageType type);
    void setWeight(int weight);
    void setLength(int length);
    void setWidth(int width);
    void setHeight(int height);
    String getId();
    PackageType getType();
    int getWeight();
    int getLength();
    int getWidth();
    int getHeight();
    int getVolume();
}

enum PackageType {
    STANDARD("Standard", 1.2, 0.05, 0.5),
    FRAGILE("Fragile", 1.5, 0.07, 0.75),
    HAZARDOUS("Standard", 1.25, 0.06, 0.625);

    final String displayName;
    final double spaceMultiplier;
    final double transportCostMultiplier;
    final double serviceChargeMultiplier;

    PackageType(String displayName, double spaceMultiplier, double transportCostMultiplier, double serviceChargeMultiplier) {
        this.displayName = displayName;
        this.spaceMultiplier = spaceMultiplier;
        this.transportCostMultiplier = transportCostMultiplier;
        this.serviceChargeMultiplier = serviceChargeMultiplier;
    }
}

class Package implements IPackage {
    String id;
    PackageType type;
    int weight;
    int length;
    int width;
    int height;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setType(PackageType type) {
        this.type = type;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public PackageType getType() {
        return type;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getVolume() {
        return length*width*height;
    }
}

class TransportationInfo {
    double space;
    double transportationCost;
    double serviceCharge;
    double totalCost;

    public TransportationInfo(double space, double transportationCost, double serviceCharge, double totalCost) {
        this.space = space;
        this.transportationCost = transportationCost;
        this.serviceCharge = serviceCharge;
        this.totalCost = totalCost;
    }
}

interface ICargoManager {
    TransportationInfo estimateTransportationInfo(List<IPackage> packages, int distance);
}

class CargoManager implements ICargoManager {

    @Override
    public TransportationInfo estimateTransportationInfo(List<IPackage> packages, int distance) {
        double space = 0d;
        double transportationCost = 0d;
        double serviceCharge = 0d;
        for (IPackage iPackage : packages) {
            space += getPackageSpace(iPackage);
            transportationCost += getPackageTransportationCost(iPackage);
            serviceCharge += getPackageServiceCharge(iPackage, distance);
        }
        return new TransportationInfo(space, transportationCost, serviceCharge, transportationCost + serviceCharge);
    }

    double getPackageSpace(IPackage iPackage) {
        return iPackage.getVolume()*iPackage.getType().spaceMultiplier;
    }

    double getPackageTransportationCost(IPackage iPackage) {
        return iPackage.getVolume()*iPackage.getType().transportCostMultiplier;
    }

    double getPackageServiceCharge(IPackage iPackage, int distance) {
        return (distance + iPackage.getWeight()) * iPackage.getType().serviceChargeMultiplier;
    }
}