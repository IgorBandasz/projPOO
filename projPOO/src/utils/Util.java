/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Graphics2D;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
  
  public static Date converterStringToDate(String texto) {
    //Construo o formato que quero transformar o texto
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    //crio minha variável data que será o retorno do método
    Date data = null;

    try {
      //tenta converter a String em Date baseado no formato 
      //contruido anteriomente
      data = formato.parse(texto);
    } catch (ParseException ex) {
      JOptionPane.showMessageDialog(null,
              "Erro ao converter a data");
    }
    //retorna a data convertida
    return data;
  }
  
  public static String calcularHash(String senha) {
    String hashSHA1 = "";
    try {
      // Crie uma instância do MessageDigest 
      //com o algoritmo SHA1
      MessageDigest sha1 = MessageDigest.getInstance("SHA1");

      // Atualize o digest com os bytes do texto
      sha1.update(senha.getBytes());

      // Calcule o hash SHA1
      byte[] digest = sha1.digest();

      // Converta o hash de bytes para 
      //uma representação hexadecimal
      for (byte b : digest) {
        hashSHA1 = hashSHA1 + String.format("%02x", b);
      }
      
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Algoritmo SHA1 não encontrado");
    }

    return hashSHA1;
  }
  
  public static String converterDateToString(Date data) {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String texto = "";

    try {
      //Irá formatar a data para o formato dd/MM/yyyy
      texto = formato.format(data);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null,
              "Erro ao formatar a data");
    }

    return texto;
  }
  
  public static File escolherImagem(){
    File arquivo = null;
    
    //Cria um escolhedor de arquivos
    JFileChooser exploradorArquivos = new JFileChooser();
    
    //Define o título que aparecerá na janela 
    //do explorador de arquivos
    exploradorArquivos.setDialogTitle("Escolha um arquivo");

    //Filtrar os tipos de arquivos que irá buscar
    FileNameExtensionFilter filtro = new FileNameExtensionFilter(
      "Imagens", "jpg", "jpeg", "png");
    exploradorArquivos.setFileFilter(filtro);

    // Configuração para permitir a seleção de apenas um arquivo
    exploradorArquivos.setMultiSelectionEnabled(false);
    
    //Chama o explorador de arquivos e guarda o resultado:
    // APPROVE_OPTION (selecionou)
    // CANCEL_OPTION (usuário cancelou)
    int resultado = exploradorArquivos.showOpenDialog(null);
    
    if (resultado == JFileChooser.APPROVE_OPTION) {
      //Pega o arquivo selecionado
      arquivo = exploradorArquivos.getSelectedFile();
    }  
    
    return arquivo;
  }
  
  public static Icon converterFileToIcon(File arquivo){
    //passo o caminho da imagem para o construtor de ImageIcon
    ImageIcon icon = new ImageIcon(arquivo.getAbsolutePath());
    return icon;
  }
  
  public static ImageIcon redimensionarImagem(Icon icone,
          int largura, int altura){
    
    //converto o icone em imagem
    Image imagemOriginal = ((ImageIcon) icone).getImage();
    
    //redimensiono a imagem
    Image novaImagem = imagemOriginal.getScaledInstance(
            largura, altura, Image.SCALE_SMOOTH);
    
    //converto a imagem redimensionada em icone de novo
    return new ImageIcon(novaImagem);
    
  }
  
  public static byte[] converterIconToBytes(Icon icon){
    BufferedImage image = new BufferedImage(
            icon.getIconWidth(),
            icon.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();
    icon.paintIcon(null, g2d, 0, 0);
    g2d.dispose();

    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    try{
      ImageIO.write(image, "png", byteArray);
    } catch(IOException erro){
      Logger.getLogger(Util.class.getName()).log(
              Level.SEVERE, null, erro);
    }
    return byteArray.toByteArray();
    
  }
  
}
