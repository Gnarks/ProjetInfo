package code.projetinfo;

public class Level {
    private Cases grid;
    private ImageBlock[] blocks;
    private ImageBlock[] placed;

    public Level(Cases grid, ImageBlock[] blocs){
        this.grid = grid;
        this.blocks = blocs;
        this.placed = new ImageBlock[blocs.length];
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
        if(x>= grid.getCol()||y >= grid.getRow()){
            return true;
        }
        if(x<0||y<0){
            if(x+imageBlock.getCols()>0 && y + imageBlock.getRows() >0){
                return false;
            }
            return true;
        }
        if (x + imageBlock.getCols() > grid.getCol() || y + imageBlock.getRows() > grid.getRow()){
            return false;
        }

        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if ((grid.getState(x+j, y+i) == CaseState.FULL && imageBlock.getState(j, i) == CaseState.FULL)||(grid.getState(x+j, y+i) == CaseState.SPECIAL && imageBlock.getState(j, i) == CaseState.FULL)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPlaced(ImageBlock imageBlock) {
        for (int i = 0; i < placed.length ; i++) {
            if(placed[i]!= null){
            if(placed[i].equals(imageBlock)){
                return true;}
            }
        }
        return false;
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
        if (isPlacable(imageBlock, x ,y)&& x< grid.getCol()&& y< grid.getRow()&&x>=0&&y>=0){
            for (int i = 0; i < imageBlock.getRows(); i++){
                for (int j = 0; j < imageBlock.getCols(); j++){
                    grid.set(x+j, y+i, imageBlock.getState(j, i));
                }
            }
            append(imageBlock);
        }
    }


    public void remove(ImageBlock imageBlock,int x,int y){
        if (isPlaced(imageBlock)){
            for (int i = 0; i < imageBlock.getRows(); i++){
                for (int j = 0; j < imageBlock.getCols(); j++){
                    grid.set(x+j, y+i, CaseState.EMPTY);
                }
            }
            unPlace(imageBlock);
        }
    }

    public void show(){
        this.grid.show();
    }

    /**
     * Add a block in the placed bloc matrix.
     * @param imageBlock
     * The block to add in the placed bloc matrix.
     */
    private void append(ImageBlock imageBlock){
        int i = 0;
        while (placed[i] != null){
            i++;
        }
        placed[i] = imageBlock;
    }


    private void unPlace(ImageBlock imageBlock){
        int i = 0;
        while (!placed[i].equals(imageBlock)) {
            i++;
        }
        placed[i]= null;
    }

    public ImageBlock[] getBlocks() {
        return blocks;
    }

    public Cases getGrid() {return grid;}
}
