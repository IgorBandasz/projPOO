/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 *
 * @author iband
 */
public class Util {
  
  public static Image getIcone(){
    URL caminhoImagem = Util.class.getResource("/images/logo_mini.png");

    ImageIcon icon = new ImageIcon(caminhoImagem);
    
    return icon.getImage();
  }
}
