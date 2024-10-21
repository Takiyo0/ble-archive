package us.takiyo.childs.output;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;

import us.takiyo.RestaurantSim;
import us.takiyo.elements.HowToPlay;
import us.takiyo.extensions.SocketData;
import us.takiyo.utils.Utils;

public class SocketOutput implements Runnable {
    private final RestaurantSim master;
    private final BufferedReader in;
    private final PrintWriter out;

    public SocketOutput(RestaurantSim restaurantSim, BufferedReader in, PrintWriter out) {
        this.master = restaurantSim;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            String data;
            while ((data = this.in.readLine()) != null) {
                SocketData parsed = this.parseData(data);
                if (parsed != null) {
                    switch (parsed.getOp()) {
                        case "start": {
                            String[] datas = parsed.getData().split("#");
                            master.setStart(Objects.equals(datas[0], "true"));
                            if (datas.length > 1 && !datas[1].isEmpty()) master.setOwnerName(datas[1]);
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
                            HowToPlay.printExit(master.getScore() + "");
                            System.exit(1);
                        }

                        case "processExists": {
                            if (parsed.getSessionId() == null)
                                break;
                            int parsedInt = Integer.parseInt(parsed.getData()) - 1;
                            boolean exists = master.getOrders().size() > parsedInt && master.getOrders().get(parsedInt) != null;
                            String addData = exists ? master.getOrders().get(parsedInt).getIngredients() + "##" + master.getOrders().get(parsedInt).haveEnoughIngredients(master) : null;
                            out.println("processExists#%" + (exists ? addData : "false") + "#%" + parsed.getSessionId());
                            break;
                        }

                        case "bake": {
                            int parsedInt = Integer.parseInt(parsed.getData()) - 1;
                            if (master.getOrders().size() > parsedInt && master.getOrders().get(parsedInt) != null) {
                                String res = master.getOrders().get(parsedInt).bake(master);
                                master.getOrders().remove(master.getOrders().get(parsedInt));
                                out.println("bake#%" + res + "#%" + parsed.getSessionId());
                            }
                            break;
                        }

                        case "getScore": {
                            out.println("getScore#%" + master.getScore() + "#%" + parsed.getSessionId());
                            break;
                        }

                        case "restock": {
                            if (master.getScore() < 30)
                                out.println("restock#%" + "You don't have enough money! ($30 for restock) Please serve the orders first" + "#%" + parsed.getSessionId());
                            else {
                                master.restockIngredients();
                                out.println("restock#%" + "Restocked all the ingredients" + "#%" + parsed.getSessionId());
                            }
                        }

                        default:
                            break;
                    }
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    private SocketData parseData(String data) {
        try {
            String[] datas = data.split("#%");
            return new SocketData(datas[0], datas[1], datas.length > 2 ? datas[2] : null);
        } catch (Exception ignored) {
            return null;
        }
    }
}
