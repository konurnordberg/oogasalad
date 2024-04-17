package oogasalad.view.playing_scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.view.enums.SceneElementType;

public class SceneElementFactory {

  private final SceneElementParser sceneElementParser;
  private final ElementStyler elementStyler;
  private final double screenWidth;
  private final double screenHeight;
  private final String typeTag = "type";
  private final String textTag = "text";
  private final String widthFactorTag = "width_factor";
  private final String heightFactorTag = "height_factor";
  private final String xLayoutFactorTag = "x_layout_factor";
  private final String yLayoutFactorTag = "y_layout_factor";
  private final String styleTag = "styling";

  public SceneElementFactory(Pane root, double screenWidth, double screenHeight) {
    sceneElementParser = new SceneElementParser();
    elementStyler = new ElementStyler(root);
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }

  public List<Node> createSceneElements(List<Map<String, String>> parameterList) {
    List<Node> sceneElements = new ArrayList<>();

    for (Map<String, String> parameterMap : parameterList) {
      String type = parameterMap.get(typeTag);

      switch (SceneElementType.valueOf(type)) {
        case BUTTON -> {
          sceneElements.add(createButton(parameterMap));
        }
        case TEXT -> {
          sceneElements.add(createText(parameterMap));
        }
        case LISTVIEW -> {
          sceneElements.add(createListView(parameterMap));
        }
      }
    }

    return sceneElements;
  }

  private Button createButton(Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    double widthFactor = Double.parseDouble(parameters.get(widthFactorTag));
    double heightFactor = Double.parseDouble(parameters.get(heightFactorTag));
    double xLayoutFactor = Double.parseDouble(parameters.get(xLayoutFactorTag));
    double yLayoutFactor = Double.parseDouble(parameters.get(yLayoutFactorTag));
    String style = parameters.get(styleTag);

    Button button = new Button(displayText);
    elementStyler.style(button, style);

    button.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    button.setLayoutX(screenWidth * xLayoutFactor - button.getWidth() / 2);
    button.setLayoutY(screenHeight * yLayoutFactor - button.getHeight() / 2);

    return button;
  }

  private Text createText(Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    double xLayoutFactor = Double.parseDouble(parameters.get(xLayoutFactorTag));
    double yLayoutFactor = Double.parseDouble(parameters.get(yLayoutFactorTag));
    String style = parameters.get(styleTag);

    Text text = new Text(displayText);
    elementStyler.style(text, style);

    text.setLayoutX(screenWidth * xLayoutFactor - text.getLayoutBounds().getWidth() / 2);
    text.setLayoutY(screenHeight * yLayoutFactor - text.getLayoutBounds().getHeight() / 2);

    return text;
  }

  private ListView<String> createListView(Map<String, String> parameters) {
    double widthFactor = Double.parseDouble(parameters.get(widthFactorTag));
    double heightFactor = Double.parseDouble(parameters.get(heightFactorTag));
    double xLayoutFactor = Double.parseDouble(parameters.get(xLayoutFactorTag));
    double yLayoutFactor = Double.parseDouble(parameters.get(yLayoutFactorTag));
    String style = parameters.get(styleTag);

    ListView<String> listView = new ListView<>(sceneElementParser.getGameTitles());
    elementStyler.style(listView, style);

    listView.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    listView.setLayoutX(screenWidth * xLayoutFactor - listView.getPrefWidth() / 2);
    listView.setLayoutY(screenHeight * yLayoutFactor);

    return listView;
  }

}
