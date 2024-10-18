package us.takiyo.extensions;

import us.takiyo.interfaces.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class ViewExtension {
    public enum ViewType {
        NeedChoices,
        Normal
    }

    public List<View> options = new ArrayList<>();
    public ViewType type;
    int currentState = 0;

    public ViewExtension(ViewType type) {
        this.type = type;
    }

    private View getFirstOfOptions() {
        return options.isEmpty() ? null : options.get(0);
    }

    public void startHandle() {
        if (this.type == ViewType.Normal) {
            Objects.requireNonNull(this.getFirstOfOptions()).function.apply(currentState);
            return;
        }
        while (true) {
            this.clearTerminal();
            View state = this.getOptionWithState(currentState);
            if (state.extensionPage != null) {
                View firstState = state.extensionPage.getOptionWithState(0);
                if (firstState == null || state.extensionPage.type != ViewType.Normal) {
                    System.out.println("asoikjakdasdkasdad");
                    break;
                }
                state.extensionPage.startHandle();
                currentState = 0; // always back to home
            } else {
                String response = state.function.apply(currentState);
                //  0/1 target state
                // 0/1 not/break while
                if (response != null) {
                    if (!Objects.equals(response.substring(0, 1), "0")) {
                        System.out.println("chahing state from " + currentState);
                        currentState = Integer.parseInt(response.substring(0, 1));
                        System.out.println("to " + currentState);
                    }

                    if (Objects.equals(response.substring(1, 2), "1"))
                        break;
                }
            }
        }
    }

    private void clearTerminal() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }

    public View getOptionWithState(int state) {
        for (View view : options)
            if (view.choice == state)
                return view;
        return null;
    }
}
