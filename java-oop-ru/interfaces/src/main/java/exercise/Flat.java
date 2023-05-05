package exercise;

// BEGIN
public class Flat implements Home{

    private double area;
    private double balconyArea;
    private Integer floor;

    public Flat(double area, double balconyArea, Integer floor) {
        this.area = area;
        this.balconyArea = balconyArea;
        this.floor = floor;
    }

    public double getArea() {
        return area + balconyArea;
    }

    public String toString() {
        return "Квартира площадью " + getArea() + " метров на " + floor + " этаже";
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
