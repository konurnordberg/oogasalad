package oogasalad.view.AuthoringScreens.AuthoringHandlers;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

public class ShapeOptionBox {
  private VBox container;
  private Shape selectedShape;
  private ColorPicker colorPicker;
  private Button imageButton;
  private Slider opacitySlider;

  public ShapeOptionBox() {
    createComponents();
  }

  public VBox getContainer() {
    return this.container;
  }

  private void createComponents() {
    container = new VBox(10);
    container.setPadding(new Insets(5));

    colorPicker = new ColorPicker();
    colorPicker.setOnAction(event -> {
      if (selectedShape != null) {
        selectedShape.setFill(colorPicker.getValue());
      }
    });

    imageButton = new Button("Select Image");
    imageButton.setOnAction(event -> {
      // Assume ImageHandler exists and is initialized somewhere
      // ImageHandler.applyImageToShape(selectedShape);
    });

    opacitySlider = new Slider(0, 1, 1);
    opacitySlider.setShowTickLabels(true);
    opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (selectedShape != null) {
        selectedShape.setOpacity(newValue.doubleValue());
      }
    });

    container.getChildren().addAll(colorPicker, imageButton, opacitySlider);
  }

  public void setSelectedShape(Shape shape) {
    this.selectedShape = shape;
    // Update UI components based on the selected shape's properties if necessary
  }
}
