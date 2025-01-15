package org.example.chess;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer {
    int T1_TimeRemaining,T2_TimeRemaining,timechange=25;
    Label T1_Label,T2_Label;
    Timeline T1_timeLine,T2_timeLine;

    Rectangle T1_Bkground,T2_Bkground;
    Rectangle InfoRect;

    String formatTimer(int milli)
    {
        return String.format("%02d", (milli/1000)/60)+":"+String.format("%02d", (milli/1000)%60);
    }

    public void addTimer(AnchorPane Pane) {
        T1_TimeRemaining = 10 * 60 * 1000;
        T2_TimeRemaining = 10 * 60 * 1000;
        T1_Label = new Label();
        T2_Label = new Label();

        T1_Label.setLayoutX(900);
        T2_Label.setLayoutX(900);
        T1_Label.setLayoutY(690);
        T2_Label.setLayoutY(20);

        T1_Label.setStyle("-fx-font: 80 sans-serif;");
        T2_Label.setStyle("-fx-font: 80 sans-serif;");

        T1_Bkground = new Rectangle();
        T2_Bkground = new Rectangle();

        T1_Bkground.setLayoutX(815);
        T2_Bkground.setLayoutX(815);
        T1_Bkground.setLayoutY(685);
        T2_Bkground.setLayoutY(15);

        T1_Bkground.setWidth(370);
        T1_Bkground.setHeight(100);
        T2_Bkground.setWidth(370);
        T2_Bkground.setHeight(100);

        T1_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20;");
        T2_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20; -fx-opacity: 0.25;");

        InfoRect = new Rectangle();
        InfoRect.setLayoutX(815);
        InfoRect.setLayoutY(130);
        InfoRect.setWidth(370);
        InfoRect.setHeight(540);
        InfoRect.setStyle("-fx-fill: #2e2e2e; -fx-arc-height: 20; -fx-arc-width: 20;");

        T1_Label.setText(formatTimer(T1_TimeRemaining));
        T2_Label.setText(formatTimer(T2_TimeRemaining));

        Pane.getChildren().add(T1_Bkground);
        Pane.getChildren().add(T2_Bkground);
        Pane.getChildren().add(T1_Label);
        Pane.getChildren().add(T2_Label);
        Pane.getChildren().add(InfoRect);
    }
    Main main = new Main();
    void StartTimer1() {
        if(T1_timeLine!=null) {
            T1_timeLine.play();
            T1_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20; -fx-opacity: 1.0;");
        }
        else {
            T1_timeLine = new Timeline(new KeyFrame(Duration.millis(timechange),e ->{
                T1_TimeRemaining-=timechange;
                T1_Label.setText(formatTimer(T1_TimeRemaining));
                if(T1_TimeRemaining<=0)
                {
                    T1_timeLine.stop();
                    main.GameOver();
                }
            }));
            T1_timeLine.setCycleCount(Animation.INDEFINITE);
            T1_timeLine.play();
            T1_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20; -fx-opacity: 1.0;");
        }
    }
    void StartTimer2() {
        if(T2_timeLine!=null) {
            T2_timeLine.play();
            T2_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20; -fx-opacity: 1.0;");
        }
        else {
            T2_timeLine = new Timeline(new KeyFrame(Duration.millis(timechange),e ->{
                T2_TimeRemaining-=timechange;
                T2_Label.setText(formatTimer(T2_TimeRemaining));
                if(T2_TimeRemaining<=0)
                {
                    T2_timeLine.stop();
                    main.GameOver();
                }
            }));
            T2_timeLine.setCycleCount(Animation.INDEFINITE);
            T2_timeLine.play();
            T2_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20; -fx-opacity: 1.0;");
        }
    }
    void pauseTimer1() {
        T1_timeLine.pause();
        T1_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20; -fx-opacity: 0.25;");
    }
    void pauseTimer2() {
        T2_timeLine.pause();
        T2_Bkground.setStyle("-fx-fill: #ededed; -fx-arc-height: 20; -fx-arc-width: 20; -fx-opacity: 0.25;");
    }
}
