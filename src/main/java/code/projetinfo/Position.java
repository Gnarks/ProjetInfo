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

    /**Default constructor for Position.
     * Sets (x,y) as a Position.
     *
     * @param x  the object's horizontal position.
     * @param y  the object's vertical position.
     */
    public Position(double x,double y){
        this.x = x;
        this.y = y;
    }

     /**Returns the object's horizontal position
     *
     * @return the object's horizontal position
     */
    public double getX() {
        return x;
    }

     /**Returns the object's vertical position
     *
     * @return the object's vertical position
     */
    public double getY() {
        return y;
    }

    /** Returns if the specified Position is equal to the current Position.
     *
     * @param other the other Position to check with.
     * @return if the other Position and the current Position have the same data.
     */
    public boolean equals(Position other){
        return other.getX() == this.getX() && other.getY() == this.getY();
    }

    /**Returns a string representation.
     *
     * @return a String representing.
     */
    public String toString(){
        return String.format("(%s, %S)",x,y);
    }

    /** Returns the cloned position.
     *
     * @return a new Position with the same data.
     */
    @Override
    public Position clone(){
        return new Position(x,y);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param o the Position to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
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

