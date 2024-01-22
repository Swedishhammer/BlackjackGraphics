
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Drawing.Frame;


public class Main{
    static Point screenMax;
    static JPanel[] panels;
    static JPanel basePanel = new JPanel();
    static CardLayout cardLayout = new CardLayout();
    static JFrame framing = new JFrame("Blackjack");
    public static void main(String[] args){
        collectScreenMax();

        panels = setUpPanels();
        setUpCardLayout();
        setUpFrame();

        String filePath = "C:\\Program Files\\Swedishhammer Studios\\BlackjackGraphics\\LocalTextFiles\\StatsFile.txt";
        try{
            Files.createDirectories(Paths.get(filePath));
        }catch(IOException e){
            System.out.println(e);
            System.out.println("Something went wrong");
        }
        
        FileManager fm = new FileManager(filePath);
        fm.createFile();
        //new Frame();
    }
    //Setup methods (probably not effecient but make code look cleaner)
    private static JPanel[] setUpPanels(){
        JPanel[] panels = new JPanel[5];
        panels[0] = mainMenuSetup();
        panels[1] = gameScreenSetup();
        panels[2] = rulesScreenSetup();
        panels[3] = settingsScreenSetup();
        panels[4] = profileScreenSetup();
        return panels;
    }
    private static JPanel mainMenuSetup(){
        JPanel p = new JPanel();
        JButton playGameBtn = new JButton("Play Game");
        JButton rulesBtn = new JButton("Rules");
        JButton profileBtn = new JButton("Profile");
        JButton settingsBtn = new JButton("Settings");

        p.add(playGameBtn);
        p.add(rulesBtn);
        p.add(settingsBtn);
        p.add(profileBtn);
        
        playGameBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "1");
            }
        });
        rulesBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "2");
            }
        });
        settingsBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "3");
            }
        });
        profileBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "4");
            }
        });
        return p;
    }
    private static JPanel gameScreenSetup(){
        JPanel p = new JPanel();
        JTextArea title = new JTextArea("Game Screen");

        p.add(title);

        JButton toMainMenu = new JButton("Return to Main Menu");
        p.add(toMainMenu);

        toMainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "0");
            }
        });
        return p;
    }
    private static JPanel rulesScreenSetup(){
        JPanel p = new JPanel();
        JTextArea title = new JTextArea("Rules Screen");

        p.add(title);

        JButton toMainMenu = new JButton("Return to Main Menu");
        p.add(toMainMenu);

        toMainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "0");
            }
        });
        return p;
    }
    private static JPanel settingsScreenSetup(){
        JPanel p = new JPanel();
        JTextArea title = new JTextArea("Settings Screen");

        p.add(title);

        JButton toMainMenu = new JButton("Return to Main Menu");
        p.add(toMainMenu);

        toMainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "0");
            }
        });
        return p;
    }
    private static JPanel profileScreenSetup(){
        JPanel p = new JPanel();
        JTextArea title = new JTextArea("Profile Screen");

        p.add(title);

        JButton toMainMenu = new JButton("Return to Main Menu");
        p.add(toMainMenu);

        toMainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(basePanel, "0");
            }
        });
        return p;
    }
    
    private static void setUpCardLayout(){
        basePanel.setLayout(cardLayout);
        for(int i = 0;i < panels.length;i++){
            basePanel.add(panels[i],Integer.toString(i));
        }
    }
    private static void setUpFrame(){
        framing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        framing.add(basePanel);
        cardLayout.show(basePanel, "0");
        framing.setExtendedState(Frame.MAXIMIZED_BOTH);
        framing.setLocationRelativeTo(null);
        framing.setVisible(true);
        
    }
    //Collects the sizing of the screen
    private static void collectScreenMax(){
        Frame screenCheck = new Frame();
        screenMax = new Point(screenCheck.getWidth(), screenCheck.getHeight());
    }
}