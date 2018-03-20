/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.viewer;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import plugins.MakeNegative;
import plugins.MirrorReflection;
import plugins.RotateImage;

/**
 *
 * @author buczak
 */
public class NewJFrame extends javax.swing.JFrame {
    public File folderPath = new File(".");
    /**
     * Creates new form NewJFrame
     */
    
    public String[] SUPPORTED_EXTENSIONS = new String[]{
        "gif", "png", "bmp", "jpg"
    };

volatile private boolean updateThumbnails = false;

volatile private boolean needRepainting = false;
    
public Map<String, WeakReference> thumbnails = new TreeMap<String, WeakReference>();

public Image createThumbnail(Image i)
{
    return i.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
}
    
public FilenameFilter IMAGE_FILTER = new FilenameFilter() {
    public boolean accept(final File dir, final String name) {
        for (final String ext : SUPPORTED_EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return (true);
            }
        }
        return (false);
    }
    };
    
    public NewJFrame() {
        initComponents();
        getImages.start();
    }
    
    Thread getImages = new Thread(new Runnable() { 
        public void run()
        {
            while(true) {
                if(updateThumbnails) {
                    System.out.println("nic");
                    thumbnailsCanvas.removeAll();
                    thumbnailsCanvas.revalidate();
                    thumbnailsCanvas.repaint();
                    for (final File f : folderPath.listFiles(IMAGE_FILTER)) {
                            Image img = null;
                            System.out.println("dibag");
                            try {
                                JLabel picLabel = null;
                                boolean alreadyExists = false;
                                for(String k : thumbnails.keySet()) {
                                    if(k.equals(f.getPath())) {
                                        picLabel = (JLabel) thumbnails.get(f.getPath()).get();
                                        alreadyExists = true;
                                        break;
                                    }
                                }
                                if(!alreadyExists || needRepainting) {
                                    img = ImageIO.read(f);
                                    Image thumbnail = createThumbnail(img);
                                    thumbnails.put(f.getPath(), new WeakReference(new JLabel(new ImageIcon(thumbnail))));
                                    //JLabel picLabel = new JLabel(new ImageIcon(thumbnail));
                                    picLabel = (JLabel) thumbnails.get(f.getPath()).get();
                                }
                        if (picLabel != null) {
                            javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();
                            
                            JMenuItem mi1 = new JMenuItem("rotate");
                            mi1.addActionListener(new java.awt.event.ActionListener()
                                {
                                    public void actionPerformed(java.awt.event.ActionEvent evt)
                                    {
                                        System.out.println("klikd");
                                        //RotateImage r = new RotateImage();
                                        try {
                                            ImageViewerClassLoader cl = new ImageViewerClassLoader();
                                            cl.invokeClassMethod("RotateImage", "rotate", ImageIO.read(f), f);
                                            updateThumbnails = true;
                                            needRepainting = true;
                                        } catch (IOException ex) {
                                            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                            menu.add(mi1);
                            
                            JMenuItem mi2 = new JMenuItem("negative");
                            mi2.addActionListener(new java.awt.event.ActionListener()
                                {
                                    public void actionPerformed(java.awt.event.ActionEvent evt)
                                    {
                                        System.out.println("klikd");
                                        //MakeNegative r = new MakeNegative();
                                        try {
                                            ImageViewerClassLoader cl = new ImageViewerClassLoader();
                                            cl.invokeClassMethod("MakeNegative", "negative", ImageIO.read(f), f);
                                            updateThumbnails = true;
                                            needRepainting = true;
                                        } catch (IOException ex) {
                                            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                            menu.add(mi2);
                            
                            JMenuItem mi3 = new JMenuItem("reflection horizontal");
                            mi3.addActionListener(new java.awt.event.ActionListener()
                                {
                                    public void actionPerformed(java.awt.event.ActionEvent evt)
                                    {
                                        System.out.println("klikd");
                                        //MirrorReflection r = new MirrorReflection();
                                        try {
                                            ImageViewerClassLoader cl = new ImageViewerClassLoader();
                                            cl.invokeClassMethod("ReflectHorizontal", "reflect", ImageIO.read(f), f);
                                            updateThumbnails = true;
                                            needRepainting = true;
                                        } catch (IOException ex) {
                                            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                            menu.add(mi3);
                            
                            JMenuItem mi4 = new JMenuItem("reflection vertical");
                            mi4.addActionListener(new java.awt.event.ActionListener()
                                {
                                    public void actionPerformed(java.awt.event.ActionEvent evt)
                                    {
                                        System.out.println("klikd");
                                        //MirrorReflection r = new MirrorReflection();
                                        try {
                                            ImageViewerClassLoader cl = new ImageViewerClassLoader();
                                            cl.invokeClassMethod("ReflectVertical", "reflect", ImageIO.read(f), f);
                                            updateThumbnails = true;
                                            needRepainting = true;
                                        } catch (IOException ex) {
                                            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                            menu.add(mi4);
                            
                            picLabel.addMouseListener(new MouseAdapter()  
                                {
                                    public void mouseClicked(MouseEvent e)  
                                    {  
                                        System.out.println("kliknieto miniaturke ");
                                        menu.show(e.getComponent(), e.getX(), e.getY());
                                    }
                                });
                                thumbnailsCanvas.add(picLabel);
                        }

                                //System.out.println("image: " + f.getName());
                                //System.out.println(" width : " + img.getWidth());
                                //System.out.println(" height: " + img.getHeight());
                                //System.out.println(" size  : " + f.length());
                            } catch (final IOException e) {
                            }
                        }
                    thumbnailsCanvas.revalidate();
                    thumbnailsCanvas.repaint();
                    int lycznyk = 0;
                    for(String path : thumbnails.keySet()) {
                    if(thumbnails.get(path).get() != null) {
                        System.out.println(lycznyk + " kanapka");
                        lycznyk++;
                    }
                }
                System.out.println("---");
                updateThumbnails = false;
                needRepainting = false;
                }
            }
    }});

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        thumbnailsCanvas = new javax.swing.JPanel();
        jFileChooser1 = new javax.swing.JFileChooser();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("image-viewer");
        setBackground(new java.awt.Color(102, 102, 102));
        setMinimumSize(new java.awt.Dimension(400, 700));

        thumbnailsCanvas.setBackground(new java.awt.Color(204, 204, 204));
        thumbnailsCanvas.setLayout(new java.awt.GridLayout(3, 4));

        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        jFileChooser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFileChooser1MouseClicked(evt);
            }
        });
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(thumbnailsCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(thumbnailsCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFileChooser1MouseClicked
        
    }//GEN-LAST:event_jFileChooser1MouseClicked

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        folderPath = jFileChooser1.getSelectedFile();
        if (folderPath != null) updateThumbnails = true;
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel thumbnailsCanvas;
    // End of variables declaration//GEN-END:variables
}
