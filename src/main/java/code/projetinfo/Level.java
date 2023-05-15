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
    private boolean isNew;
    private int placed = 0;
    private String name;
    private final String pathName = System.getProperty("user.dir")+"<src<main<resources<code<projetinfo<levels.json";
    private final File f = new File(pathName.replaceAll("<", "\\"+System.getProperty("file.separator")));
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    public Level(String name, Cases grid, ImageBlock[] blocs){
        this.grid = grid;
        this.blocks = blocs;
        this.name = name;
        this.isNew = true;
    }

    public Level(String name) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.name = name;
        this.isNew = false;
        loadState(name);
    }

    /**
     * Save all the data of a Level instance in the levels.json file with the name of the level.
     * If the level is new, will save the grid (even the FULL).
     * If the level isn't new, will save only the position of the blocks and not change the initial grid.
     *
     * @throws IOException
     */
    public void saveState() throws IOException {
        JsonNode jsonData = mapper.readTree(f);
        ObjectNode levels = (ObjectNode) jsonData;

        ObjectNode newNode = mapper.createObjectNode();
        JsonNode gridNode;

        if (isNew) {
            gridNode = mapper.convertValue(this.grid.getCases(), JsonNode.class);
        }else{
            JsonNode nodeFinder = jsonData.path(name).path("grid");
            CaseState[][] externCases = mapper.treeToValue(nodeFinder, CaseState[][].class);
            gridNode = mapper.convertValue(externCases, JsonNode.class);
        }
        newNode.set("grid", gridNode);


        ObjectNode blockList = mapper.createObjectNode();
        for (int i = 0; i < this.blocks.length; ++i){
            ObjectNode block = mapper.createObjectNode();
            block.put("type", blocks[i].getClass().toString());
            block.put("rotateState", blocks[i].getRotateState());

            block.put("layoutX", this.blocks[i].getLayoutX());
            block.put("layoutY", this.blocks[i].getLayoutY());

            block.put("isPlaced", this.blocks[i].getPlacedState());
            blockList.set(String.valueOf(i), block);
        }


        newNode.set("blockList", blockList);
        newNode.put("placed", this.placed);
        levels.set(this.name, newNode);
        mapper.writeValue(f, levels);
    }

    /**
     * Replace all the data of the calling Level instance by the data stored in the name field of levels.json
     * @param name The name of the level to load from levels.json
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
        this.grid = new Cases(externCases);

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
            double midX = mapper.treeToValue(nodeFinder, double.class);

            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("layoutY");
            double midY = mapper.treeToValue(nodeFinder, double.class);

            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("rotateState");
            int rotateState = mapper.treeToValue(nodeFinder, int.class);

            nodeFinder = jsonData.path(name).path("blockList").path(String.valueOf(i)).path("isPlaced");
            boolean placedState = mapper.treeToValue(nodeFinder, boolean.class);

            //Construct a Block with the imported block attributes and set if he is placed or not
            jsonBlocks[i] = (ImageBlock) current.getDeclaredConstructor(Position.class).newInstance(new Position(midX, midY));
            jsonBlocks[i].setPlaced(placedState);
            jsonBlocks[i].rotateTo(rotateState);
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
        this.placed++;
        imageBlock.setPlaced(true);
    }

    /**
     * Remove a block from a selected cell in the inner matrix.
     * It basically just runs through the bloc matrix and set the matching index to Empty.
     * @param imageBlock The block to remove.
     * @param x coordinate of the upper left corner of the block to remove
     * @param y coordinate of the upper left corner of the block to remove
     */
    public void remove(ImageBlock imageBlock,int x,int y){
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)
                    grid.set(x+j, y+i, CaseState.EMPTY);
            }
        }
        this.placed--;
        imageBlock.setPlaced(false);
    }

    public void show(){
        grid.show();
    }

    public ImageBlock[] getBlocks() {
        return blocks;
    }

    public Cases getGrid() {return grid;}
    public void setPlaced(int n){
        this.placed = n;
    }

    public void setName(String levelName){
        this.name = levelName;
    }

    public int getPlaced(){
        return placed;
    }

    public String getName(){
        return this.name;
    }

    /**
     * Equals method for Level class.
     * @param externalLevel The level instance to compare with the calling instance of Level.
     * @return If all the data of this and externalLevel are equal.
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

