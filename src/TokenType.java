/**
 * Created by Tom on 24/09/2015.
 */
public enum TokenType {
    BLACK("B"), WHITE("W"), Empty(" ");

    private String colour;
    TokenType(String colour) {
        this.colour = colour;
    }

    public String getColour() {

        return colour;
    }


    public void paint(){
        System.out.print(getColour());
    }
}
