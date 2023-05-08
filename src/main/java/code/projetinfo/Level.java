package code.projetinfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Level {
    private Cases grid;
    private ImageBlock[] blocks;
    private int placed = 0;
    private String name;
    private String pathname = System.getProperty("user.dir")+"<src<main<resources<levels.json";
    private final File f = new File(pathname.replaceAll("<", "\\"+System.getProperty("file.separator")));
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    public Level(String name, Cases grid, ImageBlock[] blocs){
        this.grid = grid;
        this.blocks = blocs;
        this.name = name;
    }

    public Level(String name) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.name = name;
        loadState(name);
    }

    public void saveState() throws IOException {
        JsonNode jsonData = mapper.readTree(f);
        ObjectNode levels = (ObjectNode) jsonData;

        ObjectNode newNode = mapper.createObjectNode();
        JsonNode gridNode = mapper.convertValue(this.grid.getCases(), JsonNode.class);
        newNode.set("grid", gridNode);

        ObjectNode blocklist = mapper.createObjectNode();
        for (int i = 0; i < this.blocks.length; ++i){
            ObjectNode block = mapper.createObjectNode();
            block.put("type", blocks[i].getClass().toString());
            block.put("rotatestate", blocks[i].getRotateState());

            //if the block is already placed save is position if not save (0,0) (useful for precondition in dispatchBlocks()

            block.put("GridX", this.blocks[i].getGridPos().getX());
            block.put("GridY", this.blocks[i].getGridPos().getY());


            block.put("isplaced", this.blocks[i].getPlacedState());
            blocklist.set(String.valueOf(i), block);
        }


        newNode.set("blocklist", blocklist);
        newNode.put("placed", this.placed);
        levels.set(name, newNode);
        mapper.writeValue(f, levels);
    }
    public void loadState(String name) throws IOException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //jsonData = root node
        JsonNode jsonData = mapper.readTree(f);

        //Handles the grid importing
        JsonNode nodeFinder = jsonData.path(name).path("grid");
        CaseState[][] testcases = mapper.treeToValue(nodeFinder, CaseState[][].class);
        this.grid = new Cases(testcases);

        nodeFinder = jsonData.path(name).path("blocklist");
        ImageBlock[] jsonBlocks = new ImageBlock[nodeFinder.size()];

        int count = nodeFinder.size();
        for (int i = 0; i < count; i++){
            nodeFinder = jsonData.path(name).path("blocklist").path(String.valueOf(i)).path("type");
            //remove the class prefix to prevent a bug
            String classString = mapper.treeToValue(nodeFinder, String.class);
            classString = classString.substring(6);
            Class<?> current = Class.forName(classString);

            //get the position of each saved blocks
            nodeFinder = jsonData.path(name).path("blocklist").path(String.valueOf(i)).path("GridX");
            int gridX = mapper.treeToValue(nodeFinder, int.class);

            nodeFinder = jsonData.path(name).path("blocklist").path(String.valueOf(i)).path("GridY");
            int gridY = mapper.treeToValue(nodeFinder, int.class);

            nodeFinder = jsonData.path(name).path("blocklist").path(String.valueOf(i)).path("isplaced");
            boolean placedstate = mapper.treeToValue(nodeFinder, boolean.class);

            //Construct a Block with the imported block attributes and set if he is placed or not
            jsonBlocks[i] = (ImageBlock) current.getDeclaredConstructor(Position.class).newInstance(new Position(0, 0));
            jsonBlocks[i].setGridPos(gridX, gridY);
            jsonBlocks[i].setPlaced(placedstate);
        }
        this.blocks = jsonBlocks;
    }
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
    public boolean isPlacable(ImageBlock imageBlock, int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        for (int i = 0; i < imageBlock.getRows(); i++) {
            for (int j = 0; j < imageBlock.getCols(); j++) {
                if (x + j >= grid.getCol() || y + i >= grid.getRow() ||
                        grid.getState(x + j, y + i) != CaseState.EMPTY && imageBlock.getState(j, i) == CaseState.FULL) {
                    return false;
                }
            }
        }
        return true;
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
        imageBlock.getGridPos().setPos(x, y);
        this.placed++;
        imageBlock.setPlaced(true);
    }

    public void remove(ImageBlock imageBlock,int x,int y){
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)
                    grid.set(x+j, y+i, CaseState.EMPTY);
            }
        }
        imageBlock.getGridPos().setPos(-1, -1);
        this.placed--;
        imageBlock.setPlaced(false);
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
    public void setPlaced(int n){
        this.placed = n;
    }

    public int getPlaced(){
        return placed;
    }

    public String getName(){
        return this.name;
    }


    //This method is only usefull for unit tests
    public boolean equals(Level externalLevel){
        if (!name.equals(externalLevel.getName())){
            return false;
        }
        if (!grid.equals(externalLevel.getGrid().getCases())){
            return false;
        }
        if (blocks.length != externalLevel.getBlocks().length){
            return false;
        }
        for (int i = 0; i < blocks.length; i++){
            if ((   blocks[i].getClass() != externalLevel.getBlocks()[i].getClass())||
                    blocks[i].getRotateState() != externalLevel.getBlocks()[i].getRotateState() ||
                    blocks[i].getLayoutX() != externalLevel.getBlocks()[i].getLayoutX() ||
                    blocks[i].getLayoutY() != externalLevel.getBlocks()[i].getLayoutY() ||
                    blocks[i].getPlacedState() != externalLevel.getBlocks()[i].getPlacedState())
            {
                return false;
            }
        }
        return true;
    }
}

