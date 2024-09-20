package org.example.chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Images {
    String  ImgFolder = "C:\\Users\\ss\\IdeaProjects\\Chess\\src\\main\\resources\\Images\\";
    Image bR = new Image(new File(ImgFolder+"bR.png").toURI().toString());
    Image wR = new Image(new File(ImgFolder+"wR.png").toURI().toString());
    Image bQ = new Image(new File(ImgFolder+"bQ.png").toURI().toString());
    Image wQ = new Image(new File(ImgFolder+"wQ.png").toURI().toString());
    Image bP = new Image(new File(ImgFolder+"bP.png").toURI().toString());
    Image wP = new Image(new File(ImgFolder+"wP.png").toURI().toString());
    Image bK = new Image(new File(ImgFolder+"bK.png").toURI().toString());
    Image wK = new Image(new File(ImgFolder+"wK.png").toURI().toString());
    Image bKn = new Image(new File(ImgFolder+"bKn.png").toURI().toString());
    Image wKn = new Image(new File(ImgFolder+"wKn.png").toURI().toString());
    Image bB = new Image(new File(ImgFolder+"bB.png").toURI().toString());
    Image wB = new Image(new File(ImgFolder+"wB.png").toURI().toString());
    Image Board = new Image(new File(ImgFolder+"maple.jpg").toURI().toString());
}
