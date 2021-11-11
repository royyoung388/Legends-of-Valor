package controller;

import model.monster.Monster;
import view.MonsterView;

/**
 * MVC: monster controller
 */
public class MonsterControllerImpl extends CharacterControllerImpl implements MonsterController {

    private Monster monster;
    private MonsterView monsterView;

    public MonsterControllerImpl(Monster monster, MonsterView monsterView) {
        this.monster = monster;
        this.monsterView = monsterView;
    }


    @Override
    public Monster getCharacter() {
        return monster;
    }

    @Override
    public void show() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return monsterView.show(monster);
    }
}
