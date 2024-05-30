import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageViewerGUI extends JFrame implements ActionListener{
    JButton selectFileButton = new JButton("Choose Image");
    JButton showImageButton = new JButton("Show Image");
    JButton resizeButton = new JButton("Resize");
    JButton grayscaleButton = new JButton("Gray Scale");
    JButton brightnessButton = new JButton("Brightness");
    JButton closeButton = new JButton("Exit");
    JButton showResizeButton = new JButton("Show Result");
    JButton showBrightnessButton = new JButton("Result");
    JButton backButton = new JButton("Back");
    JTextField widthTextField = new JTextField();
    JTextField heightTextField = new JTextField();
    JTextField brightnessTextField = new JTextField();
    // I used getProperty for getting the user directory : https://docs.oracle.com/javase/7/docs/api/java/lang/System.html#getProperties()
    // I also concatenate '/Pictures' at the end of the getProperty, So the path leads to the user's Pictures folder.
    String filePath = System.getProperty("user.home") + "/Pictures"; //For Windows users
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // I added this so the window wouldn't open in the corner
        this.setLocationRelativeTo(null);

        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        // Adding functionality to the buttons
        this.backButton.addActionListener(this);
        this.selectFileButton.addActionListener(this);
        this.brightnessButton.addActionListener(this);
        this.showImageButton.addActionListener(this);
        this.closeButton.addActionListener(this);
        this.showBrightnessButton.addActionListener(this);
        this.grayscaleButton.addActionListener(this);
        this.resizeButton.addActionListener(this);
        this.showResizeButton.addActionListener(this);

        mainPanel();
    }

    public void mainPanel(){
        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));
        buttonsPanel.setBounds(150,50,400,100);

        // Adding JLabel showing app title to mainPanel
        JLabel imageViewerLabel = new JLabel("Image Viewer");
        imageViewerLabel.setBounds(312, 0,100,35);
        mainPanel.add(imageViewerLabel);

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);
        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel(){
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);

        // Declaring JLabels for user instructions
        JLabel resizeSectionLabel = new JLabel("Resize Section");
        resizeSectionLabel.setBounds(300,20,100,50);

        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setBounds(200,50,100,50);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setBounds(200,100,100,50);

        this.widthTextField.setBounds(300,60,100,30);
        this.heightTextField.setBounds(300,110,100,30);
        this.backButton.setBounds(160,180,80,30);
        this.showResizeButton.setBounds(400,180,120,30);

        resizePanel.add(this.widthTextField);
        resizePanel.add(this.heightTextField);
        resizePanel.add(this.backButton);
        resizePanel.add(this.showResizeButton);
        resizePanel.add(resizeSectionLabel);
        resizePanel.add(widthLabel);
        resizePanel.add(heightLabel);
        this.add(resizePanel);
    }
    public void brightnessPanel(){
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);

        // Create JLabel for brighten value
        JLabel enterValue = new JLabel("Enter f (must be between 0 and 1)");
        enterValue.setBounds(50,50,500,100);

        // Adjusting brightnessTextField
        this.brightnessTextField.setBounds(300,85,100,30);

        // Adjusting backButton
        this.backButton.setBounds(60,150,100,30);

        // Adjusting showBrightnessButton
        this.showBrightnessButton.setBounds(300,150,100,30);

        // Adding components to the brightnessPanel
        brightnessPanel.add(showBrightnessButton);
        brightnessPanel.add(this.backButton);
        brightnessPanel.add(this.brightnessTextField);
        brightnessPanel.add(enterValue);

        // Replacing this panel with the last panel
        this.getContentPane().removeAll();
        this.add(brightnessPanel);
    }

    public void chooseFileImage(){
        // Only shows the photo related files
        // https://stackoverflow.com/questions/13517770/jfilechooser-filters
        this.fileChooser.setAcceptAllFileFilterUsed(false);
        this.fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

        int option = this.fileChooser.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            this.file = this.fileChooser.getSelectedFile();
        } else {
            //This is for debugging; we could show this on a different dialog for user
            System.out.println("Something went wrong in selecting file.");
        }
    }
    public void showOriginalImage(){
        try {
            JFrame tempFrame = new JFrame();
            JPanel tempPanel = new JPanel();

            // Reading picture from file path and adding it to JLabel
            BufferedImage bufferedImage = ImageIO.read(this.file);
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            JLabel jlabel = new JLabel();
            jlabel.setIcon(imageIcon);
            tempPanel.add(jlabel);

            tempPanel.setSize(1800, 1000);
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(1800, 1000);
            tempFrame.setVisible(true);
            tempFrame.setResizable(true);
            tempFrame.add(tempPanel);
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (IllegalArgumentException iex){
            //This is for debugging; we could show this on a different dialog for user
            System.out.println("Input is null");
        }
    }

    public void grayScaleImage(){
        try {
            JFrame tempFrame = new JFrame();
            JPanel tempPanel = new JPanel();

            BufferedImage bufferedImage = ImageIO.read(this.file);
            // Adding gray filter to the bufferedImage
            ImageFilter filter = new GrayFilter(false, 40);
            ImageProducer producer = new FilteredImageSource(bufferedImage.getSource(),filter);
            // Adding the image to the JLabel
            ImageIcon imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(producer));
            JLabel jlabel = new JLabel();
            jlabel.setIcon(imageIcon);
            tempPanel.add(jlabel);

            tempPanel.setSize(1800, 1000);
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(1800, 1000);
            tempFrame.setVisible(true);
            tempFrame.setResizable(true);
            tempFrame.add(tempPanel);

        }  catch (IOException ex){
            ex.printStackTrace();
        } catch (IllegalArgumentException iex){
            //This is for debugging; we could show this on a different dialog for user
            System.out.println("Input is null");
        }
    }
    public void showResizeImage(int w, int h){
        try {
            JFrame tempFrame = new JFrame();
            JPanel tempPanel = new JPanel();

            BufferedImage bufferedImage = ImageIO.read(this.file);
            // Adding the rescaled image to the JLabel
            ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(w,h,Image.SCALE_DEFAULT));
            JLabel jlabel = new JLabel();
            jlabel.setIcon(imageIcon);
            tempPanel.add(jlabel);

            tempPanel.setSize(1800, 1000);
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(1800, 1000);
            tempFrame.setVisible(true);
            tempFrame.setResizable(true);
            tempFrame.add(tempPanel);

        } catch (IOException ex){
            ex.printStackTrace();
        } catch (IllegalArgumentException iex){
            //This is for debugging; we could show this on a different dialog for user
            System.out.println("Input is null");
        }
    }
    public void showBrightnessImage(float f){
        try {
            JFrame tempFrame = new JFrame();
            JPanel tempPanel = new JPanel();

            // Reading picture from file path
            BufferedImage bufferedImage = ImageIO.read(this.file);

            // Rescaling the picture in order to change the brightness
            RescaleOp op = new RescaleOp(f,0,null);
            bufferedImage = op.filter(bufferedImage,bufferedImage);

            // Adding the whole thing to the JLabel
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            JLabel jlabel = new JLabel();
            jlabel.setIcon(imageIcon);
            tempPanel.add(jlabel);

            tempPanel.setSize(1800, 1000);
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(1800, 1000);
            tempFrame.setVisible(true);
            tempFrame.setResizable(true);
            tempFrame.add(tempPanel);
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (IllegalArgumentException iex){
            //This is for debugging; we could show this on a different dialog for user
            System.out.println("Input is null");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==resizeButton){
            this.getContentPane().removeAll();
            resizePanel();
            this.revalidate();
            this.repaint();

        }else if(e.getSource()== showImageButton){
            showOriginalImage();

        }else if(e.getSource()==grayscaleButton){
            grayScaleImage();

        }else if(e.getSource()== showResizeButton){
            try{
                int tempWidthValue = Integer.parseInt(this.widthTextField.getText());
                int tempHeightValue = Integer.parseInt(this.heightTextField.getText());
                if(tempWidthValue < 0 || tempHeightValue < 0){
                    System.out.println("Out of range!");
                } else
                    this.showResizeImage(tempWidthValue,tempHeightValue);

            } catch (NumberFormatException nfx){
                // This for debug, but we could show it in a dialog for user
                System.out.println("Couldn't parse the value");
            }

        }else if(e.getSource()==brightnessButton){
            brightnessPanel();
            this.revalidate();
            this.repaint();

        }else if(e.getSource()== showBrightnessButton){
            try {
                float tempValueOfBrighten = Float.parseFloat(brightnessTextField.getText());
                if (tempValueOfBrighten > 1 || tempValueOfBrighten < 0) {
                    // This for debug, but we could show it in a dialog for user
                    System.out.println("Out of range for brighten value");
                } else{
                    this.brightenFactor = tempValueOfBrighten;
                    showBrightnessImage(this.brightenFactor);
                }

            } catch (NumberFormatException nfe){
                // This for debug, but we could show it in a dialog for user
                System.out.println("Couldn't parse the value");
            }

        }else if(e.getSource()== selectFileButton){
            chooseFileImage();

        }else if(e.getSource()==closeButton){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource()==backButton){
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }
    }
}