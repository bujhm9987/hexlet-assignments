package exercise;

// BEGIN
public class Cottage implements Home{

    private double area;
    private Integer floorCount;

    public Cottage(double area, Integer floorCount) {
        this.area = area;
        this.floorCount = floorCount;
    }

    public double getArea() {
        return area;
    }

    public String toString() {
        return floorCount + " этажный коттедж площадью " + getArea() + " метров";
    }

    public byte compareTo(Home another) {
        if (this.getArea() > another.getArea()) {
            return 1;
        } else if (this.getArea() < another.getArea()) {
            return -1;
        } else {
            return 0;
        }
    }

}
// END
