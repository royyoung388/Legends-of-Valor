import java.util.Stack;

/**
 * Used for state pattern.
 * Contains a reference to game
 */
public class Context {
    private Stack<State> state;
    private RPGGame rpgGame;

    public Context(RPGGame rpgGame) {
        this.rpgGame = rpgGame;
        state = new Stack<>();
    }

    public void addState(State state) {
        this.state.add(state);
    }

    public void popState() {
        this.state.pop();
    }

    public State getState() {
        return state.peek();
    }

    public void setRpgGame(RPGGame rpgGame) {
        this.rpgGame = rpgGame;
    }

    public RPGGame getRpgGame() {
        return rpgGame;
    }
}