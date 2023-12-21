package events;

/**
 * Represents a model event that allows the active controller to communicate with the model,
 * and then the model to communicate with all its subscriber controllers.
 */
public interface IModelEvent {

  /**
   * Notifies all listeners that the model's state has changed.
   */
  void notifyModelHasChanged();
}
