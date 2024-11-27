import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

class AppPanel extends JPanel {
    int birdX = 100;
    int birdY = 200;
    int birdVelocity = 0;
    int pipeX = 500;
    int pipeGap = 150; 
    int pipeWidth = 60; 
    int pipeSpeed = 3; 
    int pipeHeightTop = 200;
    static BufferedImage BackImage;
    static BufferedImage BirdImage;
    static BufferedImage PipeUp;
    static BufferedImage PipeDown;

    Timer timer;

    AppPanel() {
        setSize(500, 500);
        loadImages();
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLoop();
                repaint();
            }
        });
        timer.start();



        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    birdVelocity = -10; 
                }
            }
        });
        setFocusable(true);
    }



    private void loadImages() {
        try {
            BackImage = ImageIO.read(AppPanel.class.getResource("Fbbackground.jpg"));
            BirdImage = ImageIO.read(AppPanel.class.getResource("Birdimage.png")).getSubimage(0, 0, 320, 240);
            PipeUp = ImageIO.read(AppPanel.class.getResource("pipeup.png"));
            PipeDown = ImageIO.read(AppPanel.class.getResource("pipedw.png"));
        } catch (IOException e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }
    // private void pipeimage(){
    //     try {
    //         PipeUp = ImageIO.read(AppPanel.class.getResource("pipeup.png"));
    //         PipeDown = ImageIO.read(AppPanel.class.getResource("pipedw.png"));
    //     } catch (Exception e) {
    //         System.out.println(e);
    //         // TODO: handle exception
    //     }
    // }


    private void gameLoop() {

        birdY += birdVelocity;
        birdVelocity += 1;

        pipeX -= pipeSpeed;
        if (pipeX + pipeWidth <= 0) { 
            pipeX = 500;
            pipeHeightTop = (int) (Math.random() * 200) + 50; 
        }

        if (birdY < 0 || birdY > 500 || checkPipeCollision()) {
            resetGame();
        }
    }

    private boolean checkPipeCollision() {

        if (birdX + 40 > pipeX && birdX < pipeX + pipeWidth && birdY < pipeHeightTop) {
            return true;
        }

        if (birdX + 40 > pipeX && birdX < pipeX + pipeWidth && birdY + 40 > pipeHeightTop + pipeGap) {
            return true;
        }

        return false;
    }


    private void resetGame() {
        birdX = 100;
        birdY = 200;
        birdVelocity = 0;
        pipeX = 500;
        pipeHeightTop = 200;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BackImage, 0, 0, 500, 500, null);
        g.drawImage(BirdImage, birdX, birdY, 40, 40, null);
        g.drawImage(PipeUp, pipeX, 0, pipeWidth, pipeHeightTop, null);
        g.drawImage(PipeDown, pipeX, pipeHeightTop + pipeGap, pipeWidth, 500 - (pipeHeightTop + pipeGap), null);
    }
}
