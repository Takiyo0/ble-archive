package us.takiyo.pages.game;

import us.takiyo.Main;
import us.takiyo.controller.Character;
import us.takiyo.controller.Enemy;
import us.takiyo.controller.Player;
import us.takiyo.controller.Skill;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.extensions.TakiyoList;
import us.takiyo.managers.IOManager;

import java.util.concurrent.ThreadLocalRandom;

public class Adventure extends Page {
    public Adventure() {
        super("game.adventure");
    }

    @Override
    public String execute(Main main) {
        Player player = main.players.get(Player::getUsername, main.getCurrentPlayer());
        Character character = player == null ? null : player.getCharacter();
        if (character == null) {
            Master.sendError("You haven't selected your character yet. Redirecting to character creation");
            return "play.register.creation";
        }

        double random = Math.random();
        Enemy enemy = null;
        for (Enemy e : main.baseEnemies)
            if (random < ((double) e.getEncounterProbability() / 100)) enemy = e;
        if (enemy == null) enemy = main.baseEnemies.get(0);

        int enemiesHealth = enemy.getHealth(player.getFloor());

        while (true) {
            Master.clearTerminal();
            System.out.printf("%s health: %.1f%n", character.getType(), character.getHealth());
            System.out.print("[");
            for (int i = 0; i < 10; i++) {
                if (((character.getHealth() / character.getBaseHealth()) * 10) > i) System.out.print("#");
                else System.out.print(".");
            }
            System.out.print("]\n\n");

            System.out.printf("%s health: %d%n", enemy.getName(), enemiesHealth);
            System.out.print("[");
            for (int i = 0; i < 10; i++) {
                if (((enemiesHealth / enemy.getHealth(player.getFloor())) * 10) > i) System.out.print("#");
                else System.out.print(".");
            }
            System.out.print("]\n\n");
            System.out.print("Your turn! Choose your action\n1. Base Attack\n2. Use Skill\n3. RUN\n> ");
            int choice = IOManager.getInt();
            if (choice < 1 || choice > 3) {
                Master.sendError("Invalid choice");
                continue;
            }

            if (choice == 1) {
                System.out.printf("%s attacks %s with a sword!\n", character.getName(), enemy.getName());
                enemiesHealth -= (int) character.getBaseAtk();
                System.out.printf("   %s takes %.1f damage. Remaining health: %.1f\n", enemy.getName(), character.getBaseAtk(), (double) enemiesHealth);

                if (enemiesHealth <= 0) {
                    System.out.printf("You defeated the enemy on floor %d!\n", player.getFloor());
                    int exp = ThreadLocalRandom.current().nextInt(20, 30) * (player.getFloor() + 1);
                    int score = ThreadLocalRandom.current().nextInt(40, 60) * (player.getFloor() + 1);
                    System.out.println("Reward :");
                    System.out.printf(" EXP : +%d\n", exp);
                    System.out.printf(" Score : +%d\n", score);
                    System.out.printf("%s gained %d EXP.\n", character.getName(), exp);
                    player.setFloor(player.getFloor() + 1);
                    player.setScore(player.getScore() + score);
                    boolean levelUp = character.addExp(player, exp);
                    if (levelUp)
                        System.out.printf("%nHoorayy!! %s leveled up to %d!%n", character.getName(), character.getLevel());

                    Master.waitForEnter();
                    return "game.game";
                }

                System.out.printf("   %s attacks!\n", enemy.getName());
                double yes = Math.random();
                double damage = 0;
                double specialDamage = 0;
                damage = enemy.getBaseAtk(player.getFloor());
                character.setHealth((int) (character.getHealth() - damage));
                System.out.printf("%s takes %.1f damage. Remaining health: %.1f\n", character.getName(), damage, character.getHealth());
                if (enemy.getSpecialAttribute() != null) {
                    if (yes < ((double) enemy.getSpecialAttribute().chance / 100)) {
                        System.out.printf("   %s casts a %s\n", enemy.getName(), enemy.getSpecialAttribute().description);
                        specialDamage = enemy.getBaseAtk(player.getFloor()) * enemy.getSpecialAttribute().damageMultiplier;
                        character.setHealth((int) (character.getHealth() - specialDamage));
                        System.out.printf("%s takes %.1f damage by %s. Remaining health: %.1f\n", character.getName(), specialDamage, enemy.getSpecialAttribute().description, character.getHealth());
                    }
                }

                if (character.getHealth() <= 0) {
                    System.out.println("You died...");
                    return "game.game";
                }
            } else if (choice == 2) {
                TakiyoList<Skill> skills = player.getSkills();
                if (skills.isEmpty()) {
                    Master.sendWithEnter("You don't have any skills!");
                    continue;
                }

                System.out.println("Choose a skill:");
                for (int i = 0; i < skills.size(); i++)
                    System.out.printf("%d. %s\n", i + 1, skills.get(i).getName());

                int skillChoice = IOManager.getInt();
                if (skillChoice < 1 || skillChoice > skills.size()) {
                    Master.sendError("Invalid choice");
                    continue;
                }
                Skill selectedSkill = skills.get(skillChoice - 1);
                if (character.getMana() < selectedSkill.getManaCost()) {
                    Master.sendWithEnter("You don't have enough mana!");
                    continue;
                }
                System.out.printf("%s uses %s\n", skillChoice, selectedSkill.getName());
                double damageFromSkill = selectedSkill.getDamageMultiplier(enemy.getElement().toString().toLowerCase());
                enemiesHealth -= (int) damageFromSkill;
                System.out.printf("   %s takes %.1f damage. Remaining health: %.1f\n", enemy.getName(), damageFromSkill, (double) enemiesHealth);
                character.setMana((int) (character.getMana() - selectedSkill.getManaCost()));
                System.out.printf("%s lost %d mana. Remaining mana : %.2f", character.getName(), selectedSkill.getManaCost(), character.getMana());
                if (enemiesHealth <= 0) {
                    System.out.printf("You defeated the enemy on floor %d!\n", player.getFloor());
                    int exp = ThreadLocalRandom.current().nextInt(20, 30) * (player.getFloor() + 1);
                    int score = ThreadLocalRandom.current().nextInt(40, 60) * (player.getFloor() + 1);
                    System.out.println("Reward :");
                    System.out.printf(" EXP : +%d\n", exp);
                    System.out.printf(" Score : +%d\n", score);
                    System.out.printf("%s gained %d EXP.\n", character.getName(), exp);
                    player.setFloor(player.getFloor() + 1);
                    player.setScore(player.getScore() + score);
                    boolean levelUp = character.addExp(player, exp);
                    if (levelUp)
                        System.out.printf("%nHoorayy!! %s leveled up to %d!%n", character.getName(), character.getLevel());

                    Master.waitForEnter();
                    return "game.game";
                }
                System.out.printf("   %s attacks!\n", enemy.getName());
                double yes = Math.random();
                double damage = 0;
                double specialDamage = 0;
                damage = enemy.getBaseAtk(player.getFloor());

                character.setHealth((int) (character.getHealth() - damage));
                System.out.printf("%s takes %.1f damage. Remaining health: %.1f\n", character.getName(), damage, character.getHealth());
                if (enemy.getSpecialAttribute() != null) {
                    if (yes < ((double) enemy.getSpecialAttribute().chance / 100)) {
                        System.out.printf("   %s casts a %s", enemy.getName(), enemy.getSpecialAttribute().description);
                        specialDamage = enemy.getBaseAtk(player.getFloor()) * enemy.getSpecialAttribute().damageMultiplier;
                        character.setHealth((int) (character.getHealth() - specialDamage));
                        System.out.printf("%s takes %.1f damage by %s. Remaining health: %.1f\n", character.getName(), specialDamage, enemy.getSpecialAttribute().description, character.getHealth());
                    }
                }

                if (character.getHealth() <= 0) {
                    System.out.println("You died...");
                    return "game.game";
                }
            } else {
                boolean runSuccess = ThreadLocalRandom.current().nextBoolean();
                if (runSuccess) {
                    Master.sendWithEnter(String.format("%s ran successfully", character.getName()));
                    return "game.game";
                } else {
                    double damage = 0;
                    damage = enemy.getBaseAtk(player.getFloor());
                    character.setHealth((int) (character.getHealth() - damage));
                    Master.sendWithEnter(String.format("%s ran but got hit with %.1f damage. Remaining health: %.1f", character.getName(), damage, character.getHealth()));
                    return "game.game";
                }
            }
        }
    }
}
