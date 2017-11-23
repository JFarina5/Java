// Dots.java
// By: John Farina

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Dots
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Dots");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocation(300, 100);
        frame.getContentPane().add(new DotsPanel());

        frame.setVisible(true);
    }
}

class DotsPanel extends JPanel

{
    private int pointsize = 10;
    private int speed =10;
    private int frameheight = 400;
    private int framewidth = 600;

    private ArrayList<Point> pointList;
    private ArrayList<Integer> sizeList;
    private ArrayList<Color> colorList;
    private ArrayList<Double> speedx;
    private ArrayList<Double> speedy;
    private ArrayList<Long> birth;
    private ArrayList<Boolean> type;


    private Timer timer;
    JSlider slid;

    public DotsPanel()
    {
        timer = new Timer(30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                int i = 0;
                for (Point point : pointList) {
                    point.setLocation(point.x - speedx.get(i), point.y - speedy.get(i));
                    repaint();

                    if (point.x <= 0) {
                        point.x = 1;
                        speedx.set(i, -Math.abs(speedx.get(i)));
                    }
                    else if (point.x >= (getWidth() - sizeList.get(i))) {
                        point.x = (getWidth() - sizeList.get(i));
                        speedx.set(i, Math.abs(speedx.get(i)));
                    }

                    if (point.y <= 0) {
                        point.y = 1;
                        speedy.set(i, -Math.abs(speedy.get(i)));
                    }
                    else if (point.y > (getHeight() - sizeList.get(i))) {
                        point.y = (getHeight() - sizeList.get(i));
                        speedy.set(i, Math.abs(speedy.get(i)));
                    }

                    i++;
                }

            }
        });
        pointList = new ArrayList<Point>();
        sizeList = new ArrayList<Integer>();
        colorList = new ArrayList<Color>();
        speedx = new ArrayList<Double>();
        speedy = new ArrayList<Double>();
        birth = new ArrayList<Long>();
        type = new ArrayList<Boolean>();
        slid = new JSlider(0, 500, 100);

        addMouseListener (new DotsListener());
        addMouseWheelListener(new DotsMouseWheel());
        addMouseMotionListener(new DotsListener());

        setBackground(Color.black);
        setPreferredSize(new Dimension(framewidth, frameheight));
        add(slid);
        timer.start();
    }


    public void paintComponent(Graphics page)
    {
        super.paintComponent(page);
        int i=0;
        for (Point spot : pointList)
        {
            page.setColor(colorList.get(i));

            if(type.get(i))
                page.fillOval((int)pointList.get(i).getX(), (int)pointList.get(i).getY(), sizeList.get(i)*2, sizeList.get(i)*2);
            else
                page.drawOval((int)pointList.get(i).getX(), (int)pointList.get(i).getY(), sizeList.get(i)*2, sizeList.get(i)*2);
            i++;
        }
        page.drawString("Count: " + pointList.size(), 5, 15);
    }

    private class DotsListener implements MouseListener, MouseMotionListener
    {
        public void mousePressed(MouseEvent event)
        {

            if (SwingUtilities.isRightMouseButton(event)) {
                if(timer.isRunning()) { timer.stop(); }
                for (Point p : pointList) {
                    p.x = event.getPoint().x;
                    p.y = event.getPoint().y;
                }
                repaint();
                return;
            }
            pointList.add(event.getPoint());
            sizeList.add(pointsize);
            colorList.add(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random()));
            speedx.add(Math.random()*speed*2-speed);
            speedy.add(Math.random()*speed*2-speed);
            birth.add(System.currentTimeMillis());
            if (SwingUtilities.isLeftMouseButton(event))
                type.add(true);
            else
                type.add(false);
            repaint();
        }
        public void mouseExited(MouseEvent event) {
        }
        public void mouseEntered(MouseEvent event) {
        }
        public void mouseReleased(MouseEvent event) {

            if (SwingUtilities.isRightMouseButton(event)) {
                if (!timer.isRunning())
                    timer.restart();
            }
        }
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mousePressed(e);
        }
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }


    private class DotsMouseWheel implements MouseWheelListener
    {

        public void mouseWheelMoved(MouseWheelEvent e) {
            int i = 0;
            for (Integer integer : sizeList) {
                sizeList.set(i, integer + e.getWheelRotation()*-1);
                ++i;
            }
        }

    }
}