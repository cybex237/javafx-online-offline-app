package io.cybex.javafx.online.offline;

//<editor-fold defaultstate="collapsed" desc="imports">
import io.cybex.javafx.online.offline.util.ConnectivityUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
//</editor-fold>

public class FXMLController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="private methods">
    @FXML
    private Pane offlinePane;

    @FXML
    private Label label;

    @FXML
    private Pane onlinePane;

    @FXML
    private Label label1;

    private ConnectivityUtil connectivityUtil = new ConnectivityUtil();

    private Timeline timeline;
//</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                internetCheck.setPeriod(Duration.seconds(5));
                internetCheck.restart();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //<editor-fold defaultstate="collapsed" desc="internetCheck">
    ScheduledService<Boolean> internetCheck = new ScheduledService<Boolean>() {
        @Override
        protected Task<Boolean> createTask() {
            Task<Boolean> aliveTask = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return connectivityUtil.netIsAvailable();
                }

                @Override
                protected void succeeded() {
                    if (getValue()) { 

                        onlinePane.setVisible(true);
                        offlinePane.setVisible(false);
                    } else {
                        
                        offlinePane.setVisible(true);
                        onlinePane.setVisible(false);
                    }

                }
            };
            return aliveTask;

        }
    ;
};
//</editor-fold>
}
