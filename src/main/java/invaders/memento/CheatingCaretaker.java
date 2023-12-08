package invaders.memento;

/**
 * caretaker for the cheatingSnapshot. only stores one instance of the cheatingSnapshot
 */
public class CheatingCaretaker {
    private CheatingSnapshot cheatingSnapshot;

    public void saveSnapshot(CheatingSnapshot cheatingSnapshot){
        this.cheatingSnapshot = cheatingSnapshot;
    }

    public CheatingSnapshot retrieveSnapshot(){
        return this.cheatingSnapshot;
    }
}
