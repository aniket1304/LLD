package RentalService.manager;

import RentalService.domain.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    Map<String, Driver> driverMap = new HashMap<>();

    public void addDriver(Driver driver) {
        if (driverMap.containsKey(driver.getId()))
            return;
        driverMap.put(driver.getId(), driver);
    }

    public Driver getDriver(String id) {
        if (!driverMap.containsKey(id))
            return null;
        return driverMap.get(id);
    }
}
