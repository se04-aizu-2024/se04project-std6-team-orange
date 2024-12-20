import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random; 

public class MainFrame extends JFrame {
    // Panel
    JPanel controlPanel;
    BarChartPanel chartPanel;
    JPanel chartContainer;
    JPanel rootPanel;
    // Data to be sorted
    List<Integer> data;
    // For sorting visualization
    private int swappedIndex1 = -1;
    private int swappedIndex2 = -1;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }   
    
    // Constructor when data is not specified
    // Create random data and build the frame
    public MainFrame() {
        List<Integer> d = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { 
            d.add((int) (Math.random() * 50 + 10));        
        }

        makeFrame(d);
    }

    // Constructor when data is specified
    // Build the frame using the provided data
    public MainFrame(List<Integer> d) {
        makeFrame(d);
    }

    public void makeFrame(List<Integer> d) {
        // Set data
        data = d;

        // Configure basic properties
        setTitle("Sort Visualizer");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create a panel for buttons and a dropdown menu
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        
        // Create and place the dropdown menu
        JComboBox<String> dropdownMenu = new JComboBox<>(new String[]{"Selection Sort", "Merge Sort", "Quick Sort"});
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(dropdownMenu, gbc);

        // Create a panel to display the sorting visualization
        chartPanel = new BarChartPanel(data);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // Create and place a button
        JButton button = new JButton("Swap");
        button.addActionListener(e -> {
            randomSwap();
            chartPanel.repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(button, gbc);
        
        // Nest the visualization panel in another panel to adjust placement
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 420));
        chartContainer.add(chartPanel, BorderLayout.EAST);

        // Combine all created panels into one
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        rootPanel.add(controlPanel, BorderLayout.WEST);
        rootPanel.add(chartContainer, BorderLayout.CENTER);
    
        // Set the created panel to be displayed
        setContentPane(rootPanel);
    }

    // Set the indices to be swapped
    public void setSwapeedIndicies(int i, int j) {
        swappedIndex1 = i;
        swappedIndex2 = j;
    } 

    // Allow repainting of the visualization from outside the class
    public void repaint() {
        chartPanel.repaint();
    }

    // Perform a random swap
    private void randomSwap() {
        if (data.size() < 2) return;

        Random rand = new Random();
        swappedIndex1 = rand.nextInt(data.size());
        swappedIndex2 = rand.nextInt(data.size());

        while (swappedIndex1 == swappedIndex2) {
            swappedIndex2 = rand.nextInt(data.size());
        }

        Collections.swap(data, swappedIndex1, swappedIndex2);        
    }

    // Panel to display the visualization
    class BarChartPanel extends JPanel {
        private List<Integer> data;

        public BarChartPanel(List<Integer> data) {
            this.data = data;
        }

        // Set the data to be displayed
        public void setData(List<Integer> data) {
            this.data = data;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;

            // Set the size
            setSize(400, 500);
            
            // Obtain parameters to adjust the visualization
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int barWidth = panelWidth / data.size();
            int maxValue = -1;
            for (int i : data) {
                if (i > maxValue) {
                    maxValue = i;
                }
            }

            // Draw the bars of the bar chart one by one
            for (int i = 0; i < data.size(); i++) {
                // Set the display position
                int barHeight = (int) ((data.get(i) / (maxValue * 1.0)) * (panelHeight - 20));
                int x = i * barWidth;
                int y = panelHeight - barHeight;

                // Display swapped parts in red, others in blue
                if (i == swappedIndex1 || i == swappedIndex2) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLUE);
                }

                g.fillRect(x, y, barWidth, barHeight);
            }
        }
    }
}

