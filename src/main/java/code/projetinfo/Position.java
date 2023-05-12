package code.projetinfo;
/**
 * Position is an object which defines a position according to
 * -a horizontal axe, which starts on left
 * and ends on right.
 * -And according to a vertical axe,
 * which starts on top
 * and ends on bottom.
 *
 */
public class Position implements Comparable<Position>{
    private final double x;
    private final double y;

    /**
     * @param x  the object's horizontal position
     * @param y  the object's vertical position
     */
    public Position(double x,double y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the object's horizontal position
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return the object's vertical position
     */
    public double getY() {
        return y;
    }

    public boolean equals(Position other){
        return other.getX() == this.getX() && other.getY() == this.getY();
    }

    public String toString(){
        return String.format("(%s, %S)",x,y);
    }

    @Override
    public Position clone(){
        return new Position(x,y);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(Position o) {
        if (this.equals(o))
            return 0;
        if (x + y > o.getX() + o.getY())
            return 1;
        else
            return -1;
    }
}

