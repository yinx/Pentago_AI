import Model.BoardController;
import Model.FileReader;
import View.GUI;

/**
 * Created by Tom on 13/10/2015.
 */
public class GuiRun {
    public static void main(String[] args) {
        BoardController boardController = new BoardController(6, 6);
        FileReader fileReader = new FileReader();
        GUI gui = new GUI(boardController, fileReader);
        boardController.addListener(gui);
        fileReader.addListener(boardController);


    }
}
