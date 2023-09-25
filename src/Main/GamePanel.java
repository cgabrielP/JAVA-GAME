package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //ajustes pantalla
    final int  originalSileSize=16;//16x16 pixels
    final int scale=3;
    final int tileSize=originalSileSize*scale;
    final int maxScreenCol=16;
    final int maxScreenRow=12;
    final int screenWidth=tileSize*maxScreenCol;//768pixels
    final int screenHeight=tileSize*maxScreenRow;//576pixels
    double FPS=60;
    Thread gameThread;//hilo del juego
    KeyHandler keyHandler=new KeyHandler();
    //Posicion predeterminada jugador
    int playerX=100;
    int playerY=100;
    int playerSpeed=4;
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.CYAN);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

 /*   @Override
    public void run() {
        double drawInterval=1000000000/FPS;
        double nextDrawTime=System.nanoTime()+drawInterval;
        while(gameThread!=null){

            update();

            repaint();
            try {
                double remainingTime=nextDrawTime-System.nanoTime();
                remainingTime /= 1_000_000;

                if (remainingTime<0){
                    remainingTime=0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime+=drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }*/
    public void  run(){
        double drawInterval=1000000000/FPS;
        double delta=0;
        long lastTime=System.nanoTime();
        long currentTime;

        while(gameThread!=null){
            currentTime=System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            System.out.println("delta: "+delta);
            System.out.println(currentTime + " + "+lastTime);
            lastTime=currentTime;
            if (delta>=1){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update(){
        if(keyHandler.upPressed){
            playerY-=playerSpeed;
        }
        if(keyHandler.downPressed){
            playerY+=playerSpeed;
        }
        if(keyHandler.leftPressed){
            playerX-=playerSpeed;
        }
        if(keyHandler.rightPressed){
            playerX+=playerSpeed;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose();

    }
}
