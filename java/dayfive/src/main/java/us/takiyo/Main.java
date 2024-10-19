package us.takiyo;

import us.takiyo.characters.MageBase;
import us.takiyo.characters.RogueBase;
import us.takiyo.characters.WarriorBase;
import us.takiyo.controller.Character;
import us.takiyo.controller.Enemy;
import us.takiyo.controller.Player;
import us.takiyo.controller.Skill;
import us.takiyo.enemies.Dragon;
import us.takiyo.enemies.Minion;
import us.takiyo.enemies.Witch;
import us.takiyo.enums.SpecialAttributeTypes;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.extensions.TakiyoList;
import us.takiyo.extensions.TakiyoMap;
import us.takiyo.interfaces.SpecialAttribute;
import us.takiyo.managers.FileManager;
import us.takiyo.pages.Home;
import us.takiyo.pages.game.Adventure;
import us.takiyo.pages.game.Game;
import us.takiyo.pages.game.Train;
import us.takiyo.pages.play.Play;
import us.takiyo.pages.play.login.Login;
import us.takiyo.pages.play.register.Creation;
import us.takiyo.pages.play.register.Register;
import us.takiyo.pages.rule.Rule;
import us.takiyo.pages.scoreboard.Scoreboard;

import java.util.Arrays;

public class Main extends Master {
    TakiyoMap<String, Page> pages = new TakiyoMap<>();
    String currentPageId = "";

    public TakiyoList<Character> baseCharacters = new TakiyoList<>();
    public TakiyoList<Enemy> baseEnemies = new TakiyoList<>();
    public TakiyoList<Player> players = new TakiyoList<>();
    private String currentPlayer;
    FileManager fileManager = new FileManager("users.txt");

    public static void main(String[] args) {
        new Main();
    }

    public void save() {
        // i guess it'll work? not yet tried
        TakiyoList<String> list = new TakiyoList<>();

        for (Player player : players) {
            Character character = player.getCharacter();
            if (character == null) continue;

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s,%d,%d,%s#%s,%s,%.2f,%.2f,%.2f,%.2f,%s,%.2f,%d,%.2f",
                    player.getUsername(),
                    player.getScore(),
                    player.getFloor(),
                    player.getHashedPassword(),
                    character.getName(),
                    character.getElement(),
                    character.getBaseAtk(),
                    character.getBaseHealth(),
                    character.getSpeed(),
                    character.getMana(),
                    character.getSpecialAtr().getType(),
                    character.getSpecialAtr().getValue(),
                    character.getLevel(),
                    character.getExp()
            ));
            if (!player.getSkills().isEmpty()) {
                for (int i = 0; i < player.getSkills().size(); i++) {
                    Skill skill = player.getSkills().get(i);
                    sb.append(String.format("#%s,%d,%f",
                            skill.getName(),
                            skill.getManaCost(),
                            skill.getMultiplier()
                    ));
                    if (i == player.getSkills().size() - 1) {
                        sb.append("\n");
                    }
                }
            } else sb.append("\n");
            list.add(sb.toString());
        }

        String[] stringArray = new String[list.size()];
        list.toArray(stringArray);
        fileManager.write(stringArray);
    }


    public void load() {
        // not yet confirmed
        String[] data = fileManager.read();
        players.clear();

        for (String line : data) {
            String[] parts = line.split("#");

            Player player = getPlayer(parts);

            if (parts.length > 1) {
                String[] skillData = parts[1].split("#");
                for (String skillInfo : skillData) {
                    String[] skillParts = skillInfo.split(",");
                    String skillName = skillParts[0];

                    Skill skill = new Skill(skillName, player);
                    player.addSkill(skill);
                }
            }
            players.add(player);
        }
    }

    private static Player getPlayer(String[] parts) {
        String[] playerInfo = parts[0].split(",");
        String username = playerInfo[0];
        int score = Integer.parseInt(playerInfo[1]);
        int floor = Integer.parseInt(playerInfo[2]);
        String hashedPassword = playerInfo[3];
        String characterName = playerInfo[4];
        String characterElement = playerInfo[5];
        double baseAtk = Double.parseDouble(playerInfo[6]);
        double baseHealth = Double.parseDouble(playerInfo[7]);
        double speed = Double.parseDouble(playerInfo[8]);
        double mana = Double.parseDouble(playerInfo[9]);

        SpecialAttributeTypes.SpecialAttributeType specialAttrType = SpecialAttributeTypes.SpecialAttributeType.valueOf(playerInfo[10]);
        double specialAttrValue = Double.parseDouble(playerInfo[11]);
        int level = Integer.parseInt(playerInfo[12]);
        double exp = Double.parseDouble(playerInfo[13]);

        SpecialAttribute specialAttribute = new SpecialAttribute(specialAttrType, specialAttrValue);
        Character character = new Character(characterElement, baseAtk, baseHealth, speed, mana, new String[]{}, specialAttribute);
        character.setName(characterName);
        character.setLevel(level);
        character.setExp((int) exp);
        Player player = new Player(username, hashedPassword);
        player.setCharacter(character);
        player.setFloor(floor);
        player.setScore(score);
        return player;
    }

    public Main() {
        super();
        this.loadPages();
        this.loadCharacters();
        this.loadEnemies();

        while (true) {
            Master.clearTerminal();
            Page currentPage = pages.get(currentPageId);
            if (currentPage == null) {
                System.out.println("oajklsdkasdasd");
                return;
            }
            this.currentPageId = currentPage.execute(this);
            if (pages.get(this.currentPageId) == null) {
                System.out.println("Couldn't find page with ID " + this.currentPageId);
                System.exit(69);
            }
        }
    }

    void loadPages() {
        Page[] pages = {new Home(), new Play(), new Register(), new Creation(), new Login(), new Game(), new Train(), new Adventure(), new Rule(), new Scoreboard()};
        for (Page page : pages)
            this.pages.put(page.getId(), page);
        this.currentPageId = pages[0].getId();
    }

    void loadCharacters() {
        Character[] baseCharacters = {new WarriorBase(), new MageBase(), new RogueBase()};
        this.baseCharacters.addAll(Arrays.asList(baseCharacters));
    }

    void loadEnemies() {
        Enemy[] baseEnemies = {new Minion(), new Witch(), new Dragon()};
        this.baseEnemies.addAll(Arrays.asList(baseEnemies));
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}