package code.projetinfo;

/** Represents the state of a single case.
 *
 * possible state:
 * {@link #EMPTY},
 * {@link #FULL},
 * {@link #SPECIAL}
 *
 */
public enum CaseState {
    /**
     * Represents an empty case.
     */
    EMPTY,
    /**
     * Represents a filled case.
     */
    FULL,
    /**
     * Represents an invisible case
     * on which nothing can be placed.
     */
    SPECIAL


}
