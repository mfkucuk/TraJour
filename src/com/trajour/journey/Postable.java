package com.trajour.journey;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Interface post
 *
 * @author Ahmet Alperen Yılmazyıldız
 * @version 1.05.2021
 */
public interface Postable {
   Post post(String text, Image image);
}
