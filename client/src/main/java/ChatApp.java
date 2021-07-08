import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println(getClass().getResource("" + "chat.fxml")); // почему null?

        Parent parent = FXMLLoader.load(getClass().getResource("chat.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
