package com.client.components;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaPlayerComponent extends AnchorPane {
    public MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private final Slider timeSlider = new Slider();
    private final Button playPauseButton = new Button("Play");
    private final Label timeLabel = new Label("00:00");

    public MediaPlayerComponent(MediaPlayer mediaPlayer) {
        this.setPrefSize(480, 400);
        this.mediaPlayer = mediaPlayer;
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(480);
        HBox controlBar = new HBox(10);
        controlBar.setAlignment(Pos.CENTER);
        controlBar.setStyle("-fx-background-color: #333;");
        playPauseButton.setOnAction(e -> togglePlayPause());
        timeSlider.setMin(0);
        timeSlider.setMax(100);
        timeSlider.setValue(0);
        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue(newValue.toSeconds() / mediaPlayer.getTotalDuration().toSeconds() * 100);
            }
        });

        timeSlider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            if (!isChanging) {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue() / 100 * mediaPlayer.getTotalDuration().toSeconds()));
            }
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> updateTimeLabel(newTime));

        controlBar.getChildren().addAll(playPauseButton, timeSlider, timeLabel);
        controlBar.setLayoutX(140);
        controlBar.setLayoutY(280);
        this.getChildren().addAll(controlBar, mediaView);
    }

    private void togglePlayPause() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playPauseButton.setText("Play");
        } else {
            mediaPlayer.play();
            playPauseButton.setText("Pause");
        }
    }

    private void updateTimeLabel(Duration time) {
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes() % 60;
        int seconds = (int) time.toSeconds() % 60;
        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}