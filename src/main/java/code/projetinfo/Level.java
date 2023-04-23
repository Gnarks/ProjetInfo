package code.projetinfo;

import java.util.ArrayList;

public class Level {
    private Cases grid;
    final private ImageBlock[] blocks;
    private ArrayList<ImageBlock> placed;

    public Level(Cases grid, ImageBlock[] blocs){
        this.grid = grid;
        this.blocks = blocs;
        this.placed = new ArrayList<ImageBlock>();
    }
    public void saveState(){}
    public void loadState(){}

    /**
     * This method check the grid to see if the block can be placed at the desired
     * coordinates.
     * @param imageBlock
     * The block to place
     * @param x
     * Horizontal coordinate of the top left of the block.
     * @param y
     * Vertical coordinate of the top left of the block.
     * @return
     * Return if the block can be placed or not.
     */
    public boolean isPlacable(ImageBlock imageBlock, int x, int y){
        if (x<0 || y<0){
            return false;
        }
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (x+j >= grid.getCol() || y+i >= grid.getRow() ||
                        grid.getState(x+j, y+i) != CaseState.EMPTY && imageBlock.getState(j, i) == CaseState.FULL){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPlaced(ImageBlock imageBlock) {
        return placed.contains(imageBlock);
    }



    /**
     * This method "place" a bloc in the grid matrix.
     * We just put the matrix bloc in a bigger matrix grid.
     * @param imageBlock
     * The matrix representing the bloc to place
     * @param x
     * The horizontal coordinate of the placement point of the bloc.
     * @param y
     * The vertical coordinate of the placement point of the bloc.
     */
    public void place(ImageBlock imageBlock, int x, int y){
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)
                    grid.set(x+j, y+i, imageBlock.getState(j, i));
            }
        }
        placed.add(imageBlock);
    }


    public void remove(ImageBlock imageBlock,int x,int y){
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)

                    grid.set(x+j, y+i, CaseState.EMPTY);
            }
        }
        placed.remove(imageBlock);

    }

    public void show(){
        System.out.println("\n");
        for (int i = 0; i < grid.getRow(); i++) {
            for (int j = 0; j < grid.getCol(); j++) {
                System.out.printf("%7s|", grid.getState(j,i));
            }
            System.out.println();
        }
    }

    public ImageBlock[] getBlocks() {
        return blocks;
    }

    public Cases getGrid() {return grid;}
}
