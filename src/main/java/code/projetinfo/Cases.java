package code.projetinfo;

/**
 * Class used to represent a 2-dimensional array of CaseState.
 * @see CaseState
 */
public class Cases {
    private final CaseState[][] cases;

    /** Default Constructor of the Cases class.
     *  Fils the Cases with CaseState.EMPTY.
     *
     * @param cols number of columns of the 2D array.
     * @param rows number of rows of the 2D array.
     */
    public Cases(int cols, int rows) {
        this(cols,rows,CaseState.EMPTY);
    }

    /** Constructor of the Cases Class specifying a CaseState to be filled with.
     * Sets all the CaseStates in the specified fill argument.
     *
     * @param cols number of columns of the 2D array.
     * @param rows number of rows of the 2D array.
     * @param fill the specified CasesState used to fill the 2D array.
     */
    public Cases(int cols, int rows, CaseState fill){
        CaseState[][] newCases = new CaseState[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newCases[i][j] = fill;
            }
        }
        this.cases = newCases;
    }

    /** Constructor of the Cases class specifying a 2D array of CaseState.
     *
     * @param cases the 2D array of CasesState to be cloned in Cases.
     */
    public Cases(CaseState[][] cases){
        if (cases.length == 0){
            this.cases = new CaseState[0][0];
            return;
        }
        CaseState[][] newCases = new CaseState[cases.length][cases[0].length];
        for (int i = 0; i < cases.length; i++) {
            System.arraycopy(cases[i], 0, newCases[i], 0, cases[i].length);
        }
        this.cases = newCases;
    }

    /** Sets the case at the x and y position to
     * the specified CaseState.
     *
     * @param x row index.
     * @param y column index.
     * @param s CaseState to set.
     */
    public void set(int x, int y, CaseState s) {
        this.cases[y][x] = s;
    }

    /** Returns the CasesState at the (x,y) position
     *  in the 2D array.
     *
     * @param x column index.
     * @param y row index.
     * @return the CaseState of the selected Cell.
     */
    public CaseState getState(int x, int y){
        return this.cases[y][x];
    }

    /** Returns the number of rows in the 2D array.
     *
     * @return The number of rows in the 2D array.
     */
    public int getRow(){
        return this.cases.length;
    }

    /** Returns the number of columns in the 2D array.
     *
     * @return The number of columns in the 2D array.
     */
    public int getCol(){
        return this.cases[0].length;
    }


    /** Returns the 2D array of CaseState of the Cases instance.
     *
     * @return the 2D array of CasesState.
     */
    public CaseState[][] getCases(){
        return cases;
    }

    /** Compare the actual Cases with the externalCases.
     *
     * @param externalCases the other Cases to be compared with.
     * @return <code>true</code> if the two Cases have equals CaseState 2D array.
     * <code>false</code> otherwise.
     */
    public boolean equals(CaseState[][] externalCases){
        if (externalCases.length == 0 && cases.length == 0)
            return true;

        if (externalCases.length != cases.length || externalCases[0].length != cases[0].length)
            return false;
        for (int i = 0; i < externalCases.length; i++){
            for (int j = 0; j < externalCases[0].length; j++){
                if (getState(j, i) != externalCases[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints the 2D array in the console.
     */
    public void show(){
        System.out.println("\n");
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                System.out.printf("%7s|", getState(j,i));
            }
            System.out.println();
        }
    }
}