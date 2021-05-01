package com.trajour.journey;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Postable {
   Post post(String text, Image image);
}
