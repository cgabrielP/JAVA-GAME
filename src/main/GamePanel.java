package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //ajustes pantalla
    final int  originalTileSize=16;//16x16 pixels
    final int scale=3;
    public final int tileSize=originalTileSize*scale;
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    public final int screenWidth=tileSize*maxScreenCol;//768pixels
    public final int screenHeight=tileSize*maxScreenRow;//576pixels
    double FPS=60;
    Thread gameThread;//hilo del juego
    KeyHandler keyHandler=new KeyHandler();
    Player player=new Player(this,keyHandler);
    TileManager tileM=new TileManager(this);
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

    public void  run(){
        double drawInterval=1000000000/FPS;
        double delta=0;
        long lastTime=System.nanoTime();
        long currentTime;

        while(gameThread!=null){
            currentTime=System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;

            lastTime=currentTime;
            if (delta>=1){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();

    }
}
