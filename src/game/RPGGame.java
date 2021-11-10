package game;

import controller.BoardController;
import controller.BoardControllerImpl;
import controller.TeamController;
import controller.TeamControllerImpl;
import model.BoardModel;
import state.Context;
import view.BoardView;

/**
 * abstract class for all rpg game
 */
public abstract class RPGGame {
    protected BoardController boardController;
    protected TeamController teamController;

    protected Context context;

    public RPGGame(int row, int column) {
        boardController = new BoardControllerImpl(new BoardView(), new BoardModel(row, column));
        teamController = new TeamControllerImpl();

        context = new Context(this);
        initGame();
    }

    public BoardController getBoardController() {
        return boardController;
    }

    public TeamController getTeamController() {
        return teamController;
    }

    public abstract void start();

    protected abstract void initGame();

    protected abstract boolean isEnd();

    public abstract void quit();
}
