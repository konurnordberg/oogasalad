package oogasalad.view.AuthoringScreens;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

/**
 * Class to represent the screen in which user places and customizes obstacle objects in their
 * unique game
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class ObstacleSelectionScreen extends AuthoringScreen{
  private ComboBox<ObstacleType> obstacleTypeComboBox;

  private Map<Shape, ObstacleType> obstacleTypeMap;


  public ObstacleSelectionScreen(AuthoringController controller, StackPane authoringBox) {
    super(controller, authoringBox);
    obstacleTypeMap = new HashMap<>();
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new StackPane();
    root.getChildren().add(authoringBox);
    createSizeAndAngleSliders();
    createShapeDisplayOptionBox();
    createDraggableShapeTemplates();
    createNextButton();
    createObstacleTypeDropdown();
    scene = new Scene(root, screenWidth, screenHeight);
  }

  void createObstacleTypeDropdown() {
    obstacleTypeComboBox = new ComboBox<>();
    obstacleTypeComboBox.getItems().addAll(ObstacleType.BOUNCE, ObstacleType.RESET, ObstacleType.SLOW);
    obstacleTypeComboBox.setPromptText("Select Obstacle Type");
    StackPane.setAlignment(obstacleTypeComboBox, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(obstacleTypeComboBox, new Insets(0, 50, 350, 0));
    obstacleTypeComboBox.setPrefSize(200, 100);
    root.getChildren().add(obstacleTypeComboBox);

    // Listen for changes and update the map
    obstacleTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
      if (selectedShape != null && newVal != null) {
        obstacleTypeMap.put(selectedShape, newVal);
      }
    });
  }


  private void makeSelectable(Shape shape) {
    shape.setOnMouseClicked(event -> {
      selectedShape = shape;
      ObstacleType type = obstacleTypeMap.getOrDefault(shape, null);
      obstacleTypeComboBox.setValue(type);
    });
  }


  private boolean allSelectionsMade() {
    for (Shape shape : selectableShapes) {
      Bounds shapeBounds = shape.getBoundsInParent();
      Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

      if (authoringBoxBounds.contains(shapeBounds)) {
        if (!obstacleTypeMap.containsKey(shape)) {
          return false; // Found an obstacle without an assigned type
        }
      }
    }
    return true; // All obstacles have an assigned type
  }
  /**
   * When the next button is clicked, controller is prompted to start the next selection process
   */
  @Override
  void endSelection() {
    printShapesAndTheirTypes();
    if (allSelectionsMade()) {
      addNewSelectionsToAuthoringBox();
      controller.startNextSelection(ImageType.OBSTACLE, authoringBox); // Adjust NEXT_TYPE to whatever comes next
    } else {
      // TODO: Show a message to the user explaining that not all obstacles have types assigned
      System.out.println("Please assign types to all obstacles.");
    }
  }

  private void printShapesAndTheirTypes() {
    obstacleTypeMap.forEach((shape, type) -> {
      //implement custom ID, currently using class name and hash code for id
      String identifier = shape.getClass().getSimpleName() + "@" + Integer.toHexString(shape.hashCode());
      System.out.println("Shape: " + identifier + ", Type: " + type);
    });
  }

  @Override
  void createDraggableShapeTemplates() {
    super.createDraggableShapeTemplates();
    selectableShapes.forEach(this::makeSelectable);
  }


  /**
   * Returns obstacle image type indicating that user is placing obstacle objects
   *
   * @return enum to represent goal image type
   */
  ImageType getImageType() {
    return ImageType.OBSTACLE;
  }

  /**
   * Updates object type to reflect type of currently selected object
   */
  void updateOptionSelections() {

  }
}
