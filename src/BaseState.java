import java.util.concurrent.TimeUnit;

/**
 * Base class for state pattern
 */
public abstract class BaseState implements State {
    @Override
    public void doAction(Context context, String action) {
        action = action.toUpperCase();
        switch (action) {
            case "I" -> context.addState(new ItemState());
            case "Q" -> context.getRpgGame().quit();
            case "M" -> {
                context.getRpgGame().getBoardController().show();
                // wait for 2 seconds
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
