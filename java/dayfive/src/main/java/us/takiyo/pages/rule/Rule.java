package us.takiyo.pages.rule;

import us.takiyo.Main;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;

public class Rule extends Page {
    public Rule() {
        super("rule.rule");
    }

    @Override
    public String execute(Main main) {
        System.out.println("*********************************************************************************\r\n*" +
                "                                                                               *\r\n*" +
                "                              GAME RULES                                       *\r\n*" +
                "                                                                               *\r\n*" +
                "  1. Objective:                                                                *\r\n*" +
                "     - The main goal of this game is to level up your character by defeating    *\r\n*" +
                "       enemies on different floors until the game is completed.                *\r\n*" +
                "     - Each enemy you defeat will grant EXP and score, which help in           *\r\n*" +
                "       strengthening your character.                                           *\r\n*" +
                "                                                                               *\r\n*" +
                "  2. Character:                                                                *\r\n*" +
                "     - At the start of the game, you can choose one of three character         *\r\n*" +
                "       classes: Warrior, Mage, or Rogue.                                       *\r\n*" +
                "     - Each class has special attributes:                                      *\r\n*" +
                "       - Warrior: High armor, suited for close-range combat.                   *\r\n*" +
                "       - Mage: Uses powerful magic, but has low armor.                         *\r\n*" +
                "       - Rogue: Has higher critical hit chance and is faster than the          *\r\n*" +
                "         other classes.                                                        *\r\n*" +
                "                                                                               *\r\n*" +
                "     - Your character will have the following basic attributes:                *\r\n*" +
                "       - Base Attack: The strength of your basic attack.                       *\r\n*" +
                "       - Health: The amount of health you have.                                *\r\n*" +
                "       - Speed: Determines the turn order during combat.                       *\r\n*" +
                "       - Mana: The magic energy needed to use skills.                          *\r\n*" +
                "       - Level & EXP: Every time your character gains EXP from battle,         *\r\n*" +
                "         their level will increase once the required EXP is reached.           *\r\n*" +
                "                                                                               *\r\n*" +
                "" +
                "  3. Combat System:                                                            *\r\n*" +
                "     - Combat uses a turn-based system, where you and the enemy take turns     *\r\n*" +
                "       to attack.                                                              *\r\n*" +
                "     - During your turn, you can choose one of two actions:                    *\r\n*" +
                "       - Base Attack: A basic attack that does not consume mana.               *\r\n*" +
                "       - Skill: Use your character's special skill (requires mana).            *\r\n*" +
                "                                                                               *\r\n*" +
                "     - Each enemy has attributes such as:                                      *\r\n*" +
                "       - Health: The enemy's health points.                                    *\r\n*" +
                "       - Attack: The strength of the enemy's attack.                           *\r\n*" +
                "       - EXP and Score: Every defeated enemy grants EXP for leveling up and    *\r\n*" +
                "         score for ranking purposes.                                           *\r\n*" +
                "                                                                               *\r\n*" +
                "  4. Training:                                                                 *\r\n*" +
                "     - Choose the \"Train\" option to increase your character's EXP without      *\r\n*" +
                "       risking combat.                                                         *\r\n*" +
                "     - Every time you train, your character gains EXP, helping to strengthen   *\r\n*" +
                "       them.                                                                   *\r\n*" +
                "                                                                               *\r\n*" +
                "  5. Adventure (Floors):                                                       *\r\n*" +
                "     - In Adventure mode, you will enter different floors and fight            *\r\n*" +
                "       increasingly stronger enemies.                                          *\r\n*" +
                "     - Every floor you clear will reward you with EXP and score.               *\r\n*" +
                "     - Health and mana will decrease during battle, so be cautious before      *\r\n*" +
                "       proceeding to the next floor.                                           *\r\n*" +
                "                                                                               *\r\n*" +
                "  6. Leveling Up:                                                              *\r\n*" +
                "     - Every time your character gains enough EXP, they will level up.         *\r\n*" +
                "     - When leveling up, basic attributes such as Base Attack, Health, Speed,  *\r\n*" +
                "       and Mana will increase.                                                 *\r\n*" +
                "     - Special attributes such as armor, magical power, or critical chance     *\r\n*" +
                "       will remain the same but become more effective with higher levels.      *\r\n*" +
                "                                                                               *\r\n*" +
                "  7. Defeat:                                                                   *\r\n*" +
                "     - If your character is defeated in battle (health drops to 0), the game   *\r\n*" +
                "       is over.                                                                *\r\n*" +
                "     - Your score will be saved, but you cannot continue with the same         *\r\n*" +
                "       character.                                                              *\r\n*" +
                "                                                                               *\r\n*" +
                "  8. Score System:                                                             *\r\n*" +
                "     - Your score is determined by how far you progress and how many enemies   *\r\n*" +
                "       you defeat.                                                             *\r\n*" +
                "     - Scores are saved to the leaderboard after your character is defeated    *\r\n*" +
                "       or the game ends.                                                       *\r\n*" +
                "                                                                               *\r\n*" +
                "********************************************************************************\r");
        Master.waitForEnter();
        return "game.game";
    }
}
