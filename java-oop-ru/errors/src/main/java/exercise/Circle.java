package exercise;

// BEGIN
public class Circle {
    private Point center;
    private int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public Integer getRadius() {
        return radius;
    }

    public Double getSquare() throws NegativeRadiusException {
        Double square = null;
        if (radius < 0) {
            throw new NegativeRadiusException("Не удалось посчитать площадь");
        } else {
            square = Math.PI * radius * radius;
        }
        return square;
    }
}
// END
