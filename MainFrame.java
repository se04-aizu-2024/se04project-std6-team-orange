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

public class MainFrame extends JFrame{
    JPanel controlPanel;
    BarChartPanel chartPanel;
    JPanel chartContainer;
    JPanel rootPanel;
    List<Integer> data;
    private int swappedIndex1 = -1;
    private int swappedIndex2 = -1;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }   
    
    public MainFrame() {
        setTitle("Sort Visualizer");
        setSize(800, 600);
        setLocationRelativeTo(null);

        controlPanel = new JPanel();
        GridBagLayout layout1 = new GridBagLayout(); 
        controlPanel.setLayout(layout1);
        GridBagConstraints gbc = new GridBagConstraints();
        

        JComboBox<String> dropdownMenu = new JComboBox<>(new String[]{"Selection Sort", "Merge Sort", "Quick Sort"});
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(dropdownMenu, gbc);

        
        data = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { 
            data.add((int) (Math.random() * 50 + 10));        
        }

        chartPanel = new BarChartPanel(data);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JButton button = new JButton("Swap");
        button.addActionListener(e -> {
            randomSwap();
            chartPanel.repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(button, gbc);
        
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 420));
        chartContainer.add(chartPanel, BorderLayout.EAST);

        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        rootPanel.add(controlPanel, BorderLayout.WEST);
        rootPanel.add(chartContainer, BorderLayout.CENTER);
    
        setContentPane(rootPanel);
        
    }

    public void setSwapeedIndicies(int i, int j){
        swappedIndex1 = i;
        swappedIndex2 = j;
    } 

    public void repaint() {
        chartPanel.repaint();
    }

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

    class BarChartPanel extends JPanel {
        private List<Integer> data;

        public BarChartPanel(List<Integer> data) {
            this.data = data;
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;

            setSize(400, 500);
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int barWidth = panelWidth / data.size();
            int maxValue = -1;
            for (int i: data) {
                if (i > maxValue) {
                    maxValue = i;
                }
            }

            for (int i = 0; i < data.size(); i++) {
                int value = data.get(i);
                int barHeight = (int) ((value / (maxValue * 1.0)) * (panelHeight - 20));
                int x = i * barWidth + 10;
                int y = panelHeight - barHeight;

                if (i == swappedIndex1 || i == swappedIndex2) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLUE);
                }

                g.fillRect(x, y, barWidth - 20, barHeight);
            }
        }
    }
}
