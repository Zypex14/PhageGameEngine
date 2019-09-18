package PhageEngine;

public class Button {

    private boolean inter;

    public Button(){
        inter = false;
    }

    public boolean get(boolean statement){

        if(statement && !inter){
            inter = true;
            return true;
        } else if(!statement){
            inter = false;
            return false;
        }

        return false;
    }

}
