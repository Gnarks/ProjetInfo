package code.projetinfo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
/**
 * Class used to represent a 2D array of CaseState.
 * See CaseState to see the states in which every case can be.
 * @see CaseState
 */
public class Cases {
    private CaseState[][] cases;

    /** Constructor of the Cases Class
     *
     * @param cols number of columns of the 2D array.
     * @param rows number of rows of the 2D array.
     */
    /**public Cases(int cols, int rows) {
        this(cols,rows,CaseState.EMPTY);
    }

    /** Constructor of the Cases Class with fill argument
     * to set all the Cases to the specified state
     *
     * @param cols number of columns of the 2D array.
     * @param rows number of rows of the 2D array.
     * @param fill the CasesState that will fill the 2D array.
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

    public Cases(CaseState[][] cases){
        this.cases = cases;
    }

    /** sets the case at the x and y position to
     * the specified CaseState.
     *
     * @param x row index
     * @param y col index
     * @param s CaseState to set
     */
    public void set(int x, int y, CaseState s) {
        this.cases[y][x] = s;
    }

    /** set the case at the x and y position to EMPTY.
     * @param x row index
     * @param y col index
     */
    public void remove(int x, int y) {
        this.cases[y][x] = CaseState.EMPTY;
    }

    /**
     * Getter for the state of a selected cell in matrix cases.
     * @param x
     * Column number of the wanted cell.
     * @param y
     * Row number of the wanted cell.
     * @return
     * The state of the selected Cell.
     */
    public CaseState getState(int x, int y){
        return this.cases[y][x];
    }

    /**
     * Getter for the number of rows in the matrix cases.
     * @return
     * The number of rows in the matrix cases.
     */
    public int getRow(){
        return this.cases.length;
    }

    /**
     * Getter for the number of columns in the matrix cases.
     * @return
     * The number of columns in the matrix cases.
     */
    public int getCol(){
        return this.cases[0].length;
    }

    public CaseState[][] getCases(){
        return cases;
    }

    //Test for load func
    public void setCases(CaseState[][] cs){
        this.cases = cs;
    }
}