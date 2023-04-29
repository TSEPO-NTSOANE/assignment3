package noone.mymediaplayer;

import java.io.File;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


public class PrimaryController {
    
    private String path;
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider timeForPlay;
    
    @FXML
    private Slider volumeDecreaseAndIncrease;
     
    public void ChooseFile(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        
        if(path != null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            DoubleProperty widthProper = mediaView.fitWidthProperty();
            DoubleProperty heightProper = mediaView.fitHeightProperty();
            
            widthProper.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            heightProper.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
            
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                 timeForPlay.setValue(newValue.toSeconds());
                }
                
            }
            );
            timeForPlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                 mediaPlayer.seek(Duration.seconds(timeForPlay.getValue()));
                }
            });
             timeForPlay.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                 mediaPlayer.seek(Duration.seconds(timeForPlay.getValue()));
                }
            });
           mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                Duration total = media.getDuration();
                timeForPlay.setMax(total.toSeconds());
                }
           });
           volumeDecreaseAndIncrease.setValue(mediaPlayer.getVolume()* 100);
           volumeDecreaseAndIncrease.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                   mediaPlayer.setVolume(volumeDecreaseAndIncrease.getValue()/100);
                }
           });
        }
    }
    public void play(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    public void pause(ActionEvent event){
        mediaPlayer.pause();
    }
    public void stop(ActionEvent event){
        mediaPlayer.stop();
    }
    public void slowRate(ActionEvent event){
        mediaPlayer.setRate(0.5);
    }
    public void increaseRate(ActionEvent event){
        mediaPlayer.setRate(2);
    }
    public void Forward10(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
    }
    public void Backward10(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-10)));
    }
}
