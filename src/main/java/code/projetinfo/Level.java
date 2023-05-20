package code.projetinfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Class representing a level to be played.
 */
public class Level {
    /**
     * The updated Cases representing the grid of the Level.
     */
    private Cases grid;
    /**
     * The array of blocks to be placed in the level.
     */
    private ImageBlock[] blocks;
    /**
     * The counter of placed blocks on the grid.
     */
    private int placed = 0;
    /**
     * The name of the level, used to save the level in the json file.
     */
    private String name;
    /**
     * The absolute path to the directory of the project.
     */
    private final String pathName = System.getProperty("user.dir")+"<src<main<resources<code<projetinfo<levels.json";
    /**
     * The File class representing the levels.json file.
     */
    private final File f = new File(pathName.replaceAll("<", "\\"+System.getProperty("file.separator")));

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * The initial grid to be saved and retrieved in the save and load methods.
     */
    private Cases initialGrid;

    /** Default Constructor for Level.
     *
     * @param name the name of the level.
     * @param grid the Cases representing the grid of the level.
     * @param blocs a list with all the blocks to be placed to finish the level.
     */
    public Level(String name, Cases grid, ImageBlock[] blocs){
        this.grid = new Cases(grid.getCases());
        this.blocks = blocs;
        this.name = name;
        this.initialGrid = new Cases(grid.getCases());
    }

    /**This constructor load the level with the specified name from the json file.
     *
     *  @param name The name of the level to load from the json file.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Level(String name) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.name = name;
        loadState(name);
    }

    /**
     * Saves all the data of a level in the levels.json file with the name of the level.
     * *
     * @throws IOException
     */
    public void saveState() throws IOException {
        JsonNode jsonData = mapper.readTree(f);
        ObjectNode levels = (ObjectNode) jsonData;

        ObjectNode newNode = mapper.createObjectNode();
        JsonNode gridNode = mapper.convertValue(this.initialGrid.getCases(), JsonNode.class);
        newNode.set("grid", gridNode);


        ObjectNode blockList = mapper.createObjectNode();
        for (int i = 0; i < this.blocks.length; ++i){
            ObjectNode block = mapper.createObjectNode();
            block.put("type", blocks[i].getClass().toString());
            block.put("rotateState", blocks[i].getRotateState());

            if (blocks[i].getPlacedState())
                blocks[i].rotateTo(0);

            block.put("layoutX", this.blocks[i].getLayoutX());
            block.put("layoutY", this.blocks[i].getLayoutY());

            block.put("isPlaced", this.blocks[i].getPlacedState());
            blockList.set(String.valueOf(i), block);
        }


        newNode.set("blockList", blockList);
        levels.set(this.name, newNode);
        mapper.writeValue(f, levels);
    }

    /** Replace all the data of the level by the data stored in the saved level
     * with the specified name.
     *
     * @param name The name of the level to load from the levels.json file.
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void loadState(String name) throws IOException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //jsonData = root node
        JsonNode jsonData = mapper.readTree(f);

        //Handles the grid importing
        JsonNode nodeFinder = jsonData.path(name).path("grid");
        CaseState[][] externCases = mapper.treeToValue(nodeFinder, CaseState[][].class);
        this.grid = new Cases(externCases.clone());
        this.initialGrid = new Cases(externCases.clone());


        nodeFinder = jsonData.path(name).path("blockList");
        ImageBlock[] jsonBlocks = new ImageBlock[nodeFinder.size()];

        int count = nodeFinder.size();
        for (int i = 0; i < count; i++){
            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("type");
            //remove the class prefix to prevent a bug
            String classString = mapper.treeToValue(nodeFinder, String.class);
            classString = classString.substring(6);
            Class<?> current = Class.forName(classString);

            //get the position of each saved blocks
            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("layoutX");
            double layoutX = mapper.treeToValue(nodeFinder, double.class);

            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("layoutY");
            double layoutY = mapper.treeToValue(nodeFinder, double.class);

            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("rotateState");
            int rotateState = mapper.treeToValue(nodeFinder, int.class);

            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("isPlaced");
            boolean placedState = mapper.treeToValue(nodeFinder, boolean.class);

            //Construct a Block with the imported block attributes and set if he is placed or not
            jsonBlocks[i] = (ImageBlock) current.getDeclaredConstructor(Position.class).newInstance(new Position(layoutX, layoutY));

            jsonBlocks[i].setPlaced(placedState);
            if (jsonBlocks[i].getPlacedState()){
                jsonBlocks[i].rotateTo(rotateState);
                jsonBlocks[i].setRotateState(rotateState);
            }
        }
        this.blocks = jsonBlocks;
    }
    /** Checks if the specified ImageBlock can be placed at the (x,y) position
     * in the grid.
     * @param imageBlock the ImageBlock to check with.
     * @param x the column index of the grid.
     * @param y the row index of the grid.
     * @return if the ImageBlock can be placed or not.
     */
    public boolean isPlaceable(ImageBlock imageBlock, int x, int y) {
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


    /** Places the specified ImageBlock at the (x,y) Position in the grid.
     *
     * @param imageBlock the ImageBlock to be placed.
     * @param x the column index of the grid.
     * @param y the row index of the grid.
     */
    public void place(ImageBlock imageBlock, int x, int y){
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)
                    grid.setState(x+j, y+i, imageBlock.getState(j, i));
            }
        }
        this.placed++;
        imageBlock.setPlaced(true);
    }

    /** Removes the specified ImageBlock of the grid.
     *
     * @param imageBlock the ImageBlock to remove.
     * @param x Position of the left corner of the block to remove.
     * @param y Position of the upper corner of the block to remove.
     */
    public void remove(ImageBlock imageBlock,int x,int y){
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)
                    grid.setState(x+j, y+i, CaseState.EMPTY);
            }
        }
        this.placed--;
        imageBlock.setPlaced(false);
    }

    /**Returns the array of ImageBlocks to be placed in the level.
     *
     * @return the array of ImageBlocks to be placed in the level.
     */
    public ImageBlock[] getBlocks() {
        return blocks;
    }

    /** Returns the Cases representing the current grid.
     *
     * @return the Cases representing the current grid.
     */
    public Cases getGrid() {return grid;}

    /** Sets the number of placed blocks to the specified value.
     *
     * @param n the number of placed blocks to be set.
     */
    public void setPlaced(int n){
        this.placed = n;
    }

    /**Sets the level's name to the specified name.
     *
     * @param levelName the name of the level to be set.
     */
    public void setName(String levelName){
        this.name = levelName;
    }

    /**Returns the number of blocks placed on the level.
     *
     * @return the number of blocks placed on the level.
     */
    public int getPlaced(){
        return placed;
    }

    /**Returns the current name.
     *
     * @return the name of the level.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the list of placed blocks a specified list.
     *
     * @param blocks the list of blocks to be set
     */
    public void setBlocks(ImageBlock[] blocks) {
        this.blocks = blocks;
    }

    /**
     * Sets a new grid to the level.
     *
     * @param grid the grid to be set
     */
    public void setGrid(Cases grid) {
        this.grid = grid;
    }

    /**
     * Equals method for Level class.
     * @param externalLevel The level instance to compare with the calling instance of Level.
     * @return If all the data of this and externalLevel are equal or not.
     */
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

