import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.min;

class Render {

    public static void renderLine(BufferedImage img, float x1, float y1, float x2, float y2, int color) {
        if (x1 == x2) {
            for (int j = 0; j < img.getHeight(); j++) {
                img.setRGB((int)x1, j, color);
            }
        } else {
            float k = (y1 - y2) / (x1 - x2);
            float b = (x1 * y2 - x2 * y1) / (x1 - x2);
            for (int i = 1; i < img.getWidth(); i++) {
                if (k * i + b < img.getHeight()&&k*i+b>0) {
                    img.setRGB(i, (int)(k * i + b), color);
                }
            }
        }
    }
}


public class Main extends JFrame {

    static final int w = 1366;
    static final int h = 768;
    static final float x1 = 100;
    static final float y1 = 100;
    static final Color color=new Color(0,0,0);

    public static void draw(Graphics2D g) {
        //Создаем буффер в который рисуем кадр.
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        //Рисуем кадр.
        Render.renderLine(img, x1, y1, x1, y1-1, color.getRGB());
        Render.renderLine(img, x1, y1, x1+15, y1-30, color.getRGB());
        Render.renderLine(img, x1, y1, x1+15, y1-15, color.getRGB());
        Render.renderLine(img, x1, y1, x1+30, y1-15, color.getRGB());
        Render.renderLine(img, x1, y1, x1+1, y1, color.getRGB());
        Render.renderLine(img, x1, y1, x1+30, y1+15, color.getRGB());
        Render.renderLine(img, x1, y1, x1+15, y1+15, color.getRGB());
        Render.renderLine(img, x1, y1, x1+15, y1+30, color.getRGB());
        g.drawImage(img, 0, 0, null);
    }


    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        Main jf = new Main();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Моя супер программа");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        //в бесконечном цикле рисуем новый кадр
        while (true) {
            long frameLength = 1000 / 60; //пытаемся работать из рассчета  60 кадров в секунду
            long start = System.currentTimeMillis();
            BufferStrategy bs = jf.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
            draw(g);

            bs.show();
            g.dispose();

            long end = System.currentTimeMillis();
            long len = end - start;
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }
}