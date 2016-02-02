package lccm.sippe.controller;

import lccm.sippe.model.GamePreferences;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * @author: Luis Carlos Castillo Martinez on 27/01/16.
 * Université Blaise Pascal
 * lcarlos.asimov@gmail.com
 * github.com/luisccastillo
 */
public class PreferencesDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JButton aliveCellGridColorButton;
    private JButton deadCellGridColorButton;
    private JButton borderColorButton;
    private JButton pointerColorButton;
    private JCheckBox displayBordersCheckBox;
    private JSpinner boardSizeSpinner;
    private JComboBox rulePresetComboBox;
    private JTextField birthRulesTextField;
    private JTextField survivalRulesTextField;

    private static int HEIGHT = 370;
    private static int WIDTH = 300;
    private static Color randomColor = new Color(233, 233, 233);
    private static List<Color> colors = new ArrayList<>
            (Arrays.asList(randomColor, Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
                    Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW));
    private Random random;

    public PreferencesDialog(){
        this.setLocationByPlatform(true);
        initialize();
    }

    private void initialize(){
        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");
        aliveCellGridColorButton = new JButton("");
        deadCellGridColorButton = new JButton("");
        borderColorButton = new JButton("");
        pointerColorButton = new JButton("");
        displayBordersCheckBox = new JCheckBox("");
        boardSizeSpinner = new JSpinner();
        birthRulesTextField = new JTextField();
        survivalRulesTextField = new JTextField();
        rulePresetComboBox = new JComboBox(GamePreferences.getRULES());
        random = new Random();
        SpinnerModel spinnerModel = new SpinnerNumberModel(GamePreferences.getCellGridSize(), 10, 500, 5);
        Container dialogPane = getContentPane();
        dialogPane.setLayout(new BorderLayout());

        this.setTitle("Preferences");
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        getAliveCellGridColorButton().setBackground(GamePreferences.getAliveCellColor());
        getAliveCellGridColorButton().setFocusPainted(false);
        getDeadCellGridColorButton().setBackground(GamePreferences.getDeadCellColor());
        getDeadCellGridColorButton().setFocusPainted(false);
        getDisplayBordersCheckBox().setSelected(GamePreferences.isBorderedGrid());
        getDisplayBordersCheckBox().setFocusPainted(false);
        getCellPointerColorButton().setBackground(GamePreferences.getCellPointerColor());
        getDeadCellGridColorButton().setFocusPainted(false);
        getBorderColorButton().setBackground(GamePreferences.getBorderColor());
        getBorderColorButton().setFocusPainted(false);
        getBoardSizeSpinner().setModel(spinnerModel);
        getSurvivalRulesTextField().setText(alterString(Arrays.toString(GamePreferences.getSurvivalPreset())));
        getBirthRulesTextField().setText(alterString(Arrays.toString(GamePreferences.getBirthPreset())));

        //border layout: center
        JPanel verticalLayoutPanel;
        verticalLayoutPanel = new JPanel();
        verticalLayoutPanel.setLayout(new BoxLayout(verticalLayoutPanel, BoxLayout.Y_AXIS));
        verticalLayoutPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel gridLayoutPanel;
        gridLayoutPanel = new JPanel();
        gridLayoutPanel.setLayout(new GridLayout(5, 2, 5, 5));
        TitledBorder titledBorder = new TitledBorder(" Visual ");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        gridLayoutPanel.setBorder(titledBorder);
        gridLayoutPanel.add(new JLabel(" Alive cell "));
        gridLayoutPanel.add(getAliveCellGridColorButton());
        gridLayoutPanel.add(new JLabel(" Dead cell "));
        gridLayoutPanel.add(getDeadCellGridColorButton());
        gridLayoutPanel.add(new JLabel(" Cell pointer "));
        gridLayoutPanel.add(getCellPointerColorButton());
        gridLayoutPanel.add(new JLabel(" Cell border "));
        gridLayoutPanel.add(getBorderColorButton());
        gridLayoutPanel.add(new JLabel(" Draw borders  "));
        gridLayoutPanel.add(getDisplayBordersCheckBox());
        verticalLayoutPanel.add(gridLayoutPanel);

        gridLayoutPanel = new JPanel();
        gridLayoutPanel.setLayout(new GridLayout(4, 2, 5, 5));
        titledBorder = new TitledBorder(" Game ");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        gridLayoutPanel.setBorder(titledBorder);
        gridLayoutPanel.add(new JLabel(" Grid size"));
        gridLayoutPanel.add(getBoardSizeSpinner());
        gridLayoutPanel.add(new JLabel (" Rule preset"));
        gridLayoutPanel.add(getRulePresetComboBox());
        gridLayoutPanel.add(new JLabel (" Survival"));
        gridLayoutPanel.add(getSurvivalRulesTextField());
        gridLayoutPanel.add(new JLabel (" Birth"));
        gridLayoutPanel.add(getBirthRulesTextField());
        verticalLayoutPanel.add(gridLayoutPanel);
        dialogPane.add(verticalLayoutPanel,BorderLayout.CENTER);

        //borderlayout: south
        gridLayoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        gridLayoutPanel.add(getOkButton());
        gridLayoutPanel.add(getCancelButton());
        dialogPane.add(gridLayoutPanel,BorderLayout.SOUTH);

        addGUIEventListeners();
        this.setModal(true);
        this.setVisible(false);
    }

    private void addGUIEventListeners(){
        getDeadCellGridColorButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeButtonBackgroundColor(getDeadCellGridColorButton());
            }
        });
        getAliveCellGridColorButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeButtonBackgroundColor(getAliveCellGridColorButton());
            }
        });
        getCellPointerColorButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeButtonBackgroundColor(getCellPointerColorButton());
            }
        });
        getBorderColorButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeButtonBackgroundColor(getBorderColorButton());
            }
        });

        getRulePresetComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeRulePreset(getRulePresetComboBox());
            }
        });
    }

    private Color createRandomColor(){
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return new Color(r, g, b);
    }

    /** Changes the background color of the element
     * from the static list of colors in game preferences
    * @params jButton JButton the button to be modified
     */
    private void changeButtonBackgroundColor(JButton jButton){
        Color currentColor = jButton.getBackground();
        int index = colors.indexOf(currentColor);
        if (index == colors.size() - 1) {
            jButton.setBackground(colors.get(0));
            jButton.setText("random");
        }
        else {
            jButton.setText("");
            jButton.setBackground(colors.get(index + 1));
        }
    }

    /** Removes the [ ] characters from the array to string transformation
     * as well as empty spaces
     * @param string string to be modified
     * @return string string after being modified
     */
    private String alterString(String string){
        string = string.substring(1 , string.length() - 1);
        string = string.replaceAll("\\s+","");
        return string;
    }

    /** Changes the values of the survival and birth JTextFields depending on the
     * JComboBox selection
    * @param rulePresetComboBox JComboBox that holds the rule selection in the UI
     **/
    private void changeRulePreset(JComboBox rulePresetComboBox){
        int index = rulePresetComboBox.getSelectedIndex();
        String stringSurvivalRule = alterString(Arrays.toString(GamePreferences.getSurvivalPresetAt(index)));
        getSurvivalRulesTextField().setText(stringSurvivalRule);
        String stringBirthRule = alterString(Arrays.toString(GamePreferences.getBirthPresetAt(index)));
        getBirthRulesTextField().setText(stringBirthRule);
    }

    /**
    *  Transforms a string into an array of Integers by the split of a ","
     * @param text String to be split
     * @return rules Integer[] the created array with integer values
     */
    private Integer[] stringToIntegerArray(String text){
        String [] textFieldString = text.split(",");
        Integer[] rules = new Integer[textFieldString.length];
        for(int i=0; i<rules.length; i++)
        {
            try{
                rules[i] = Integer.parseInt(textFieldString[i]);
            }
            catch(NumberFormatException nfe){
                nfe.printStackTrace();
            }
        }
        return rules;
    }

    public Integer[] getBirthRulePreset(){
        return stringToIntegerArray(getBirthRulesTextField().getText());
    }

    public Integer[] getSurvivalRulePreset(){
        return stringToIntegerArray(getSurvivalRulesTextField().getText());
    }

    public JComboBox getRulePresetComboBox() {
        return rulePresetComboBox;
    }

    public JTextField getBirthRulesTextField() {
        return birthRulesTextField;
    }

    public JTextField getSurvivalRulesTextField() {
        return survivalRulesTextField;
    }

    public Color getAliveCellColor(){
        if (aliveCellGridColorButton.getBackground() != randomColor)
            return aliveCellGridColorButton.getBackground();
        else
            return createRandomColor();
    }

    public Color getDeadCellColor(){
        if (deadCellGridColorButton.getBackground() != randomColor)
            return deadCellGridColorButton.getBackground();
        else
            return createRandomColor();
    }

    public Color getBorderCellColor(){
        if (borderColorButton.getBackground() != randomColor)
            return borderColorButton.getBackground();
        else
            return createRandomColor();
    }

    public Color getCellPointerColor(){
        if (pointerColorButton.getBackground() != randomColor)
            return pointerColorButton.getBackground();
        else
            return createRandomColor();
    }

    public int getCellGridSize() { return (int)boardSizeSpinner.getValue();}

    public JButton getCellPointerColorButton(){ return pointerColorButton; }

    public JButton getBorderColorButton(){ return borderColorButton; }

    public JButton getAliveCellGridColorButton(){ return aliveCellGridColorButton; }

    public JButton getDeadCellGridColorButton(){ return deadCellGridColorButton; }

    public JCheckBox getDisplayBordersCheckBox(){ return displayBordersCheckBox; }

    public JSpinner getBoardSizeSpinner(){ return boardSizeSpinner;}

    public boolean displaysBorders(){
        if (displayBordersCheckBox.isSelected())
            return true;
        else
            return false;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }


}
