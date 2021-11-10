package state;

/**
 * state.State pattern
 */
public interface State {
    void doAction(Context context, String action);

    void showPrompt(Context context);
}
