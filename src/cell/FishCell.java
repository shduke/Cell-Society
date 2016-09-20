package cell;

public class FishCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;

    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime--;
    }

    private void breed () {

    }

}
