import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    // Panels and others
    JPanel controlPanel;
    JPanel logPanel;
    JTextArea logArea;
    JScrollPane scrollPane;
    BarChartPanel chartPanel;
    JPanel chartContainer;
    JPanel rootPanel;
    JButton button1;
    JButton button2;
    // Data to be sorted
    List<Integer> data;
    // For sorting visualization
    private int swappedIndex1 = -1;
    private int swappedIndex2 = -1;
    private List<List<Integer>> procedureList = new ArrayList<>();

    private ArraylistSorter sorter = null;
    private boolean isMergeSort = false;
    private int currentStep = 0;
    private int listSize = 0;

    public static void main(String[] args) {
        List<Integer> list = ListUtils.generateRandomList(50);

        MainFrame frame = new MainFrame(list);
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
      sorter = new BubbleSort();
      makeFrame(d);
    }

    public void makeFrame(List<Integer> d) {
        // Set data
        data = d;

        // Configure basic properties
        setTitle("Sort Visualizer");
        setSize(800, 650);
        setLocationRelativeTo(null);

        // Create a panel for buttons and a dropdown menu
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        sorter = new BubbleSort();
        isMergeSort = false;
        
        // Create and place the dropdown menu
        JComboBox<String> dropdownMenu = new JComboBox<>(new String[]{"Bubble Sort", "Heap Sort", "Merge Sort", "Quick Sort"});
        dropdownMenu.addActionListener(e -> {
            String selectedSort = (String) dropdownMenu.getSelectedItem();

            switch (selectedSort) {
                case "Bubble Sort":
                    sorter = new BubbleSort();
                    isMergeSort = false;
                    break;
                case "Heap Sort":
                    sorter = new HeapSort();
                    isMergeSort = false;
                    break;
                case "Merge Sort":
                    sorter = new MergeSort();
                    isMergeSort = true;
                    break;
                case "Quick Sort":
                    sorter = new QuickSort();
                    isMergeSort = false;
                    break;
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(dropdownMenu, gbc);

        // Create a panel to display the sorting visualization
        chartPanel = new BarChartPanel(data);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Create and place a button
        button1 = new JButton("Sort All");
        button1.addActionListener(e -> {
            if (!ListUtils.isSortedAscending(data)) {
                button1.setEnabled(false);
                button2.setEnabled(false);
    
                if (procedureList.size() == 0) {
                    procedureList = sorter.sort(new ArrayList<>(data));
                }
    
                autoSwap();
    
                currentStep = 0;
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(button1, gbc);

        // Create and place a button
        button2 = new JButton("Sort Step by Step");
        button2.addActionListener(e -> {
            if (!ListUtils.isSortedAscending(data)) {
                if (currentStep ==  0) {
                    procedureList = sorter.sort(new ArrayList<>(data));
                    listSize = procedureList.size();
                }
     
                manualSwap(procedureList.remove(0));
                currentStep++;
                if (currentStep == listSize) {
                    currentStep = 0;
                    
                    if (ListUtils.isSortedAscending(data)) {
                        logArea.append("\nSort complete.");
                        logArea.setCaretPosition(logArea.getDocument().getLength());
                    } else {
                        logArea.append("\nError occurred.");
                        logArea.setCaretPosition(logArea.getDocument().getLength());
                    }
                } else {
                    logArea.append("\nSorting " + currentStep + "/" + listSize + ".");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                }
            } else {
                procedureList.clear();

                currentStep = 0;
                    
                if (ListUtils.isSortedAscending(data)) {
                    logArea.append("\nSort complete.");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                } else {
                    logArea.append("\nError occurred.");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                }
            }

        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(button2, gbc);

        // Create and place a button
        JButton button3 = new JButton("Generate data");
        button3.addActionListener(e -> {
            data = ListUtils.generateRandomList(50);
            currentStep = 0;
            setSwapeedIndicies(-1, -1);
            chartPanel.setData(data);
            chartPanel.repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(button3, gbc);

        // Create a log area at the bottom
        logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        logArea = new JTextArea(1, 30);
        logArea.setEditable(false);
        scrollPane = new JScrollPane(logArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Nest the visualization panel in another panel to adjust placement
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 420));
        chartContainer.add(chartPanel, BorderLayout.EAST);

        // Combine all created panels into one
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        rootPanel.add(controlPanel, BorderLayout.WEST);
        rootPanel.add(chartContainer, BorderLayout.CENTER);
        rootPanel.add(logPanel, BorderLayout.SOUTH);
    
        // Set the created panel to be displayed
        setContentPane(rootPanel);
    }

    // Set the indices to be swapped
    public void setSwapeedIndicies(int i, int j) {
        swappedIndex1 = i;
        swappedIndex2 = j;
    } 

    private void autoSwap() {

        SwingWorker<Void, List<Integer>> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (isMergeSort) {
                    while (!procedureList.isEmpty()) {
                        List<Integer> tempList = procedureList.remove(0);
                        setSwapeedIndicies(tempList.get(0), -1);
                        data.set(tempList.get(0), tempList.get(1));
                        publish(new ArrayList<>(data));
                        Thread.sleep(100);
                    }
                } else {
                    while (!procedureList.isEmpty()) {
                        List<Integer> tempList = procedureList.remove(0);
                        setSwapeedIndicies(tempList.get(0), tempList.get(1));
                        int temp = data.get(tempList.get(0));
                        data.set(tempList.get(0), data.get(tempList.get(1)));
                        data.set(tempList.get(1), temp);
                        publish(new ArrayList<>(data));
                        Thread.sleep(100);

                    }
                }
                return null;
            }

            @Override
            protected void process(List<List<Integer>> chunks) {
                chartPanel.setData(chunks.get(chunks.size() - 1));
                chartPanel.repaint();
            }

            @Override
            protected void done() {
                if (ListUtils.isSortedAscending(data)) {
                    logArea.append("\nSort complete.");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                } else {
                    logArea.append("\nError occurred.");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                }

                button1.setEnabled(true);
                button2.setEnabled(true);
            }
        };

        worker.execute();
    }

    private void manualSwap(List<Integer> li) {
        if(isMergeSort) {
            setSwapeedIndicies(li.get(0), -1);
            data.set(li.get(0), li.get(1));
            chartPanel.setData(data);
            chartPanel.repaint();
        } else {
            setSwapeedIndicies(li.get(0), li.get(1));
            int temp = data.get(li.get(0));
            data.set(li.get(0), data.get(li.get(1)));
            data.set(li.get(1), temp);
            chartPanel.setData(data);
            chartPanel.repaint();
        }
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
                int barHeight = (int) ((data.get(i) / (maxValue * 1.0)) * (panelHeight - 10));
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
