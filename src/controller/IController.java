package controller;

import events.IViewEvent;

/**
 * How the model communicates with the player.
 * Allows the player to actually play the game.
 */
public interface IController extends IViewEvent {
  /**
   * Activates the controller.
   */
  void run();
}
