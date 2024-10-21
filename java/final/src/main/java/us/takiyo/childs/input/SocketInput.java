package us.takiyo.childs.input;

import us.takiyo.RestaurantSim;
import us.takiyo.elements.HowToPlay;
import us.takiyo.extensions.SocketData;
import us.takiyo.utils.Utils;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;

public class SocketInput implements Runnable {
    private final RestaurantSim master;
    private final BufferedReader in;
    private final PrintWriter out;

    public SocketInput(RestaurantSim restaurantSim, BufferedReader in, PrintWriter out) {
        this.master = restaurantSim;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            String data;
            while ((data = in.readLine()) != null) {
                SocketData parsed = SocketData.parseData(data);
                if (parsed != null) {
                    switch (parsed.getOp()) {
                        case "setName": {
                            master.setOwnerName(parsed.getData());
                            break;
                        }

                        case "start": {
                            master.setStart(Objects.equals(parsed.getData(), "true"));
                            break;
                        }

                        case "htp": {
                            boolean show = Objects.equals(parsed.getData(), "show");
                            master.setStart(!show);
                            Utils.clearTerminal();
                            if (show) HowToPlay.printHowToPlay();
                            break;
                        }

                        case "exit": {
                            master.setStart(false);
                            HowToPlay.printExit(parsed.getData());
                            System.exit(1);
                        }

                        case "processExists":
                        case "restock":
                        case "getScore":
                        case "bake": {
                            if (parsed.getSessionId() != null)
                                master.getSocketQueue().put(parsed);
                            break;
                        }

                        default:
                            break;
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
