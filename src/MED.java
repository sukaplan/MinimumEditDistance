import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

public class MED {
	 
	private JFrame frame;
	 public static String toString(char[] a)
	    {
	        String string = new String(a);
	        return string;
	    }
	public int[][] dynamicEditDistance(char[] str1, char[] str2) {
		int temp[][] = new int[str1.length + 1][str2.length + 1];

		for (int i = 0; i < temp[0].length; i++) {
			temp[0][i] = i;
		}

		for (int i = 0; i < temp.length; i++) {
			temp[i][0] = i;
		}

		for (int i = 1; i <= str1.length; i++) {
			for (int j = 1; j <= str2.length; j++) {
				if (str1[i - 1] == str2[j - 1]) {
					temp[i][j] = temp[i - 1][j - 1];
				} else {
					temp[i][j] = 1 + min(temp[i - 1][j - 1], temp[i - 1][j], temp[i][j - 1]);
				}
			}
		}
	
		return temp;

	}

	public String printActualEdits(int T[][], char[] str1, char[] str2) {
		String operations = "";
		int i = T.length - 1;
		int j = T[0].length - 1;
		while (true) {
			if (i == 0 || j == 0) {
				break;
			}
			if (str1[i - 1] == str2[j - 1]) {
				i = i - 1;
				j = j - 1;
			} else if (T[i][j] == T[i - 1][j - 1] + 1) {
				//System.out.println("Edit " + str2[j - 1] + " in string2 to " + str1[i - 1] + " in string1");
				operations = operations + "Edit " + str2[j - 1] + " in " + toString(str2) + " to " + str1[i - 1] + " in " + toString(str1)+"\n";
				i = i - 1;
				j = j - 1;
			} else if (T[i][j] == T[i - 1][j] + 1) {
				//System.out.println("Delete in string1 " + str1[i - 1]);
				operations = operations + "Delete "  + " " + str1[i - 1] + " in " + toString(str1) +"\n";
				i = i - 1;
			} else if (T[i][j] == T[i][j - 1] + 1) {
				//System.out.println("Delete in string2 " + str2[j - 1]);
				operations = operations + "Delete " + " " + str2[j - 1] +" in " + toString(str2) + "\n";
				j = j - 1;
			} else {
				throw new IllegalArgumentException("Some wrong with given data");
			}

		}
		
		return operations;
	}

	public static String loadDictionary(String file) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-9"));) {

			String line;
			String dict = "";

			while ((line = br.readLine()) != null) {
				dict = dict + line + "\n";
			}
			return dict;
		}
	}

	static int min(int a, int b, int c) {
		int l = Math.min(a, b);
		return Math.min(l, c);
	}

	public MED() throws IOException {
		// load dictionary
		String[] dict_array = loadDictionary("sozluk.txt").split("\n");
		initialize(dict_array);
	}
	String word;
	private void initialize(String[] dict_array) {
		frame = new JFrame();
		frame.setBackground(new Color(176, 196, 222));
		frame.getContentPane().setBackground(new Color(205, 92, 92));
		frame.setBounds(100, 100, 425, 566);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Alternative Word Search");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.setBounds(137, 80, 151, 21);
		frame.getContentPane().add(btnNewButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(48, 52, 312, 21);
		frame.getContentPane().add(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(48, 111, 312, 97);
		frame.getContentPane().add(scrollPane_1);
		
		JTextArea textArea_Output = new JTextArea();
		scrollPane_1.setViewportView(textArea_Output);
		
		JTextArea string1 = new JTextArea();
		string1.setBounds(48, 267, 217, 28);
		frame.getContentPane().add(string1);
		
		JTextArea string2 = new JTextArea();
		string2.setBounds(48, 305, 217, 28);
		frame.getContentPane().add(string2);
		
		JButton btnNewButton_1 = new JButton("MED Value");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_1.setBounds(286, 288, 85, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 343, 312, 162);
		frame.getContentPane().add(scrollPane);
		
		JTextArea part2_output = new JTextArea();
		scrollPane.setViewportView(part2_output);
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				long startTime_first = System.currentTimeMillis();
				word = textArea.getText();
                textArea.setText(word);
                ArrayList<Dictionary> distances = new ArrayList<Dictionary>();
            	// calculate each word and its MED, add to dictionary array
        		for (int i = 0; i < dict_array.length; i++) {
        			int[][] val = dynamicEditDistance(word.toCharArray(), dict_array[i].toCharArray());
        			int value = val[word.toCharArray().length][dict_array[i].toCharArray().length];
        			Dictionary newdict = new Dictionary(dict_array[i], value);
        			distances.add(newdict);
        		}
        		String output_words = "";
        		Collections.sort(distances);
        		for(int i = 0; i < 5; i++) {
        			output_words = output_words + distances.get(i).getWord() + " " + distances.get(i).getDistance() + "\n";      			
        		}
        		
        		long endTime_first = System.currentTimeMillis();
        		long duration_first = (endTime_first - startTime_first);
        		System.out.println("First part, running time: " + duration_first + " ms");
        		output_words = output_words + "Running time: " + duration_first + " ms\n";
        		textArea_Output.setText(output_words);
            }
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long startTime_second = System.currentTimeMillis();
				String str1 = string1.getText();
				String str2 = string2.getText();
				int[][] result = dynamicEditDistance(str1.toCharArray(), str2.toCharArray());
				String op = printActualEdits(result, str1.toCharArray(), str2.toCharArray());
				
				long endTime_second = System.currentTimeMillis();
				long duration_second = (endTime_second - startTime_second);
				System.out.println("Second part, running time: " + duration_second + " ms");
				part2_output.setText(op + "\nTotal = " + result[str1.toCharArray().length][str2.toCharArray().length] + "\nRunning time: " + duration_second + " ms\n");
			}
		});
		
	}
	public static void main(String args[]) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MED window = new MED();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
}