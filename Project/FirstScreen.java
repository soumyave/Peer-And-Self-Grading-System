import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//The first window i.e. displayed upon execution of the program
//Asks the user for number of members and if scores have been previously entered
public class FirstScreen extends JFrame implements ActionListener {
	
    private JComboBox numOfMembers;
    private JRadioButton scoresNotEntered;
    private JRadioButton scoresEntered;
    private JButton submitButton;
    private Integer[] data = {2, 3, 4, 5, 6, 7};


    public static void main(String[] args) {
        FirstScreen firstScreen = new FirstScreen();
        firstScreen.setTitle("SEC 542 Peer Evaluation");
        firstScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstScreen.setSize(500, 500);
        firstScreen.setVisible(true);
    }

    //adds member components to the container like JLabels, radio buttons and drop down box
    //Parameters : pane - container to which all the components are added
    private void addMemberComponents(Container pane) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.ipadx = 16;
        gc.ipady = 8;


        JLabel label = new JLabel("Enter the number of group members:");
        gc.fill = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 0;
        pane.add(label, gc);

        numOfMembers = new JComboBox(data);
        gc.gridx = 1;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        pane.add(numOfMembers, gc);

        label = new JLabel("Have you previously entered scores?");
        gc.gridx = 0;
        gc.gridy = 1;
        pane.add(label, gc);


        scoresEntered = new JRadioButton("Yes");
        scoresNotEntered = new JRadioButton("No");
        ButtonGroup scoresRadioButtons = new ButtonGroup();
        scoresRadioButtons.add(scoresEntered);
        scoresRadioButtons.add(scoresNotEntered);
        scoresNotEntered.setSelected(true);
        JPanel panelForRadioButtons = new JPanel();
        panelForRadioButtons.add(scoresEntered);
        panelForRadioButtons.add(scoresNotEntered);
        gc.gridx = 1;
        gc.gridy = 1;
        pane.add(panelForRadioButtons, gc);

        submitButton = new JButton("Submit");
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.CENTER;
        pane.add(submitButton, gc);
        submitButton.addActionListener(this);

    }

    public FirstScreen() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        addMemberComponents(pane);
    }

    //this method is called when the user presses submit button
    @Override
    public void actionPerformed(ActionEvent e) {

        int numOfMembers = data[this.numOfMembers.getSelectedIndex()];
        boolean previousScoreExist = scoresEntered.isSelected();
        //Called when submit button is clicked
        //Invoking the second window.
        SecondScreen secondScreen = new SecondScreen(numOfMembers, previousScoreExist);
        //Disposing the current window
        this.dispose();
    }
}