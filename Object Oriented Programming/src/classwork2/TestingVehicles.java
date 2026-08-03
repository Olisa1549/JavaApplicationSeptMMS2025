
package classwork2;

public class TestingVehicles {
    public static void main (String[] args) {
        Vehicle[] vehicleObject = {
            new Car(),
            new Bike(),
            new Bus()
        };
        for (Vehicle vehicle :vehicleObject){
            vehicle.move();
}
    }
    
}
