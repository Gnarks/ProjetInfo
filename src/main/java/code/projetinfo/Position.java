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
public class Position {
    private double x;
    private double y;

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
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
}

