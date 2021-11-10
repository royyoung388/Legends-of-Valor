package controller;

import bean.monster.Monster;

/**
 * MVC: monster controller
 */
public interface MonsterController extends CharacterController {
    @Override
    Monster getCharacter();
}
