package RentalService.domain;

public class Driver {
    String id;
    String name;

    public Driver(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return this.id;
    }
}
