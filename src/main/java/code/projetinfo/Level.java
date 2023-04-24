package code.projetinfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Level {
    private Cases grid;
    private ImageBlock[] blocks;
    private ImageBlock[] placed;
    private String name;
    //Uses for json handling move it later to a higher place in object hierarchy
    private File f = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\levels.json");
    private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    public Level(String name, Cases grid, ImageBlock[] blocs){
        this.grid = grid;
        this.blocks = blocs;
        this.placed = new ImageBlock[blocs.length];
        this.name = name;
    }

    public void saveState() throws IOException {
        JsonNode jsonData = mapper.readTree(f);
        ObjectNode levels = (ObjectNode) jsonData;

        ObjectNode newNode = mapper.createObjectNode();
        JsonNode gridNode = mapper.convertValue(this.grid.getCases(), JsonNode.class);
        newNode.put("grid", gridNode);

        ObjectNode blocklist = mapper.createObjectNode();
        for (int i = 0; i < this.blocks.length; ++i){
            ObjectNode block = mapper.createObjectNode();
            block.put("type", blocks[i].getClass().toString());
            block.put("rotatestate", blocks[i].getRotateState());

            block.put("MidX", this.blocks[i].getMidX());
            block.put("MidY", this.blocks[i].getMidY());
            blocklist.put(String.valueOf(i), block);
        }
        newNode.put("blocklist", blocklist);
        levels.put(name, newNode);
        mapper.writeValue(f, levels);
    }
    public void loadState(String name) throws IOException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //jsonData = root node
        JsonNode jsonData = mapper.readTree(f);

        //Handles the grid importing
        JsonNode nodeFinder = jsonData.path(name).path("grid");
        CaseState[][] testcases = mapper.treeToValue(nodeFinder, CaseState[][].class);
        this.grid.setCases(testcases);

        nodeFinder = jsonData.path(name).path("blocklist");
        ImageBlock[] jsonBlocks = new ImageBlock[nodeFinder.size()];

        int count = nodeFinder.size();
        for (int i = 0; i < count; i++){
            nodeFinder = jsonData.path(name).path("blocklist").path(String.valueOf(i)).path("type");
            //remove the class prefix to prevent a bug
            String classString = mapper.treeToValue(nodeFinder, String.class);
            classString = classString.substring(6, classString.length());
            Class current = Class.forName(classString);
            nodeFinder = jsonData.path(name).path("blocklist").path(String.valueOf(i)).path("position");
            jsonBlocks[i] = (ImageBlock) current.getDeclaredConstructor(Position.class).newInstance(new Position(25, 5));
        }
        this.blocks = jsonBlocks;
    }

    public void initJSON() throws IOException {

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
    public boolean isPlacable(ImageBlock imageBlock, int x, int y){
        if (x + imageBlock.getCols() > grid.getCol() || y + imageBlock.getRows() > grid.getRow()){
            return false;
        }
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (grid.getState(x+j, y+i) == CaseState.FULL && imageBlock.getState(j, i) == CaseState.FULL){
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
        if (isPlacable(imageBlock, x ,y)){
            for (int i = 0; i < imageBlock.getRows(); i++){
                for (int j = 0; j < imageBlock.getCols(); j++){
                    grid.set(x+j, y+i, imageBlock.getState(j, i));
                }
            }
            append(imageBlock);
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

    public ImageBlock[] getBlocks() {
        return blocks;
    }

    public Cases getGrid() {return grid;}

}
