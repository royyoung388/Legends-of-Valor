package controller;

import model.monster.Monster;

/**
 * MVC: monster controller
 */
public interface MonsterController extends CharacterController {
    @Override
    Monster getCharacter();
}
